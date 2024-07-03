package com.api.pickle.domain.bookmark.api;

import com.api.pickle.domain.bookmark.application.BookmarkService;
import com.api.pickle.domain.bookmark.domain.MarkStatus;
import com.api.pickle.domain.bookmark.dto.response.MarkedResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name = "즐겨찾기 API", description = "즐겨찾기 관련 API입니다.")
@RestController
@RequiredArgsConstructor
@RequestMapping("/hearts")
public class BookmarkController {
    private final BookmarkService bookmarkService;

    @Operation(summary = "즐겨찾기 추가", description = "특정 앨범에 즐겨찾기를 적용합니다.")
    @PostMapping("/apply/{albumId}")
    public MarkedResponse bookmarkAlbum(@PathVariable Long albumId){
        return bookmarkService.changeMarkByAlbumId(albumId, MarkStatus.MARKED);
    }

    @Operation(summary = "즐겨찾기 해제", description = "특정 앨범의 즐겨찾기를 해제합니다.")
    @DeleteMapping("/unapply/{albumId}")
    public MarkedResponse unmarkAlbum(@PathVariable Long albumId){
        return bookmarkService.changeMarkByAlbumId(albumId, MarkStatus.UNMARKED);
    }
}
