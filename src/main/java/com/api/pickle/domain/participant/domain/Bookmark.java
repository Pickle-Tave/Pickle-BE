package com.api.pickle.domain.participant.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Bookmark {
    MARKED("MARKED"),
    UNMARKED("UNMARKED");

    private final String value;
}
