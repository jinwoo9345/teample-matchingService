package com.teample.matching.matching.dto;


import com.teample.matching.project.domain.Project;
import com.teample.matching.user.domain.User;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ApplicationRequestDto {

    private Long userId;    // User 객체 대신 ID만!
    private Long projectId; // Project 객체 대신 ID만!

    @NotBlank(message = "자기소개 및 지원동기를 보내주세요")
    private String introduction;
}
