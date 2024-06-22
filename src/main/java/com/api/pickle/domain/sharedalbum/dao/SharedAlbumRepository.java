package com.api.pickle.domain.sharedalbum.dao;

import com.api.pickle.domain.sharedalbum.domain.SharedAlbum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SharedAlbumRepository extends JpaRepository<SharedAlbum, Long> {
    Optional<SharedAlbum> findByLink(String Link);
}
