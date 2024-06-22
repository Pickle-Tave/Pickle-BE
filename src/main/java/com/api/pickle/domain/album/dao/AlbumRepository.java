package com.api.pickle.domain.album.dao;

import com.api.pickle.domain.album.domain.Album;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlbumRepository extends JpaRepository<Album, Long>, AlbumRepositoryCustom {
}
