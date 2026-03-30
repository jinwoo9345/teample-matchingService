package com.teample.matching.domain.project.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ProjectType {

    PROJECT("프로젝트"),
    STUDY("스터디");

    private final String description;

}
