package com.api.pickle.domain.bookmark.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MarkStatus {
    MARKED("MARKED"),
    UNMARKED("UNMARKED");

    private final String value;
}
