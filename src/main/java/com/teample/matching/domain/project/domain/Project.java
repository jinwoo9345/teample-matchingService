package com.teample.matching.domain.project.domain;


import com.teample.matching.domain.user.domain.User;
import com.teample.matching.global.BaseTimeEntity;
import com.teample.matching.global.domain.Tag;
import com.teample.matching.global.exception.ForbiddenException;
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
    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProjectTag> projectTags = new ArrayList<>();



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

    public void joinMember() {
        // 1. 검증: 정원이 가득 찼는지 확인
        validateCapacity();
        // 2. 상태 변경: 현재 참여 인원 증가
        this.currentMemberCount++;

    }

    private void validateCapacity() {
        if (this.currentMemberCount >= this.capacity) {
            throw new ForbiddenException("정원이 가득 찼습니다.");
        }
    }
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