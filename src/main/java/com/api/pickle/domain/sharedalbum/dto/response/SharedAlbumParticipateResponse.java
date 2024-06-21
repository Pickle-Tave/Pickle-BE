package com.api.pickle.domain.sharedalbum.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SharedAlbumParticipateResponse {
    @Schema(description = "참여한 공유앨범의 id")
    private Long albumId;
    @Schema(description = "참여한 공유앨범의 이름")
    private String albumName;
}
