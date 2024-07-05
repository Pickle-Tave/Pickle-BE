package com.api.pickle.domain.image.dao;

import com.api.pickle.domain.image.domain.Image;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface ImageRepository extends JpaRepository<Image, Long>,ImageRepositoryCustom {
    List<Image> findAllByAlbumId(Long albumId);
}
