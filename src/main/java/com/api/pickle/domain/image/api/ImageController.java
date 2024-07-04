package com.api.pickle.domain.image.api;

import com.api.pickle.domain.album.dto.response.FetchAlbumImagesResponse;
import com.api.pickle.domain.image.application.ImageService;
import com.api.pickle.domain.image.dto.request.*;
import com.api.pickle.domain.image.dto.response.ClassifiedImageResponse;
import com.api.pickle.domain.image.dto.response.ImageResponse;
import com.api.pickle.domain.image.dto.response.PresignedUrlResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Slice;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    @PostMapping("/save-album")
    public ResponseEntity<Void> updateImageAlbum(@RequestBody UpdateAllAlbumIdRequest request) {
        imageService.updateAllImageAlbum(request);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @Operation(summary = "앨범에 이미지 추가", description = "앨범에 이미지를 추가합니다.")
    @PostMapping("/add-album")
    public ResponseEntity<Void> addImageAlbum(@RequestBody AddImageAlbumRequest request) {
        imageService.addImageAlbum(request);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @Operation(summary = "이미지 삭제", description = "이미지를 삭제합니다.")
    @DeleteMapping("/delete-image")
    public ResponseEntity<Void> deleteImage(@RequestBody DeleteImageRequest request) {
        imageService.deleteImage(request);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @Operation(summary = "해시 태그로 이미지 조회", description = "앨범 내의 이미지들에 대하여 해시태그 기준으로 검색합니다.")
    @GetMapping("/search/tag")
    public Slice<FetchAlbumImagesResponse> searchImagesByTag(@Parameter(description = "조회할 앨범의 id", example = "1")
                                                           @RequestParam Long albumId,
                                                             @Parameter(description = "조회할 해시태그")
                                                           @RequestParam String tagName,
                                                             @Parameter(description = "이전 페이지의 마지막 이미지 ID (첫 페이지는 비워두세요.)")
                                                           @RequestParam(required = false) Long lastImageId,
                                                             @Parameter(description = "페이지당 이미지 수", example = "1")
                                                           @RequestParam(value = "size") int pageSize){
        return imageService.searchImageByTag(albumId, tagName, pageSize, lastImageId);
    }
}