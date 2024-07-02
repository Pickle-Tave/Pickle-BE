package com.api.pickle.domain.image.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class PresignedUrlResponse {

    @Schema(description = "발급된 presigned url 리스트")
    private List<String> presignedUrls;
}