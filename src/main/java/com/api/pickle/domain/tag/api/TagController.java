package com.api.pickle.domain.tag.api;


import com.api.pickle.domain.membertag.dto.MemberTagResponse;
import com.api.pickle.domain.tag.application.TagService;
import com.api.pickle.domain.tag.dto.request.TagCreateRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "태그 API", description = "해시태그 관련 API입니다.")
@RequestMapping("/tags")
@RestController
@RequiredArgsConstructor
public class TagController {

    private final TagService tagService;

    @Operation(summary = "해시태그 만들기", description = "사용자 해시태그를 만듭니다.")
    @PostMapping("/create")
    public ResponseEntity<Void> createHashTag(@RequestBody TagCreateRequest request) {
        tagService.createMemberHashTag(request.getName());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(summary = "해시태그 목록 조회", description = "사용자 해시태그 목록을 보여줍니다.")
    @GetMapping("/list")
    public List<MemberTagResponse> showHashTagList() {
        return tagService.showMemberHashTag();
    }

    @Operation(summary = "해시태그 삭제", description = "사용자 해시태그를 삭제합니다.")
    @DeleteMapping("/{memberTagId}")
    public ResponseEntity<Void> deleteHashTag(@PathVariable Long memberTagId) {
        tagService.deleteMemberHashTag(memberTagId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}

