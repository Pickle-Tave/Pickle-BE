package com.api.pickle.domain.member.dto.response;


import com.api.pickle.domain.member.domain.Member;
import com.api.pickle.domain.member.domain.MemberRole;
import com.api.pickle.domain.member.domain.MemberStatus;
import com.api.pickle.domain.member.domain.OauthInfo;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MyPageResponse {

    private String nickname;
    private OauthInfo oauthInfo;
    private MemberRole role;
    private MemberStatus status;


}
