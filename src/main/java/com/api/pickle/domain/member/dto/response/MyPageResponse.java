package com.api.pickle.domain.member.dto.response;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
public class MyPageResponse {

    private Map<String,Object> memberInfo;

}
