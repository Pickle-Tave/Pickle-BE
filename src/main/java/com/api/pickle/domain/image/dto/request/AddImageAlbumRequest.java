package com.api.pickle.domain.image.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class AddImageAlbumRequest {

    @Schema(description = "사진을 추가하려는 앨범 ID")
    private Long albumId;

    @Schema(description = "업로드 한 이미지 URL 리스트")
    private List<String> imageUrls;
}
