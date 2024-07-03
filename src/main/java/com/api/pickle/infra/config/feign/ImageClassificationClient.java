package com.api.pickle.infra.config.feign;

import com.api.pickle.domain.image.dto.request.ImageClassificationRequest;
import com.api.pickle.domain.image.dto.response.ClassifiedImageResponse;
import com.api.pickle.global.config.feign.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(
        name = "imageClassificationClient",
        url = "${fastapi.serverUrl}",
        configuration = FeignConfig.class)
public interface ImageClassificationClient {
    @PostMapping(value = "${fastapi.endpoint}")
    ClassifiedImageResponse getClassifiedImages(@RequestBody ImageClassificationRequest request);
}