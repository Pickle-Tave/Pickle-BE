package com.api.pickle.domain.album.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UpdateAlbumRequest {
    @Schema(description = "앨범의 수정될 이름")
    private String newAlbumName;
}
