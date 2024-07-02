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
public class UpdateAllAlbumIdRequest {

    @Schema(description = "여러 앨범에 대한 이미지 저장 요청 목록")
    private List<UpdateAlbumIdRequest> updateAlbumIdRequestList;
}
