package com.api.pickle.domain.image.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class PresignedUrlRequest {

    @Schema(description = "업로드 하고자 하는 사진 수")
    private int imageUploadSize;
}
