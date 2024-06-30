package com.api.pickle.domain.image.dto.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class PresignedUrlResponse {

    private List<String> presignedUrls;
}