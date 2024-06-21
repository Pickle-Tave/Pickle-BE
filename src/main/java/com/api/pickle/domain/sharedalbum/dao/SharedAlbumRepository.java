package com.api.pickle.domain.sharedalbum.dao;

import com.api.pickle.domain.sharedalbum.domain.SharedAlbum;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SharedAlbumRepository extends JpaRepository<SharedAlbum, Long> {
}
