package com.api.pickle.domain.image.api;

import com.api.pickle.domain.image.application.ImageService;
import com.api.pickle.domain.image.dto.request.*;
import com.api.pickle.domain.image.dto.response.ClassifiedImageResponse;
import com.api.pickle.domain.image.dto.response.ImageResponse;
import com.api.pickle.domain.image.dto.response.PresignedUrlResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "이미지 API", description = "이미지 API입니다.")
@RestController
@RequiredArgsConstructor
@RequestMapping("/images")
public class ImageController {

    private final ImageService imageService;

    @Operation(summary = "이미지 Presigned URL 생성", description = "이미지 Presigned URL을 생성합니다.")
    @PostMapping("/upload-url")
    public PresignedUrlResponse imagePresignedUrlCreate(@RequestBody PresignedUrlRequest request) {
        return imageService.createImagePresignedUrl(request);
    }

    @Operation(summary = "이미지 분류 결과", description = "이미지 분류 결과를 반환합니다.")
    @PostMapping("/classify")
    public ClassifiedImageResponse classifyImages(@RequestBody ImageClassificationRequest request) {
        return imageService.classifyImages(request);
    }

    @Operation(summary = "이미지 해시태그 설정", description = "이미지에 해시태그를 설정합니다.")
    @PostMapping("/assign/tag")
    public ImageResponse assignImageTags(@RequestBody ImageTagAssignRequest request) {
        return imageService.assignImageTags(request);
    }

    @Operation(summary = "앨범에 분류된 이미지 저장", description = "앨범에 분류된 이미지를 저장합니다.")
    @PostMapping("/save/album")
    public ResponseEntity<Void> updateImageAlbum(@RequestBody UpdateAllAlbumIdRequest request) {
        imageService.updateAllImageAlbum(request);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @Operation(summary = "앨범에 이미지 추가", description = "앨범에 이미지를 추가합니다.")
    @PostMapping("/add/album")
    public ResponseEntity<Void> addImageAlbum(@RequestBody AddImageAlbumRequest request) {
        imageService.addImageAlbum(request);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}