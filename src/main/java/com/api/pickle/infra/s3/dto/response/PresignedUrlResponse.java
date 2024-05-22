package com.api.pickle.infra.s3.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PresignedUrlResponse {
    // S3 Presigned URL
    private String url;
}
