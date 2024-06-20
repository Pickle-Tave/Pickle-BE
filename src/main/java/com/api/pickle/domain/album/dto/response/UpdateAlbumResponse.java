package com.api.pickle.domain.album.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UpdateAlbumResponse {
    @Schema(description = "수정된 앨범 id")
    private Long albumId;
    @Schema(description = "수정된 앨범의 이름")
    private String updatedAlbumName;
}
