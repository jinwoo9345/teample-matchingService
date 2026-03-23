package com.teample.matching.domain.project.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ProjectStatus {
    RECRUITING("모집 중"),
    COMPLETE("모집 완료"),
    FINISHED("프로젝트 종료");

    private final String description;
}