package com.api.pickle.domain.image.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DeleteImageRequest {

    @Schema(description = "삭제하려는 이미지 ID 리스트")
    List<Long> imageIds;
}
