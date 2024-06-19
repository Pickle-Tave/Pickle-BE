package com.api.pickle.domain.album.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SharingStatus {
    PRIVATE("PRIVATE"),
    PUBLIC("PUBLIC");

    private final String value;
}
