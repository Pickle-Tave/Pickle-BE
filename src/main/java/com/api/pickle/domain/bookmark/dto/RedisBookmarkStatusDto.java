package com.api.pickle.domain.bookmark.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class RedisBookmarkStatusDto {
    private List<Long> markedList;
    private List<Long> unmarkedList;
}
