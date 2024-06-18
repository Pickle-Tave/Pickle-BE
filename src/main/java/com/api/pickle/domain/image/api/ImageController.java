package com.api.pickle.domain.image.api;

import com.api.pickle.domain.image.application.ImageService;
import com.api.pickle.domain.image.dto.request.AlbumImageCreateRequest;
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

    @Operation(summary = "앨범 이미지 Presigned URL 생성", description = "앨범 이미지 Presigned URL을 생성합니다.")
    @PostMapping("/album/upload-url")
    public PresignedUrlResponse albumPresignedUrlCreate(@RequestBody AlbumImageCreateRequest request) {
        return imageService.createAlbumPresignedUrl(request);
    }
}