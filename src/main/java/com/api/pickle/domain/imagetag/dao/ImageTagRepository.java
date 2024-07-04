package com.api.pickle.domain.imagetag.dao;

import com.api.pickle.domain.imagetag.domain.ImageTag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageTagRepository extends JpaRepository<ImageTag, Long>, ImageTagRepositoryCustom {
}
