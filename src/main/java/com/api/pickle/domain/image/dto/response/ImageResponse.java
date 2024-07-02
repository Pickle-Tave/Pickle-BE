package com.api.pickle.domain.image.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
@Builder
public class ImageResponse {

    @Schema(description = "해시태그 설정한 이미지들의 ID")
    private List<Long> imageIds;
}
