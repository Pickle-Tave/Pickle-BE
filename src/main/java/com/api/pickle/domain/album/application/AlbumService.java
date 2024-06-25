package com.api.pickle.domain.album.application;

import com.api.pickle.domain.album.dao.AlbumRepository;
import com.api.pickle.domain.album.domain.Album;
import com.api.pickle.domain.album.dto.response.AlbumSearchResponse;
import com.api.pickle.domain.album.dto.response.UpdateAlbumResponse;
import com.api.pickle.domain.member.domain.Member;
import com.api.pickle.domain.participant.dao.ParticipantRepository;
import com.api.pickle.domain.participant.domain.Participant;
import com.api.pickle.domain.sharedalbum.application.SharedAlbumService;
import com.api.pickle.global.error.exception.CustomException;
import com.api.pickle.global.error.exception.ErrorCode;
import com.api.pickle.global.util.MemberUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AlbumService {
    private final AlbumRepository albumRepository;
    private final ParticipantRepository participantRepository;
    private final SharedAlbumService sharedAlbumService;
    private final MemberUtil memberUtil;

    @Transactional
    public void createAlbum(String albumName){
        Member currentMember = memberUtil.getCurrentMember();
        Album newAlbum = Album.createPrivateAlbum(albumName);

        albumRepository.save(newAlbum);
        participantRepository.save(Participant.createHostParticipant(newAlbum, currentMember));
    }

    @Transactional
    public UpdateAlbumResponse updateAlbumName(Long albumId, String newAlbumName){
        final Member currentMember = memberUtil.getCurrentMember();
        Album album = albumRepository.findById(albumId)
                .orElseThrow(() -> new CustomException(ErrorCode.ALBUM_NOT_FOUND));
        sharedAlbumService.validateAlbumOwner(currentMember, album);
        album.updateAlbumName(newAlbumName);
        return new UpdateAlbumResponse(albumId, newAlbumName);
    }

    public List<AlbumSearchResponse> searchKeywordInAlbumOrderByCreatedDateDesc(String keyword) {
        return albumRepository.searchKeywordInAlbumOrderByCreatedDateDesc(keyword);
    }

    public List<AlbumSearchResponse> searchAlbumStatusInAlbumOrderByCreatedDateDesc(String albumStatus) {
        return albumRepository.searchAlbumStatusInAlbumOrderByCreatedDateDesc(albumStatus);
    }

    public List<AlbumSearchResponse> findAllAlbumOfMember(){
        final Member currentMember = memberUtil.getCurrentMember();
        return albumRepository.findAllAlbumOfMemberByCreatedDateDesc(currentMember.getId());
    }
}
