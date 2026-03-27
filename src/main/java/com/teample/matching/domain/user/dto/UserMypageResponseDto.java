package com.teample.matching.domain.user.dto;


import com.teample.matching.domain.matching.dto.ApplicationSummaryResponseDto;
import com.teample.matching.domain.project.dto.ProjectSummaryResponseDto;
import com.teample.matching.domain.user.domain.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class UserMypageResponseDto {

    private String email;

    private String nickname;

    private List<String> userTags;

    //private Tier tier;
    private String introduction;

    private List<ProjectSummaryResponseDto> myProjects;

    private List<ApplicationSummaryResponseDto> appliedProjects;

    @Builder
    public UserMypageResponseDto(User user,
                                 List<ProjectSummaryResponseDto> myProjects,
                                 List<ApplicationSummaryResponseDto> appliedProjects) {
        this.email = user.getEmail();
        this.nickname = user.getNickName();
        this.userTags = user.getUserTags().stream()
                .map(userTag -> userTag.getTag().getTagName())
                .toList();
        this.introduction = user.getIntroduction();
        this.myProjects = myProjects;
        this.appliedProjects = appliedProjects;
    }

}