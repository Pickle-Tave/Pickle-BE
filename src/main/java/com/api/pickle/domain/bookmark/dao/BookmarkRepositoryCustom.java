package com.api.pickle.domain.bookmark.dao;

import com.api.pickle.domain.bookmark.domain.Bookmark;

public interface BookmarkRepositoryCustom {
    Bookmark findByAlbumIdAndMemberId(Long albumId, Long memberId);
}
