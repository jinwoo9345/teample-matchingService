package com.teample.matching.domain.review.domain;

import com.teample.matching.domain.project.domain.Project;
import com.teample.matching.domain.user.domain.User;
import com.teample.matching.global.common.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Review extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int professionalism;
    private int communication;
    private int punctuality;
    private int cooperation;
    private int passion;

    @Column(length = 1000)
    private String comment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "target_id", nullable = false)
    private User targetUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "evaluator_id", nullable = false)
    private User evaluatorUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;

    @Builder
    public Review(
            int professionalism,
            int communication,
            int punctuality,
            int cooperation,
            int passion,
            String comment,
            User targetUser,
            User evaluatorUser,
            Project project
    ) {
        this.professionalism = professionalism;
        this.communication = communication;
        this.punctuality = punctuality;
        this.cooperation = cooperation;
        this.passion = passion;
        this.comment = comment;
        this.targetUser = targetUser;
        this.evaluatorUser = evaluatorUser;
        this.project = project;
    }
}