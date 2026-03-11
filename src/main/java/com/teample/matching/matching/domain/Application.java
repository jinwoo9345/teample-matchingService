package com.teample.matching.matching.domain;

import com.teample.matching.project.domain.Project;
import com.teample.matching.project.domain.ProjectRole;
import com.teample.matching.user.domain.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Application {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ApplicationStatus status;

    @Column(columnDefinition = "TEXT")
    private String introduction;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id")
    private Project project;

    @Enumerated(EnumType.STRING)
    private ProjectRole applyRole;


    @Builder
    public Application(User user, Project project, String introduction,ProjectRole applyRole) {
        this.user = user;
        this.project = project;
        this.introduction = introduction;
        this.status = ApplicationStatus.PENDING;
        this.applyRole = applyRole;
    }

    // 승인 메소드
    public void accept() {
        this.status = ApplicationStatus.ACCEPTED;
    }

    // 거절 메소드
    public void reject() {
        this.status = ApplicationStatus.REJECTED;
    }
}
