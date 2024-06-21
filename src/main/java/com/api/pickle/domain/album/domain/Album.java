package com.api.pickle.domain.album.domain;

import com.api.pickle.domain.common.model.BaseTimeEntity;
import com.api.pickle.global.error.exception.CustomException;
import com.api.pickle.global.error.exception.ErrorCode;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Album extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "album_id")
    private Long id;

    @Column(name = "album_name")
    private String name;

    @Enumerated(EnumType.STRING)
    private SharingStatus status;

    @Builder
    private Album (String name, SharingStatus status){
        this.name = name;
        this.status = status;
    }

    public static Album createPrivateAlbum(String name){
        return Album.builder()
                .name(name)
                .status(SharingStatus.PRIVATE)
                .build();
    }

    public void switchStatus(SharingStatus newStatus){
        if (status == newStatus && newStatus == SharingStatus.PUBLIC){
            throw new CustomException(ErrorCode.ALREADY_SHARED_ALBUM);
        }
        this.status = newStatus;
    }
}
