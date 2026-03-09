package com.teample.matching.user.dto;


import com.teample.matching.matching.dto.ApplicationSummaryResponseDto;
import com.teample.matching.project.dto.ProjectSummaryResponseDto;
import com.teample.matching.user.domain.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class UserMypageResponseDto {

    private String email;

    private String nickname;

    //private UserTag userTag;

    //private Tier tier


    private List<ProjectSummaryResponseDto> myProjects;

    private List<ApplicationSummaryResponseDto> appliedProjects;

    @Builder
    public UserMypageResponseDto(User user,
                                 List<ProjectSummaryResponseDto> myProjects,
                                 List<ApplicationSummaryResponseDto> appliedProjects) {
        this.email = user.getEmail();
        this.nickname = user.getNickName();
        this.myProjects = myProjects;
        this.appliedProjects = appliedProjects;
    }

}