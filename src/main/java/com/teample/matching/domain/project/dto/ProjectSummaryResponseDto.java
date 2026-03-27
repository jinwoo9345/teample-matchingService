package com.teample.matching.domain.project.dto;

import com.teample.matching.domain.project.domain.Project;
import com.teample.matching.domain.project.domain.ProjectStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class ProjectSummaryResponseDto {
    private Long id;
    private String title;
    private List<String> projectTags;
    private ProjectStatus status;

    public ProjectSummaryResponseDto(Project project) {
        this.id = project.getId();
        this.title = project.getTitle();
        this.projectTags = project.getProjectTags().stream()
                .map(projectTag -> projectTag.getTag().getTagName())
                .toList();
        this.status = project.getStatus();
    }
}
