package com.teample.matching.domain.matching.dto;


import com.teample.matching.domain.project.domain.ProjectRole;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ApplicationRequestDto {

    
    private Long projectId; // Project 객체 대신 ID만!
    private ProjectRole projectRole;

    @NotBlank(message = "자기소개 및 지원동기를 보내주세요")
    private String introduction;
}
