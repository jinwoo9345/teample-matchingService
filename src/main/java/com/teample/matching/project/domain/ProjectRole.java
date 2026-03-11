package com.teample.matching.project.domain;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ProjectRole {
    LEADER("리더"),
    PLANNER("기획자"),
    DEVELOPER("개발자"),
    DESIGNER("디자이너");

    private final String description;

}
