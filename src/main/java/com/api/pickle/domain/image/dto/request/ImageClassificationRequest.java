package com.api.pickle.domain.image.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class ImageClassificationRequest {

    @Schema(description = "업로드 한 이미지 URL 리스트")
    private List<String> imageUrls;

    @Schema(description = "그룹 간 유사도 설정 (true: 강하게, false: 약하게)")
    private Boolean strongClustering;

    @Schema(description = "선명하지 않은 사진 제외 (true: 체크)")
    private Boolean eyeClosing;

    @Schema(description = "눈 감은 사진 제외 (true: 체크)")
    private Boolean blurred;
}
