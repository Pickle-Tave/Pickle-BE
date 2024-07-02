package com.api.pickle.domain.image.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class ImageTagAssignRequest {

    @Schema(description = "분류된 이미지 그룹")
    private List<String> imageUrls;

    @Schema(description ="사용자 지정 해시태그")
    private List<String> hashtags;
}
