package com.api.pickle.infra.s3.api;

import com.api.pickle.infra.s3.application.AwsS3Service;
import com.api.pickle.infra.s3.dto.response.PresignedUrlResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@Tag(name = "6. [AWS S3 Presigned URL]", description = "AWS S3 Presigned URL 관련 API입니다.")
@RestController
@RequestMapping("/v1/file")
@RequiredArgsConstructor
public class AwsS3Controller {

    private final AwsS3Service awsS3Service;

    @Operation(summary = "업로드용 Presigned URL", description = "업로드를 위한 Presigned URL 생성")
    @GetMapping("/presigned/upload")
    public PresignedUrlResponse getPresignedUrlToUpload(@RequestParam(value = "filename") String fileName)
        throws IOException {
        return awsS3Service.getPresignedUrlToUpload(fileName);
    }

    @Operation(summary = "다운로드용 Presigned URL", description = "다운로드를 위한 Presigned URL 생성")
    @GetMapping("/presigned/download")
    public PresignedUrlResponse getPresignedUrlToDownload(@RequestParam(value = "filename") String fileName)
            throws IOException {
        return awsS3Service.getPresignedUrlToDownload(fileName);
    }
}
