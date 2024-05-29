package com.api.pickle.domain.image.dto.response;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class PresignedUrlResponse {

    private String presignedUrl;
}