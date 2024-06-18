package com.api.pickle.domain.sharedalbum.application;

import com.api.pickle.domain.album.dao.AlbumRepository;
import com.api.pickle.domain.album.domain.Album;
import com.api.pickle.domain.album.domain.SharingStatus;
import com.api.pickle.domain.sharedalbum.dto.response.SharedLinkResponse;
import com.api.pickle.domain.member.domain.Member;
import com.api.pickle.domain.participant.dao.ParticipantRepository;
import com.api.pickle.domain.participant.domain.Participant;
import com.api.pickle.domain.sharedalbum.dao.SharedAlbumRepository;
import com.api.pickle.domain.sharedalbum.domain.SharedAlbum;
import com.api.pickle.global.error.exception.CustomException;
import com.api.pickle.global.error.exception.ErrorCode;
import com.api.pickle.global.util.MemberUtil;
import com.api.pickle.global.util.RandomUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional
@Service
@RequiredArgsConstructor
public class SharedAlbumService {
    private final AlbumRepository albumRepository;
    private final SharedAlbumRepository sharedAlbumRepository;
    private final ParticipantRepository participantRepository;
    private final MemberUtil memberUtil;

    public SharedLinkResponse getSharedAlbumLink(Long albumId){
        final Member currentMember = memberUtil.getCurrentMember();
        Album album = albumRepository.findById(albumId)
                .orElseThrow(() -> new CustomException(ErrorCode.ALBUM_NOT_FOUND));
        album.switchStatus(SharingStatus.PUBLIC);

        validateAlbumOwner(currentMember, album);

        return new SharedLinkResponse(saveSharedAlbum(album).getLink());
    }

    private void validateAlbumOwner(Member member, Album album){
        Optional<Participant> optionalParticipant = participantRepository.findByMemberAndAlbum(member, album);
        if (optionalParticipant.isEmpty()){
            throw new CustomException(ErrorCode.NOT_ALBUM_OWNER);
        }
    }

    private SharedAlbum saveSharedAlbum(Album album) {
        final int LINK_LENGTH = 30;
        final int PASSWORD_LENGTH = 10;

        String newLink = RandomUtil.generateToken(LINK_LENGTH);
        String password = RandomUtil.generateToken(PASSWORD_LENGTH);
        return sharedAlbumRepository.save(SharedAlbum.createSharedAlbum(album, newLink, password));
    }
}
