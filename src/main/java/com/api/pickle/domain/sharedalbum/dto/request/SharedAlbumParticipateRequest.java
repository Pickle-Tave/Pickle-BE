package com.api.pickle.domain.sharedalbum.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SharedAlbumParticipateRequest {
    @Schema(description = "공유 앨범의 링크")
    private String albumLink;
    @Schema(description = "공유 앨범의 비밀번호")
    private String albumPassword;
}
