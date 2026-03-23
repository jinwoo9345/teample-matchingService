package com.teample.matching.domain.review.dto;

import com.teample.matching.domain.project.domain.ProjectRole;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ReviewableMemberResponseDto {
    private Long userId;
    private String nickname;
    private String profileImage; // 프로필 이미지가 있다면 추가
    private ProjectRole role;
    private boolean isReviewed;


    @Builder
    public ReviewableMemberResponseDto(Long userId,String nickname,String profileImage,ProjectRole role,boolean isReviewed) {
        this.userId = userId;
        this.nickname = nickname;
        this.profileImage = profileImage;
        this.role = role;
        this.isReviewed = isReviewed;
    }
}
