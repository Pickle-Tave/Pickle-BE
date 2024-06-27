package com.api.pickle.domain.bookmark.dao;

import com.api.pickle.domain.bookmark.domain.Bookmark;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookmarkRepository extends JpaRepository<Bookmark, Long> , BookmarkRepositoryCustom{
}
