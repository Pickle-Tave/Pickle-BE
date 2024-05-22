package com.api.pickle.infra.s3.api;

import com.api.pickle.infra.s3.application.AwsS3Service;
import com.api.pickle.infra.s3.dto.response.PresignedUrlResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/v1/file")
@RequiredArgsConstructor
public class AwsS3Controller {

    private final AwsS3Service awsS3Service;

    @GetMapping("/presigned/upload")
    public PresignedUrlResponse getPresignedUrlToUpload(@RequestParam(value = "filename") String fileName)
        throws IOException {
        return awsS3Service.getPresignedUrlToUpload(fileName);
    }

    @GetMapping("/presigned/download")
    public PresignedUrlResponse getPresignedUrlToDownload(@RequestParam(value = "filename") String fileName)
            throws IOException {
        return awsS3Service.getPresignedUrlToDownload(fileName);
    }
}
