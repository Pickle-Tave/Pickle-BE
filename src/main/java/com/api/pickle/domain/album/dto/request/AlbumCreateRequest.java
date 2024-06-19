package com.api.pickle.domain.album.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AlbumCreateRequest {
    @Schema(description = "생성할 앨범의 이름")
    private String name;
}
