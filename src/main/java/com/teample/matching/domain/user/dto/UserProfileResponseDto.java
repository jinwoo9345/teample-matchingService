package com.teample.matching.domain.user.dto;


import com.teample.matching.domain.project.dto.ProjectSummaryResponseDto;
import com.teample.matching.domain.user.domain.User;
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
    private boolean isMe;
    private List<ProjectSummaryResponseDto> projects;
    //private Tier tier;
    //private UserTag userTag;


    @Builder
    public UserProfileResponseDto(User user,List<ProjectSummaryResponseDto> projects,boolean isMe) {
        this.email = user.getEmail();
        this.nickName = user.getNickName();
        this.introduction = user.getIntroduction();
        this.temperature = user.getTemperature();
        this.projects = projects;
        this.isMe = isMe;
    }

}
