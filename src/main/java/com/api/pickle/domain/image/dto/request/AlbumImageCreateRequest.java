package com.api.pickle.domain.image.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class AlbumImageCreateRequest {

    @Schema(description = "앨범 ID", defaultValue = "1")
    private Long albumId;
}
