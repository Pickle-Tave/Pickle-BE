package com.api.pickle.domain.album.dto.response;

import com.querydsl.core.annotations.QueryProjection;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class FetchAlbumImagesResponse {
    @Schema(description = "검색된 이미지의 id")
    private Long imageId;

    @Schema(description = "검색된 이미지의 해시태그")
    private String tagName;

    @Schema(description = "검색된 이미지의 url")
    private String imageUrl;

    @QueryProjection
    public FetchAlbumImagesResponse(Long imageId, String tagName, String imageUrl){
        this.imageId = imageId;
        this.tagName = tagName;
        this.imageUrl = imageUrl;
    }
}
