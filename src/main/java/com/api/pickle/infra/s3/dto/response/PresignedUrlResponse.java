package com.api.pickle.infra.s3.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Schema(description = "AWS S3 응답 URL")
@Data
@Builder
public class PresignedUrlResponse {
    // S3 Presigned URL
    private String url;
}
