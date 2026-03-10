package com.teample.matching.project.domain;


import com.teample.matching.common.BaseTimeEntity;
import com.teample.matching.user.domain.User;
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



    @Builder
    public ProjectMember( User user, Project project) {
        this.contribution = 0;
        this.user = user;
        this.project = project;
    }

    public static ProjectMember createMember(Project project, User user) {
        return ProjectMember.builder()
                .project(project)
                .user(user)
                .build();
    }
}
