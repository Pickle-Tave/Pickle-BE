package com.api.pickle.domain.sharedalbum.domain;

import com.api.pickle.domain.album.domain.Album;
import com.api.pickle.domain.common.model.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SharedAlbum extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "shared_album_id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "album_id")
    private Album album;

    @Column(name = "album_link")
    private String link;

    @Column(name = "album_password")
    private String password;

    @Builder
    public SharedAlbum(Album album, String link, String password){
        this.album = album;
        this.link = link;
        this.password = password;
    }

    public static SharedAlbum createSharedAlbum(Album album, String link, String password){
        return SharedAlbum.builder()
                .album(album)
                .link(link)
                .password(password)
                .build();
    }
}
