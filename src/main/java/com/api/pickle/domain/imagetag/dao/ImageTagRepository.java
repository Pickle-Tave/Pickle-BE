package com.api.pickle.domain.imagetag.dao;

import com.api.pickle.domain.imagetag.domain.ImageTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ImageTagRepository extends JpaRepository<ImageTag, Long>, ImageTagRepositoryCustom {
    @Modifying
    @Query("DELETE FROM ImageTag it WHERE it.image.album.id = :albumId")
    void deleteByAlbumId(@Param("albumId") Long albumId);
}
