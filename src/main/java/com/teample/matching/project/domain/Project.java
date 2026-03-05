package com.teample.matching.project.domain;


import com.teample.matching.common.BaseTimeEntity;
import com.teample.matching.user.domain.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

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

    @Enumerated(EnumType.STRING)
    private ProjectStatus status = ProjectStatus.RECRUITING;

    private String period;

    private LocalDateTime deadline;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User leader;

    @Builder // 빌더 패턴 추가
    public Project(String title, String content, ProjectStatus status, LocalDateTime deadline,String period, User leader) {
        this.title = title;
        this.content = content;
        this.deadline = deadline;
        this.period = period;
        this.leader = leader;
    }

    public void update(String title, String content, String period,LocalDateTime deadline) {
        this.title = title;
        this.content = content;
        this.period = period;
        this.deadline = deadline;
    }
}