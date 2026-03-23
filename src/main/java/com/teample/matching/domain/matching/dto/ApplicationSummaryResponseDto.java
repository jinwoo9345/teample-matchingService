package com.teample.matching.domain.matching.dto;

import com.teample.matching.domain.matching.domain.Application;
import com.teample.matching.domain.matching.domain.ApplicationStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ApplicationSummaryResponseDto {

    private Long applicationId;
    private Long userId;
    private String nickName;
    private ApplicationStatus status;

    public ApplicationSummaryResponseDto(Application application) {
        this.applicationId = application.getId();
        this.userId = application.getUser().getId();
        this.nickName = application.getUser().getNickName();
        this.status = application.getStatus();
    }
}
