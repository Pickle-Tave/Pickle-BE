package com.api.pickle.domain.image.application;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.Headers;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.api.pickle.domain.album.dao.AlbumRepository;
import com.api.pickle.domain.image.dao.ImageRepository;
import com.api.pickle.domain.image.domain.Image;
import com.api.pickle.domain.image.dto.request.ImageClassificationRequest;
import com.api.pickle.domain.image.dto.request.ImageTagAssignRequest;
import com.api.pickle.domain.image.dto.request.PresignedUrlRequest;
import com.api.pickle.domain.image.dto.response.ClassifiedImageResponse;
import com.api.pickle.domain.image.dto.response.ImageResponse;
import com.api.pickle.domain.image.dto.response.PresignedUrlResponse;
import com.api.pickle.domain.imagetag.dao.ImageTagRepository;
import com.api.pickle.domain.imagetag.domain.ImageTag;
import com.api.pickle.domain.member.domain.Member;
import com.api.pickle.domain.membertag.dao.MemberTagRepository;
import com.api.pickle.domain.tag.dao.TagRepository;
import com.api.pickle.domain.tag.domain.Tag;
import com.api.pickle.global.error.exception.CustomException;
import com.api.pickle.global.error.exception.ErrorCode;
import com.api.pickle.global.util.MemberUtil;
import com.api.pickle.infra.config.feign.ImageClassificationClient;
import com.api.pickle.infra.config.s3.S3Properties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

import static com.api.pickle.domain.image.domain.Image.createImage;
import static com.api.pickle.domain.imagetag.domain.ImageTag.createImageTag;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class ImageService {

    private final MemberUtil memberUtil;
    private final S3Properties s3Properties;
    private final AmazonS3 amazonS3;
    private final ImageRepository imageRepository;
    private final AlbumRepository albumRepository;
    private final ImageClassificationClient imageClassificationClient;
    private final TagRepository tagRepository;
    private final MemberTagRepository memberTagRepository;
    private final ImageTagRepository imageTagRepository;

    public PresignedUrlResponse createImagePresignedUrl(PresignedUrlRequest request) {
        final Member member = memberUtil.getCurrentMember();

        List<String> presignedUrls = new ArrayList<>();

        IntStream.range(0, request.getImageUploadSize())
                .forEach(i -> {
                            String imageKey = generateUUID();
                            String fileName = createFileName(member.getId(), imageKey);

                            GeneratePresignedUrlRequest generatePresignedUrlRequest =
                                    createGeneratePresignedUrlRequest(s3Properties.getBucket(), fileName);

                            String presignedUrl = amazonS3.generatePresignedUrl(generatePresignedUrlRequest).toString();

                            presignedUrls.add(presignedUrl);
                        }
                );

        return PresignedUrlResponse.builder()
                .presignedUrls(presignedUrls)
                .build();
    }

    private String generateUUID() {
        return UUID.randomUUID().toString();
    }

    private String createFileName(Long memberId, String imageKey) {
        return memberId
                + "/"
                + imageKey;
    }

    private GeneratePresignedUrlRequest createGeneratePresignedUrlRequest(String bucket, String fileName) {

        GeneratePresignedUrlRequest generatePresignedUrlRequest = new GeneratePresignedUrlRequest(bucket, fileName)
                .withKey(fileName)
                .withMethod(HttpMethod.PUT)
                .withExpiration(getPresignedUrlExpiration());

        generatePresignedUrlRequest.addRequestParameter(
                Headers.S3_CANNED_ACL, CannedAccessControlList.PublicRead.toString()
        );

        return generatePresignedUrlRequest;
    }

    private Date getPresignedUrlExpiration() {
        Date expiration = new Date();
        long expTime = expiration.getTime();
        expTime += TimeUnit.MINUTES.toMillis(3);
        expiration.setTime(expTime);

        return expiration;
    }

    public ClassifiedImageResponse classifyImages(ImageClassificationRequest request) {
        final Member currentMember = memberUtil.getCurrentMember();

        ClassifiedImageResponse response = imageClassificationClient.getClassifiedImages(request);

        response.getGroupedImages().stream()
                .flatMap(Collection::stream)
                .map(imageUrl -> createImage(currentMember, imageUrl))
                .forEach(imageRepository::save);

        return response;
    }

    public ImageResponse assignImageTags(ImageTagAssignRequest request) {
        final Member currentMember = memberUtil.getCurrentMember();

        List<Tag> tags = request.getHashtags().stream()
                .map(tagName -> tagRepository.findByName(tagName)
                        .orElseThrow(() -> new CustomException(ErrorCode.TAG_NOT_FOUND)))
                .toList();

        tags.forEach(tag -> validateTagOwner(currentMember, tag));

        List<Image> imageUrls = request.getImageUrls().stream()
                .map(imageUrl -> imageRepository.findByImageUrl(imageUrl)
                        .orElseThrow(() -> new CustomException(ErrorCode.IMAGE_NOT_FOUND)))
                .toList();

        List<ImageTag> imageTags = imageUrls.stream()
                .flatMap(image -> tags.stream()
                        .map(tag -> createImageTag(tag, image)))
                .toList();

        imageTagRepository.saveAll(imageTags);

        List<Long> imageIds = imageUrls.stream()
                .map(Image::getId)
                .toList();

        return ImageResponse.builder()
                .imageIds(imageIds)
                .build();
    }

    private void validateTagOwner(Member member, Tag tag) {
        memberTagRepository.findByMemberAndTag(member, tag)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_TAG_OWNER));
    }
}