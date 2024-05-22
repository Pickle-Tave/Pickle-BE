package com.api.pickle.infra.s3.application;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.api.pickle.infra.s3.dto.response.PresignedUrlResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class AwsS3Service {

    private final AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    public PresignedUrlResponse getPresignedUrlToUpload(String fileName) {

        GeneratePresignedUrlRequest generatePresignedUrlRequest = new GeneratePresignedUrlRequest(bucket, fileName)
                .withMethod(HttpMethod.PUT)
                .withExpiration(getPresignedUrlExpiration()); // 제한시간 설정

        return PresignedUrlResponse.builder()
                .url(amazonS3.generatePresignedUrl(generatePresignedUrlRequest).toString())
                .build();
    }

    public PresignedUrlResponse getPresignedUrlToDownload(String fileName) {
        GeneratePresignedUrlRequest generatePresignedUrlRequest = new GeneratePresignedUrlRequest(bucket, fileName)
                .withMethod(HttpMethod.GET)
                .withExpiration(getPresignedUrlExpiration()); // 제한시간 설정

        return PresignedUrlResponse.builder()
                .url(amazonS3.generatePresignedUrl(generatePresignedUrlRequest).toString())
                .build();
    }

    private static Date getPresignedUrlExpiration() {
        Date expiration = new Date();
        long expTime = expiration.getTime();
        expTime += TimeUnit.MINUTES.toMillis(3); // 3 Minute
        expiration.setTime(expTime);

        return expiration;
    }
}
