package com.teample.matching.domain.project.domain;


import com.teample.matching.global.BaseTimeEntity;
import com.teample.matching.domain.user.domain.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class ProjectMember extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String role;

    private int contribution;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

    @Enumerated(EnumType.STRING)
    private ProjectRole projectRole;


    @Builder
    public ProjectMember( User user, Project project, ProjectRole projectRole) {
        this.contribution = 0;
        this.user = user;
        this.project = project;
        this.projectRole = projectRole;

    }

    public static ProjectMember createMember(Project project, User user, ProjectRole projectRole) {
        ProjectMember member = ProjectMember.builder()
                .project(project)
                .user(user)
                .projectRole(projectRole)
                .build();
        member.role = "MEMBER";
        return member;
    }
}
