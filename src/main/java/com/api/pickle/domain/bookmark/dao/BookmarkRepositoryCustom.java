package com.api.pickle.domain.bookmark.dao;

import com.api.pickle.domain.album.dto.response.AlbumSearchResponse;
import com.api.pickle.domain.bookmark.domain.Bookmark;
import com.api.pickle.domain.bookmark.dto.RedisBookmarkStatusDto;
import com.api.pickle.domain.participant.domain.Participant;
import org.springframework.data.domain.Slice;

import java.util.List;


public interface BookmarkRepositoryCustom {

    Slice<AlbumSearchResponse> findAlbumByBookmarks(Long memberId, RedisBookmarkStatusDto markLists, int pageSize, Long lastAlbumId);
    List<Bookmark> findByParticipant(List<Participant> participants);
}
