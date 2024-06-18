package com.api.pickle.domain.album.api;


import com.api.pickle.domain.album.application.AlbumService;
import com.api.pickle.domain.album.dto.request.AlbumCreateRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "앨범 API", description = "앨범 관련 API입니다.")
@RestController
@RequiredArgsConstructor
@RequestMapping("/albums")
public class AlbumController {
    private final AlbumService albumService;

    @Operation(summary = "앨범 생성", description = "앨범 생성을 진행합니다.")
    @PostMapping("/create")
    public ResponseEntity<Void> createAlbum(@RequestBody AlbumCreateRequest request){
        albumService.createAlbum(request.getName());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
