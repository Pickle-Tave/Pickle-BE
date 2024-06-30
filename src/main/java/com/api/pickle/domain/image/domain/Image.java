package com.api.pickle.domain.image.domain;

import com.api.pickle.domain.album.domain.Album;
import com.api.pickle.domain.common.model.BaseTimeEntity;
import com.api.pickle.domain.member.domain.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Image extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "image_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "album_id")
    private Album album;

    private String imageUrl;

    @Builder
    private Image(Member member, Album album, String imageUrl) {
        this.member = member;
        this.album = album;
        this.imageUrl = imageUrl;
    }

    public static Image createImage(Member member, Album album, String imageUrl) {
        return Image.builder()
                .member(member)
                .album(album)
                .imageUrl(imageUrl)
                .build();
    }
}
