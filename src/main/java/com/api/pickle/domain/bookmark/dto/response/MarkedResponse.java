package com.api.pickle.domain.bookmark.dto.response;

import com.api.pickle.domain.bookmark.domain.MarkStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class MarkedResponse {
    @Schema(description = "즐겨찾기가 적용된 앨범 id", example = "1")
    private Long albumId;
    @Schema(description = "즐겨찾기가 적용된 상태", example = "MARKED")
    private MarkStatus markStatus;
}
