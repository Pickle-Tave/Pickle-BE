package com.api.pickle.domain.album.application;

import com.api.pickle.domain.album.dao.AlbumRepository;
import com.api.pickle.domain.album.domain.Album;
import com.api.pickle.domain.album.dto.response.AlbumSearchResponse;
import com.api.pickle.domain.album.dto.response.UpdateAlbumResponse;
import com.api.pickle.domain.bookmark.application.BookmarkService;
import com.api.pickle.domain.member.domain.Member;
import com.api.pickle.domain.participant.dao.ParticipantRepository;
import com.api.pickle.domain.participant.domain.Participant;
import com.api.pickle.domain.sharedalbum.application.SharedAlbumService;
import com.api.pickle.global.error.exception.CustomException;
import com.api.pickle.global.error.exception.ErrorCode;
import com.api.pickle.global.util.MemberUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
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
    private final BookmarkService bookmarkService;
    private final MemberUtil memberUtil;

    @Transactional
    public void createAlbum(String albumName){
        Member currentMember = memberUtil.getCurrentMember();
        Album newAlbum = Album.createPrivateAlbum(albumName);

        albumRepository.save(newAlbum);

        Participant participant = Participant.createHostParticipant(newAlbum, currentMember);
        participantRepository.save(participant);
        bookmarkService.createBookmark(participant);
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

    public Slice<AlbumSearchResponse> searchKeywordInAlbumOrderByCreatedDateDesc(String keyword, int pageSize, Long lastAlbumId) {
        final Member currentMember = memberUtil.getCurrentMember();
        return albumRepository.searchKeywordInAlbumOrderByCreatedDateDesc(currentMember.getId(), keyword, pageSize, lastAlbumId);
    }

    public Slice<AlbumSearchResponse> searchAlbumStatusInAlbumOrderByCreatedDateDesc(String albumStatus, int pageSize, Long lastAlbumId) {
        final Member currentMember = memberUtil.getCurrentMember();
        return albumRepository.searchAlbumStatusInAlbumOrderByCreatedDateDesc(currentMember.getId(), albumStatus, pageSize, lastAlbumId);
    }

    public Slice<AlbumSearchResponse> findAllAlbumOfMember(int pageSize, Long lastAlbumId) {
        final Member currentMember = memberUtil.getCurrentMember();
        return albumRepository.findAllAlbumOfMemberByCreatedDateDesc(currentMember.getId(), pageSize, lastAlbumId);
    }
}
