package com.api.pickle.domain.bookmark.dao;

import com.api.pickle.domain.album.dto.response.AlbumSearchResponse;
import com.api.pickle.domain.bookmark.dto.RedisBookmarkStatusDto;
import org.springframework.data.domain.Slice;


public interface BookmarkRepositoryCustom {

    Slice<AlbumSearchResponse> findAlbumByBookmarks(Long memberId, RedisBookmarkStatusDto markLists, int pageSize, Long lastAlbumId);
}
