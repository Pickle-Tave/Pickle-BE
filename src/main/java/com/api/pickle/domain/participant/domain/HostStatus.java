package com.api.pickle.domain.participant.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum HostStatus {
    HOST("HOST"),
    GUEST("GUEST");

    private final String value;
}
