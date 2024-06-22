package com.api.pickle.domain.tag.domain;

import com.api.pickle.domain.common.model.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Tag extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tag_id")
    private Long id;

    private String name;

    @Builder
    private Tag(String name) {
        this.name = name;
    }

    public static Tag createTag(String name) {
        return Tag.builder()
                .name(name)
                .build();
    }
}
