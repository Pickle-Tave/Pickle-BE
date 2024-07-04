package com.api.pickle.domain.album.application;

import com.api.pickle.domain.album.dao.AlbumRepository;
import com.api.pickle.domain.album.domain.Album;
import com.api.pickle.domain.album.dto.response.AlbumSearchResponse;
import com.api.pickle.domain.album.dto.response.FetchAlbumImagesResponse;
import com.api.pickle.domain.album.dto.response.UpdateAlbumResponse;
import com.api.pickle.domain.image.dao.ImageRepository;
import com.api.pickle.domain.bookmark.application.BookmarkService;
import com.api.pickle.domain.imagetag.dao.ImageTagRepository;
import com.api.pickle.domain.member.domain.Member;
import com.api.pickle.domain.participant.dao.ParticipantRepository;
import com.api.pickle.domain.participant.domain.HostStatus;
import com.api.pickle.domain.participant.domain.Participant;
import com.api.pickle.domain.sharedalbum.application.SharedAlbumService;
import com.api.pickle.domain.sharedalbum.dao.SharedAlbumRepository;
import com.api.pickle.global.error.exception.CustomException;
import com.api.pickle.global.error.exception.ErrorCode;
import com.api.pickle.global.util.MemberUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AlbumService {
    private final AlbumRepository albumRepository;
    private final ParticipantRepository participantRepository;
    private final ImageRepository imageRepository;
    private final SharedAlbumService sharedAlbumService;
    private final BookmarkService bookmarkService;
    private final MemberUtil memberUtil;
    private final SharedAlbumRepository sharedAlbumRepository;
    private final ImageTagRepository imageTagRepository;

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
        Slice<AlbumSearchResponse> response =  albumRepository.searchKeywordInAlbumOrderByCreatedDateDesc(currentMember.getId(), keyword, pageSize, lastAlbumId);
        return bookmarkService.reflectRedisMarkStatus(response, currentMember.getId());
    }

    public Slice<AlbumSearchResponse> searchAlbumStatusInAlbumOrderByCreatedDateDesc(String albumStatus, int pageSize, Long lastAlbumId) {
        final Member currentMember = memberUtil.getCurrentMember();
        Slice<AlbumSearchResponse> response =  albumRepository.searchAlbumStatusInAlbumOrderByCreatedDateDesc(currentMember.getId(), albumStatus, pageSize, lastAlbumId);
        return bookmarkService.reflectRedisMarkStatus(response, currentMember.getId());
    }

    public Slice<AlbumSearchResponse> findAllAlbumOfMember(int pageSize, Long lastAlbumId) {
        final Member currentMember = memberUtil.getCurrentMember();
        Slice<AlbumSearchResponse> response =  albumRepository.findAllAlbumOfMemberByCreatedDateDesc(currentMember.getId(), pageSize, lastAlbumId);
        return bookmarkService.reflectRedisMarkStatus(response, currentMember.getId());
    }

    @Transactional
    public void deleteAlbum(Long albumId) {
        final Member currentMember = memberUtil.getCurrentMember();

        Participant findMember = participantRepository.findParticipant(currentMember,albumId)
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));

        if (!findMember.getHostStatus().equals(HostStatus.HOST)) throw new CustomException(ErrorCode.MEMBER_NOT_HOST);
        deleteImageParticipantSharedAlbum(albumId);
        albumRepository.delete(findMember.getAlbum());
    }


    private void deleteImageParticipantSharedAlbum(Long albumId) {
        imageTagRepository.deleteByAlbumId(albumId);
        imageRepository.deleteAllByAlbumId(albumId);
        participantRepository.deleteAllByAlbumId(albumId);
        sharedAlbumRepository.deleteByAlbumId(albumId);
    }

    public Slice<FetchAlbumImagesResponse> findImagesFromAlbum(Long albumId, int pageSize, Long lastAlbumId){
        final Member currentMember = memberUtil.getCurrentMember();
        validateAlbumWithMember(albumId, currentMember);
        return imageRepository.findAllImagesByCreatedDateDesc(albumId, pageSize, lastAlbumId);
    }

    public void validateAlbumWithMember(Long albumId, Member member){
        participantRepository.findParticipant(member, albumId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_ALBUM_OWNER));
    }
}
