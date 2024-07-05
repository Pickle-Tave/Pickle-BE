package com.api.pickle.domain.member.api;

import com.api.pickle.domain.member.application.MemberService;
import com.api.pickle.domain.member.dto.request.UpdateFcmTokenRequest;
import com.api.pickle.domain.member.dto.response.MyPageResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "유저 API", description = "사용자 관련 API입니다.")
@RequestMapping("/members")
@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @Operation(summary = "로그아웃", description = "로그아웃을 진행합니다.")
    @PostMapping("/logout")
    public ResponseEntity<Void> memberLogout(){
        memberService.memberLogout();
        return ResponseEntity.ok().build();
    }


    @Operation(summary = "회원탈퇴", description = "회원탈퇴를 진행합니다.")
    @DeleteMapping("/withdrawal")
    public ResponseEntity<Void> memberWithdrawal(){
        memberService.memberWithdrawal();
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "마이페이지", description = "회원정보를 가져옵니다.")
    @GetMapping("/mypage")
    public ResponseEntity<MyPageResponse> memberMyPage() {
        MyPageResponse memberMyPageResponse = memberService.getMemberMyPageInfo();
        return new ResponseEntity<MyPageResponse>(memberMyPageResponse, HttpStatus.OK);
    }

    @Operation(summary = "토글 여부 변경", description = "기존 토글 값을 변경합니다.")
    @PatchMapping("/alarm")
    public ResponseEntity<Void> memberToggleAppAlarmStateUpdate() {
        memberService.toggleAppAlarm();
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "FCM 토큰 갱신", description = "FCM 토큰을 갱신합니다.")
    @PatchMapping("/fcm-token")
    public ResponseEntity<Void> memberFcmTokenUpdate(@RequestBody UpdateFcmTokenRequest updateFcmTokenRequest) {
        memberService.updateFcmToken(updateFcmTokenRequest);
        return ResponseEntity.ok().build();
    }

}
