package com.api.pickle.domain.imagetag.domain;

import com.api.pickle.domain.common.model.BaseTimeEntity;
import com.api.pickle.domain.tag.domain.Tag;
import com.api.pickle.domain.image.domain.Image;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ImageTag extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "image_tag_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "tag_id")
    private Tag tag;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "image_id")
    private Image image;

    @Builder
    private ImageTag(Tag tag, Image image) {
        this.tag = tag;
        this.image = image;
    }

    public static ImageTag createImageTag(Tag tag, Image image) {
        return ImageTag.builder()
                .tag(tag)
                .image(image)
                .build();
    }
}
