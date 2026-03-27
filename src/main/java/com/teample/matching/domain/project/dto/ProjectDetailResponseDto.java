package com.teample.matching.domain.project.dto;


import com.teample.matching.domain.project.domain.Project;
import com.teample.matching.domain.project.domain.ProjectStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
public class ProjectDetailResponseDto {

    private Long id;
    private String title;
    private List<String> projectTags;
    private LocalDateTime deadline;
    private Long leaderId;
    private String period;
    private ProjectStatus status;
    private int capacity;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private String content;

    public ProjectDetailResponseDto(Project project) {

        this.id = project.getId();
        this.title = project.getTitle();
        this.projectTags = project.getProjectTags().stream()
                .map(projectTag -> projectTag.getTag().getTagName())
                .toList();
        this.deadline = project.getDeadline();
        this.leaderId = project.getLeader().getId();
        this.period = project.getPeriod();
        this.capacity = project.getCapacity();
        this.status = project.getStatus();
        this.createdAt = project.getCreatedAt();
        this.modifiedAt = project.getModifiedAt();
        this.content = project.getContent();

    }
}
