package com.teample.matching.domain.project.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ProjectStatus {
    RECRUITING("모집 중"),
    FINISHED("모집 완료");

    private final String description;
}