package com.teample.matching.domain.project.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
public class ProjectCreateRequestDto {

    @NotBlank(message = "프로젝트 제목은 필수입니다.")
    private String title;
    
    private List<String> tagNames;

    @NotBlank(message = "프로젝트 내용은 필수입니다.")
    private String content;

    @NotBlank(message = "프로젝트에 필요한 멤버를 적어주세요.")
    private String memberRole;

    @NotNull(message = "모집 마감 기한을 설정해주세요.")
    private LocalDateTime deadline;


    @Min(1)
    private int capacity;

    private String period;

    // 임시: 아직 토큰 기능이 없으므로, 누가 쓰는지 ID를 직접 받습니다.
    @NotNull(message = "작성자 ID는 필수입니다.")
    private Long leaderId;
}