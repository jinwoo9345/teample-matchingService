package com.teample.matching.user.dto;


import com.teample.matching.project.dto.ProjectSummaryResponseDto;
import com.teample.matching.user.domain.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class UserProfileResponseDto {

    private String email;
    private String nickName;
    private String introduction;
    private int temperature;
    private List<ProjectSummaryResponseDto> projects;
    //private Tier tier;
    //private UserTag userTag;


    @Builder
    public UserProfileResponseDto(User user,List<ProjectSummaryResponseDto> projects) {
        this.email = user.getEmail();
        this.nickName = user.getNickName();
        this.introduction = user.getIntroduction();
        this.temperature = user.getTemperature();
        this.projects = projects;
    }

}
