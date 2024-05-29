package com.api.pickle.domain.image.application;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.Headers;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.api.pickle.domain.album.dao.AlbumRepository;
import com.api.pickle.domain.album.domain.Album;
import com.api.pickle.domain.image.dao.ImageRepository;
import com.api.pickle.domain.image.dto.request.AlbumImageCreateRequest;
import com.api.pickle.domain.image.dto.response.PresignedUrlResponse;
import com.api.pickle.domain.member.domain.Member;
import com.api.pickle.global.error.exception.CustomException;
import com.api.pickle.global.error.exception.ErrorCode;
import com.api.pickle.global.util.MemberUtil;
import com.api.pickle.infra.config.s3.S3Properties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static com.api.pickle.domain.image.domain.Image.createImage;

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

    public PresignedUrlResponse createAlbumPresignedUrl(AlbumImageCreateRequest request) {
        Member member = memberUtil.getCurrentMember();

        Album album = albumRepository.findById(request.getAlbumId())
                .orElseThrow(() -> new CustomException(ErrorCode.ALBUM_NOT_FOUND));

        String imageKey = generateUUID();
        String fileName = createFileName(member.getId(), album.getId(), imageKey);

        GeneratePresignedUrlRequest generatePresignedUrlRequest =
                createGeneratePresignedUrlRequest(s3Properties.getBucket(), fileName);

        String presignedUrl = amazonS3.generatePresignedUrl(generatePresignedUrlRequest).toString();

        imageRepository.save(createImage(member, album, imageKey));

        return PresignedUrlResponse.builder()
                .presignedUrl(presignedUrl)
                .build();
    }

    private String generateUUID() {
        return UUID.randomUUID().toString();
    }

    private String createFileName(Long memberId, Long albumId, String imageKey) {
        return memberId
                + "/"
                + imageKey
                + "/"
                + albumId;
    }

    private GeneratePresignedUrlRequest createGeneratePresignedUrlRequest(String bucket, String fileName) {

        GeneratePresignedUrlRequest generatePresignedUrlRequest = new GeneratePresignedUrlRequest(bucket, fileName)
                .withKey(fileName)
                .withMethod(HttpMethod.PUT)
//                .withContentType("image/" + fileExtension)
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
}
