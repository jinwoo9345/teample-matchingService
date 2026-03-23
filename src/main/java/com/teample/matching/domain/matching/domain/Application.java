package com.teample.matching.domain.matching.domain;

import com.teample.matching.domain.project.domain.Project;
import com.teample.matching.domain.project.domain.ProjectRole;
import com.teample.matching.domain.user.domain.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

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

    @ElementCollection(targetClass = ProjectRole.class)
    @CollectionTable(
            name = "application_apply_roles", // 별도의 테이블 이름 지정
            joinColumns = @JoinColumn(name = "application_id") // 외래키 설정
    )
    @Enumerated(EnumType.STRING) // Enum을 문자열로 저장
    @Column(name = "role_name")
    private List<ProjectRole> applyRoles = new ArrayList<>();


    @Builder
    public Application(User user, Project project, String introduction,List<ProjectRole> applyRoles) {
        this.user = user;
        this.project = project;
        this.introduction = introduction;
        this.status = ApplicationStatus.PENDING;
        this.applyRoles = applyRoles;
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
