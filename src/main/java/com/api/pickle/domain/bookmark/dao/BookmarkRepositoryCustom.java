package com.api.pickle.domain.bookmark.dao;

import com.api.pickle.domain.album.dto.response.AlbumSearchResponse;
import com.api.pickle.domain.bookmark.domain.Bookmark;
import org.springframework.data.domain.Slice;

import java.util.List;

public interface BookmarkRepositoryCustom {
    List<Bookmark> findAllByMemberId (Long memberId);

    Slice<AlbumSearchResponse> findAlbumByBookmarks(List<Bookmark> bookmarks, int pageSize, Long lastAlbumId);
}
