package com.api.pickle.domain.album.api;


import com.api.pickle.domain.album.application.AlbumService;
import com.api.pickle.domain.album.dao.AlbumRepository;
import com.api.pickle.domain.album.dto.request.AlbumCreateRequest;
import com.api.pickle.domain.album.dto.request.UpdateAlbumRequest;
import com.api.pickle.domain.album.dto.response.AlbumSearchResponse;
import com.api.pickle.domain.album.dto.response.UpdateAlbumResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "앨범 API", description = "앨범 관련 API입니다.")
@RestController
@RequiredArgsConstructor
@RequestMapping("/albums")
public class AlbumController {

    private final AlbumService albumService;

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
    public List<AlbumSearchResponse> searchAlbumKeyword(@Parameter(description = "검색할 앨범의 이름 키워드", example = "앨범")
                                                     @RequestParam String keyword) {
        return albumService.searchKeywordInAlbumOrderByCreatedDateDesc(keyword);
    }

    @Operation(summary = "앨범상태 검색", description = "앨범상태로 검색하고 최신순으로 조회합니다.")
    @GetMapping("/search/status")
    public List<AlbumSearchResponse> searchAlbumStatus(@Parameter(description = "검색할 앨범의 공유상태", example = "PRIVATE OR PUBLIC")
                                                 @RequestParam String albumStatus) {
        return albumService.searchAlbumStatusInAlbumOrderByCreatedDateDesc(albumStatus);
    }
}
