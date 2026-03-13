package com.teample.matching.domain.project.dto;

import com.teample.matching.domain.project.domain.Project;
import com.teample.matching.domain.project.domain.ProjectStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ProjectSummaryResponseDto {
    private Long id;
    private String title;
    private String contentSummary;
    private ProjectStatus status;

    public ProjectSummaryResponseDto(Project project) {
        this.id = project.getId();
        this.title = project.getTitle();
        // 요약본으로 만들기 필요
        this.contentSummary = project.getContent();
        this.status = project.getStatus();
    }
}
