package com.teample.matching.domain.review.domain;


import com.teample.matching.domain.project.domain.Project;
import com.teample.matching.domain.user.domain.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int professionalism;

    private int communication;

    private int punctuality;

    private String comment;

    @Column(name = "create_at")
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "target_id")
    private User targetUser;

    @ManyToOne
    @JoinColumn(name = "evaluator_id")
    private User evaluatorUser;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

}
