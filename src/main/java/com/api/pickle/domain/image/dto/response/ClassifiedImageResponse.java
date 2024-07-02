package com.api.pickle.domain.image.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ClassifiedImageResponse {

    @Schema(description = "분류된 이미지 이중 리스트")
    private List<List<String>> groupedImages;
}
