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
public class UpdateAlbumIdRequest {

    @Schema(description = "앨범의 ID")
    private Long albumId;

    @Schema(description = "앨범에 저장할 이미지들의 ID")
    private List<Long> imageIds;
}
