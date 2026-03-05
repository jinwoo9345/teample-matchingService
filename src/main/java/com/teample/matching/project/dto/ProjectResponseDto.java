package com.teample.matching.project.dto;


import com.teample.matching.project.domain.Project;
import com.teample.matching.project.domain.ProjectStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class ProjectResponseDto {

    private Long id;
    private String title;
    private LocalDateTime deadline;
    private Long leaderId;
    private String period;
    private ProjectStatus status;

    public ProjectResponseDto(Project project) {

        this.id = project.getId();
        this.title = project.getTitle();
        this.deadline = project.getDeadline();
        this.leaderId = project.getLeader().getId();
        this.period = project.getPeriod();
        this.status = project.getStatus();

    }


}
