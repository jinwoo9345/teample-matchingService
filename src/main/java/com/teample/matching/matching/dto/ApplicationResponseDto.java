package com.teample.matching.matching.dto;

import com.teample.matching.matching.domain.Application;
import com.teample.matching.matching.domain.ApplicationStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ApplicationResponseDto {

    private Long applicationId;

    private Long userId;
    private String nickName;

    private String introduction;

    private ApplicationStatus status;


    public ApplicationResponseDto(Application application) {
        this.applicationId = application.getId();
        this.userId = application.getUser().getId();
        this.nickName = application.getUser().getNickName();
        this.introduction = application.getIntroduction();
        this.status = application.getStatus();
    }

}
