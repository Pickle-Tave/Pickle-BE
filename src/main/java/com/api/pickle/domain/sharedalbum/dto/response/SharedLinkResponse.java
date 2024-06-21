package com.api.pickle.domain.sharedalbum.dto.response;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SharedLinkResponse {
    @Schema(description = "전환된 공유앨범의 공유 링크")
    private final String link;
}
