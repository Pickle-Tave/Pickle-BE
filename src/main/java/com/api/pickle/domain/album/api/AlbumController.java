package com.api.pickle.domain.album.api;


import com.api.pickle.domain.album.application.AlbumService;
import com.api.pickle.domain.album.dto.request.AlbumCreateRequest;
import com.api.pickle.domain.album.dto.request.UpdateAlbumRequest;
import com.api.pickle.domain.album.dto.response.AlbumSearchResponse;
import com.api.pickle.domain.album.dto.response.UpdateAlbumResponse;
import com.api.pickle.domain.bookmark.application.BookmarkService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Slice;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "앨범 API", description = "앨범 관련 API입니다.")
@RestController
@RequiredArgsConstructor
@RequestMapping("/albums")
public class AlbumController {

    private final AlbumService albumService;
    private final BookmarkService bookmarkService;

    @Operation(summary = "앨범 생성", description = "앨범 생성을 진행합니다.")
    @PostMapping("/create")
    public ResponseEntity<Void> createAlbum(@RequestBody AlbumCreateRequest request) {
        albumService.createAlbum(request.getName());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(summary = "앨범 명 수정", description = "앨범 명을 수정합니다.")
    @PutMapping("/{albumId}")
    public UpdateAlbumResponse updateAlbumName(@PathVariable Long albumId,
                                               @RequestBody UpdateAlbumRequest request) {
        return albumService.updateAlbumName(albumId, request.getNewAlbumName());
    }

    @Operation(summary = "앨범명 검색", description = "앨범명으로 검색하고 최신순으로 조회합니다.")
    @GetMapping("/search/keyword")
    public Slice<AlbumSearchResponse> searchAlbumKeyword(@Parameter(description = "검색할 앨범의 이름 키워드", example = "앨범")
                                                         @RequestParam String keyword,
                                                         @Parameter(description = "이전 페이지의 마지막 앨범 ID (첫 페이지는 비워두세요.)")
                                                         @RequestParam(required = false) Long lastAlbumId,
                                                         @Parameter(description = "페이지당 앨범 수", example = "1")
                                                         @RequestParam(value = "size") int pageSize) {

        return albumService.searchKeywordInAlbumOrderByCreatedDateDesc(keyword, pageSize, lastAlbumId);
    }

    @Operation(summary = "앨범상태 검색", description = "앨범상태로 검색하고 최신순으로 조회합니다.")
    @GetMapping("/search/status")
    public Slice<AlbumSearchResponse> searchAlbumStatus(@Parameter(description = "검색할 앨범의 공유상태", example = "PRIVATE OR PUBLIC")
                                                        @RequestParam String albumStatus,
                                                        @Parameter(description = "이전 페이지의 마지막 앨범 ID (첫 페이지는 비워두세요.)")
                                                        @RequestParam(required = false) Long lastAlbumId,
                                                        @Parameter(description = "페이지당 앨범 수", example = "1")
                                                        @RequestParam(value = "size") int pageSize) {

        return albumService.searchAlbumStatusInAlbumOrderByCreatedDateDesc(albumStatus, pageSize, lastAlbumId);
    }

    @Operation(summary = "사용자 앨범 조회", description = "사용자가 참여 중인 모든 앨범을 조회합니다.")
    @GetMapping("/list")
    public Slice<AlbumSearchResponse> albumFindAll(@Parameter(description = "이전 페이지의 마지막 앨범 ID (첫 페이지는 비워두세요.)")
                                                   @RequestParam(required = false) Long lastAlbumId,
                                                   @Parameter(description = "페이지당 앨범 수", example = "1")
                                                   @RequestParam(value = "size") int pageSize) {

        return albumService.findAllAlbumOfMember(pageSize, lastAlbumId);
    }

    @Operation(summary = "앨범 즐겨찾기 검색", description = "사용자가 즐겨찾기를 적용한 모든 앨범을 조회합니다.")
    @GetMapping("/search/heart")
    public Slice<AlbumSearchResponse> searchAlbumBookmarked(@Parameter(description = "이전 페이지의 마지막 앨범 ID (첫 페이지는 비워두세요.)")
                                                               @RequestParam(required = false) Long lastAlbumId,
                                                           @Parameter(description = "페이지당 앨범 수", example = "1")
                                                               @RequestParam(value = "size") int pageSize){
        return bookmarkService.searchAlbumInfoWithBookmarked(pageSize, lastAlbumId);
    }
}
