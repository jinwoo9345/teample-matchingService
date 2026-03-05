package com.teample.matching.project.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class ProjectUpdateRequestDto {

    private String title;
    private String content;
    private String period;
    private LocalDateTime deadline;

}
