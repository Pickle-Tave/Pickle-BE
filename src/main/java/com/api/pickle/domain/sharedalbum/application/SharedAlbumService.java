package com.api.pickle.domain.sharedalbum.application;

import com.api.pickle.domain.album.dao.AlbumRepository;
import com.api.pickle.domain.album.domain.Album;
import com.api.pickle.domain.album.domain.SharingStatus;
import com.api.pickle.domain.participant.domain.Participant;
import com.api.pickle.domain.sharedalbum.dto.request.SharedAlbumParticipateRequest;
import com.api.pickle.domain.sharedalbum.dto.response.SharedAlbumParticipateResponse;
import com.api.pickle.domain.sharedalbum.dto.response.SharedLinkResponse;
import com.api.pickle.domain.member.domain.Member;
import com.api.pickle.domain.participant.dao.ParticipantRepository;
import com.api.pickle.domain.sharedalbum.dao.SharedAlbumRepository;
import com.api.pickle.domain.sharedalbum.domain.SharedAlbum;
import com.api.pickle.global.error.exception.CustomException;
import com.api.pickle.global.error.exception.ErrorCode;
import com.api.pickle.global.util.MemberUtil;
import com.api.pickle.global.util.RandomUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
@RequiredArgsConstructor
public class SharedAlbumService {
    private final AlbumRepository albumRepository;
    private final SharedAlbumRepository sharedAlbumRepository;
    private final ParticipantRepository participantRepository;
    private final MemberUtil memberUtil;

    public SharedLinkResponse getSharedAlbumLink(Long albumId, String password){
        final Member currentMember = memberUtil.getCurrentMember();
        Album album = albumRepository.findById(albumId)
                .orElseThrow(() -> new CustomException(ErrorCode.ALBUM_NOT_FOUND));
        album.switchStatus(SharingStatus.PUBLIC);

        validateAlbumOwner(currentMember, album);

        return new SharedLinkResponse(saveSharedAlbum(album, password).getLink());
    }

    public SharedAlbumParticipateResponse participateSharedAlbum(SharedAlbumParticipateRequest request){
        final Member currentMember = memberUtil.getCurrentMember();
        SharedAlbum sharedAlbum = sharedAlbumRepository.findByLink(request.getAlbumLink())
                .orElseThrow(() -> new CustomException(ErrorCode.SHARED_ALBUM_NOT_FOUND));

        validateSharedAlbumPassword(sharedAlbum, request.getAlbumPassword());
        validateAlreadyJoined(currentMember, sharedAlbum.getAlbum());

        Participant participant = Participant.createGuestParticipant(sharedAlbum.getAlbum(), currentMember);
        participantRepository.save(participant);
        return new SharedAlbumParticipateResponse(participant.getAlbum().getId(), participant.getAlbum().getName());
    }

    private void validateAlreadyJoined(Member member, Album album){
        if (participantRepository.findByMemberAndAlbum(member, album).isPresent()){
            throw new CustomException(ErrorCode.MEMBER_ALREADY_JOINED);
        }
    }

    private void validateSharedAlbumPassword(SharedAlbum sharedAlbum, String password){
        if (!sharedAlbum.getPassword().equals(password)){
            throw new CustomException(ErrorCode.SHARED_ALBUM_PASSWORD_MISMATCH);
        }
    }

    public void validateAlbumOwner(Member member, Album album){
        participantRepository.findByMemberAndAlbum(member, album)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_ALBUM_OWNER));
    }

    private SharedAlbum saveSharedAlbum(Album album, String password) {
        final int LINK_LENGTH = 30;
        String newLink = RandomUtil.generateToken(LINK_LENGTH);
        return sharedAlbumRepository.save(SharedAlbum.createSharedAlbum(album, newLink, password));
    }
}
