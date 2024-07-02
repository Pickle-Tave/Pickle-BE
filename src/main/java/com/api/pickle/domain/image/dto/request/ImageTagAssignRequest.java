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

    @Schema(description ="지정하고자 하는 해시태그 리스트")
    private List<Long> hashtagIds;
}
