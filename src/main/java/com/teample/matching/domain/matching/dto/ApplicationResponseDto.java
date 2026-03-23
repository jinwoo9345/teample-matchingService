package com.teample.matching.domain.matching.dto;

import com.teample.matching.domain.matching.domain.Application;
import com.teample.matching.domain.matching.domain.ApplicationStatus;
import com.teample.matching.domain.project.domain.ProjectRole;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class ApplicationResponseDto {

    private Long applicationId;

    private Long userId;
    private String nickName;

    private String introduction;

    private List<ProjectRole> projectRoles;

    private ApplicationStatus status;


    public ApplicationResponseDto(Application application) {
        this.applicationId = application.getId();
        this.userId = application.getUser().getId();
        this.nickName = application.getUser().getNickName();
        this.introduction = application.getIntroduction();
        this.status = application.getStatus();
    }

}
