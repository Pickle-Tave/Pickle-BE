package com.api.pickle.domain.sharedalbum.api;

import com.api.pickle.domain.sharedalbum.dto.request.SwitchToSharedAlbumRequest;
import com.api.pickle.domain.sharedalbum.dto.response.SharedLinkResponse;
import com.api.pickle.domain.sharedalbum.application.SharedAlbumService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name = "공유 앨범 API", description = " 공유앨범 관련 API입니다.")
@RestController
@RequiredArgsConstructor
@RequestMapping("/albums/share")
public class SharedAlbumController {
    private final SharedAlbumService sharedAlbumService;

    @Operation(summary = "공유 앨범 전환", description = "공유 앨범으로 전환하고 공유 링크를 반환합니다.")
    @PostMapping("/{albumId}")
    public SharedLinkResponse switchToPublicAlbum(@PathVariable Long albumId,
                                                  @RequestBody SwitchToSharedAlbumRequest request){
        return sharedAlbumService.getSharedAlbumLink(albumId, request.getAlbumPassword());
    }
}
