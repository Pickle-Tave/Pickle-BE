package com.api.pickle.domain.tag.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TagCreateRequest {
    @Schema(description = "생성할 해시태그의 이름")
    private String name;
}
