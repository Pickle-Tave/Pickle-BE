package com.api.pickle.domain.bookmark.dao;

import com.api.pickle.domain.album.dto.response.AlbumSearchResponse;
import com.api.pickle.domain.bookmark.domain.Bookmark;

import java.util.List;

public interface BookmarkRepositoryCustom {
    List<Bookmark> findAllByMemberId (Long memberId);

    List<AlbumSearchResponse> findAlbumByBookmarks(List<Bookmark> bookmarks);
}
