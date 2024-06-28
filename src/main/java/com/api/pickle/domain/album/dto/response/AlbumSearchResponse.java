package com.api.pickle.domain.album.dto.response;

import com.querydsl.core.annotations.QueryProjection;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@NoArgsConstructor
@Getter
@Setter
public class AlbumSearchResponse {

    @Schema(description = "검색된 앨범 id")
    private Long albumId;

    @Schema(description = "검색된 앨범의 이름")
    private String searchedAlbumName;

    @Schema(description = "검색된 앨범의 상태")
    private String searchedAlbumStatus;

    @Schema(description = "검색된 앨범의 북마크 상태")
    private String searchedAlbumMarkedStatus;

    @QueryProjection
    public AlbumSearchResponse(Long albumId, String searchedAlbumName, String searchedAlbumStatus, String searchedAlbumMarkedStatus) {
        this.albumId = albumId;
        this.searchedAlbumName = searchedAlbumName;
        this.searchedAlbumStatus = searchedAlbumStatus;
        this.searchedAlbumMarkedStatus = searchedAlbumMarkedStatus;
    }
}
