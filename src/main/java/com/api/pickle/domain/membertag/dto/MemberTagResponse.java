package com.api.pickle.domain.membertag.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class MemberTagResponse {
    private Long id;
    private String text;

}
