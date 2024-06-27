package com.api.pickle.domain.bookmark.application;

import com.api.pickle.domain.album.dao.AlbumRepository;
import com.api.pickle.domain.album.domain.Album;
import com.api.pickle.domain.album.dto.response.AlbumSearchResponse;
import com.api.pickle.domain.bookmark.dao.BookmarkRepository;
import com.api.pickle.domain.bookmark.domain.Bookmark;
import com.api.pickle.domain.bookmark.domain.MarkStatus;
import com.api.pickle.domain.bookmark.dto.response.MarkedResponse;
import com.api.pickle.domain.member.domain.Member;
import com.api.pickle.domain.participant.dao.ParticipantRepository;
import com.api.pickle.domain.participant.domain.Participant;
import com.api.pickle.global.error.exception.CustomException;
import com.api.pickle.global.error.exception.ErrorCode;
import com.api.pickle.global.util.MemberUtil;
import com.api.pickle.infra.config.redis.RedisService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.api.pickle.global.common.constants.RedisConstants.*;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BookmarkService {
    private final RedisService redisService;
    private final BookmarkRepository bookmarkRepository;
    private final ParticipantRepository participantRepository;
    private final AlbumRepository albumRepository;
    private final MemberUtil memberUtil;

    @Transactional
    public void createBookmark(Participant participant){
        bookmarkRepository.save(Bookmark.builder().participant(participant).build());
    }

    @Transactional
    public MarkedResponse changeMarkByAlbumId(Long albumId, MarkStatus status) {
        Participant participant = findParticipantByAlbumId(albumId);
        markAlbumOfMember(participant, status);
        return new MarkedResponse(participant.getAlbum().getId(), MarkStatus.MARKED);
    }

    public List<AlbumSearchResponse> searchAlbumInfoWithBookmarked(){
        List<AlbumSearchResponse> response =  bookmarkRepository.findAlbumByBookmarks(findAlbumsMarked());
        if (response.isEmpty()){
            throw new CustomException(ErrorCode.BOOKMARKED_ALBUM_NOT_FOUND);
        }
        return response;
    }

    private List<Bookmark> findAlbumsMarked(){
        final Member currentMember = memberUtil.getCurrentMember();
        Map<Long, Bookmark> bookmarks = bookmarkRepository.findAllByMemberId(currentMember.getId())
                        .stream()
                        .collect(Collectors.toMap(Bookmark::getId, Function.identity()));
        Map<Object, Object> redisData = redisService.getHash(concatKeyAndUserId(currentMember.getId()));

        for (Map.Entry<Object, Object> entry : redisData.entrySet()){
            Long bookmarkId = Long.parseLong(entry.getKey().toString());
            MarkStatus markStatus = MarkStatus.valueOf(entry.getValue().toString());
            Bookmark bookmark = bookmarks.get(bookmarkId);
            if (bookmark != null){
                bookmark.updateMarkStatus(markStatus);
            }
        }

        return bookmarks.values().stream()
                .filter(bookmark -> bookmark.getMarkStatus() == MarkStatus.MARKED)
                .collect(Collectors.toList());
    }

    private String concatKeyAndUserId(Long userId){
        return BOOKMARK_USER_ID_KEY.concat(userId.toString());
    }

    private void markAlbumOfMember(Participant participant, MarkStatus status) {
        Bookmark bookmark = bookmarkRepository.findByParticipant(participant)
                .orElseThrow(() -> new CustomException(ErrorCode.BOOKMARK_NOT_FOUND));
        redisService.putToBookmarkHash(generateRedisKey(BOOKMARK_USER_ID_KEY, participant.getMember().getId()),
                bookmark.getId().toString(),
                status);
    }

    private String generateRedisKey(String key, Long id){
        return key.concat(id.toString());
    }

    private Participant findParticipantByAlbumId(Long albumId){
        final Member currentMember = memberUtil.getCurrentMember();
        Album album = albumRepository.findById(albumId)
                .orElseThrow(() -> new CustomException(ErrorCode.ALBUM_NOT_FOUND));
        return participantRepository.findByMemberAndAlbum(currentMember, album)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_ALBUM_OWNER));
    }
}
