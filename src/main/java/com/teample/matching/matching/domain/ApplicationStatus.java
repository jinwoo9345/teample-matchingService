package com.teample.matching.matching.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ApplicationStatus {
    PENDING("대기 중"),
    ACCEPTED("승인됨"),
    REJECTED("거절됨");

    private final String description;
}
