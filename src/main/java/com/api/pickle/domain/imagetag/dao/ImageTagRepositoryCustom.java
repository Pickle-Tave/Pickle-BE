package com.api.pickle.domain.imagetag.dao;

import com.api.pickle.domain.image.domain.Image;
import com.api.pickle.domain.imagetag.domain.ImageTag;

import java.util.List;

public interface ImageTagRepositoryCustom {

    List<ImageTag> findByImage(List<Image> images);
}
