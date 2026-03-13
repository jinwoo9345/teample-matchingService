package com.teample.matching.domain.project.dto;


import com.teample.matching.domain.project.domain.Project;
import com.teample.matching.domain.project.domain.ProjectStatus;
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
    private int capacity;
    private ProjectStatus status;

    public ProjectResponseDto(Project project) {

        this.id = project.getId();
        this.title = project.getTitle();
        this.deadline = project.getDeadline();
        this.leaderId = project.getLeader().getId();
        this.period = project.getPeriod();
        this.capacity = project.getCapacity();
        this.status = project.getStatus();

    }


}
