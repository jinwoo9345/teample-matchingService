package com.teample.matching.domain.project.domain;


import com.teample.matching.domain.review.domain.Review;
import com.teample.matching.domain.user.domain.User;
import com.teample.matching.global.common.entity.BaseTimeEntity;
import com.teample.matching.domain.tag.domain.Tag;
import com.teample.matching.global.error.exception.BadRequestException;
import com.teample.matching.global.error.exception.ForbiddenException;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED) // 무분별한 객체 생성을 막기 위해 접근 제어
public class Project extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;

    @Column(nullable = false)
    private String memberRole;

    @Column(nullable = false)
    private int capacity;

    private int currentMemberCount;

    @Enumerated(EnumType.STRING)
    private ProjectStatus status = ProjectStatus.RECRUITING;

    private String period;

    private LocalDateTime deadline;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User leader;


    // ProjectTag 리스트 추가
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProjectTag> projectTags = new ArrayList<>();

    // 프로젝트 멤버 리스트
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProjectMember> projectMembers = new ArrayList<>();

    //프로젝트 리뷰 리스트
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "project",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Review> reviews = new ArrayList<>();

    @Builder // 빌더 패턴 추가
    public Project(String title, String content, String memberRole ,int capacity, LocalDateTime deadline, String period, User leader) {
        this.title = title;
        this.content = content;
        this.memberRole = memberRole;
        this.capacity = capacity;
        this.currentMemberCount = 0;
        this.deadline = deadline;
        this.period = period;
        this.leader = leader;
    }

    public void update(String title, String content,int capacity, String period,LocalDateTime deadline) {
        this.title = title;
        this.content = content;
        this.period = period;
        this.deadline = deadline;
        this.capacity = capacity;
    }

    public void joinMember(ProjectMember member) {
        // 1. 검증: 참가 가능한지 확인
        validateForApply();

        // 2. 프로젝트 멤버에 추가
        this.projectMembers.add(member);

        // 3. 상태 변경: 현재 참여 인원 증가
        this.currentMemberCount++;

        // 4. 참여후 인원 재확인
        if (isFull()) {
            changeStatusToComplete();
        }
    }



    public void addReview(Review review) {
        validateProjectFinish();
        this.reviews.add(review);
    }



    public void validateProjectFinish() {
        if(this.status != ProjectStatus.FINISHED) {
            throw new BadRequestException("프로젝트가 아직 진행중입니다!");
        }
    }


    public void validateForApply() {
        if (this.status != ProjectStatus.RECRUITING) {
            throw new ForbiddenException("현재 모집 중인 프로젝트가 아닙니다.");
        }
        if (isFull()) {
            throw new ForbiddenException("이미 정원이 가득 찼습니다.");
        }
    }

    public void validateMember(Long userId) {
        boolean isMember = this.projectMembers.stream()
                .anyMatch(member -> member.getUser().getId().equals(userId));

        if (!isMember) {
            throw new ForbiddenException("해당 프로젝트의 참여자가 아닙니다.");
        }
    }


    // 정원확인
    private boolean isFull() {
        return this.currentMemberCount >= this.capacity;
    }

    // 모집 상태 완료 변경
    public void changeStatusToComplete() {
        this.status = ProjectStatus.COMPLETE;
    }

    // 프로젝트 완전 종료
    public void changeStatusToFinish() {this.status = ProjectStatus.FINISHED; }

    // 리더 권한 확인
    public void validateLeader(Long userId) {
        if (!this.leader.getId().equals(userId)) {
            throw new ForbiddenException("리더 권한이 없습니다.");
        }
    }


    public void addTag(Tag tag) {
        ProjectTag projectTag = ProjectTag.builder()
                .project(this)
                .tag(tag)
                .build();

        this.projectTags.add(projectTag);
    }
}