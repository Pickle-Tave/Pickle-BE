package com.api.pickle.infra.config.s3;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class S3Config {

    private final S3Properties s3Properties;

    @Bean
    public AmazonS3 amazonS3() {
        AWSCredentials credentials =
                new BasicAWSCredentials(s3Properties.getAccessKey(), s3Properties.getSecretKey());
        AwsClientBuilder.EndpointConfiguration endpointConfiguration =
                new AwsClientBuilder.EndpointConfiguration(
                        s3Properties.getEndpoint(), s3Properties.getRegion());

        return AmazonS3ClientBuilder.standard()
                .withEndpointConfiguration(endpointConfiguration)
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .build();
    }
}