package com.api.pickle.domain.image.api;

import com.api.pickle.domain.image.application.ImageService;
import com.api.pickle.domain.image.dto.request.ImageClassificationRequest;
import com.api.pickle.domain.image.dto.request.PresignedUrlRequest;
import com.api.pickle.domain.image.dto.response.ClassifiedImageResponse;
import com.api.pickle.domain.image.dto.response.PresignedUrlResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "이미지 API", description = "이미지 API입니다.")
@RestController
@RequiredArgsConstructor
public class ImageController {

    private final ImageService imageService;

    @Operation(summary = "이미지 Presigned URL 생성", description = "이미지 Presigned URL을 생성합니다.")
    @PostMapping("/images/upload-url")
    public PresignedUrlResponse imagePresignedUrlCreate(@RequestBody PresignedUrlRequest request) {
        return imageService.createImagePresignedUrl(request);
    }

    @Operation(summary = "이미지 분류 결과", description = "이미지 분류 결과를 반환합니다.")
    @PostMapping("/images/classify")
    public ClassifiedImageResponse classifyImages(@RequestBody ImageClassificationRequest request) {
        return imageService.classifyImages(request);
    }
}