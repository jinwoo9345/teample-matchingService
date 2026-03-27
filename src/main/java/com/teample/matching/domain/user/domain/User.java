package com.teample.matching.domain.user.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Builder
@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    private String nickName;

    private String major;

    private String profile;

    @Column(columnDefinition = "TEXT")
    private String introduction;

    private int temperature;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tier_id")
    private Tier tier;


    @Builder.Default
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserTag> userTags = new ArrayList<>();


    public void addUserTag(UserTag userTag) {
        this.userTags.add(userTag);
    }

    public void updateProfile(String nickName, String major, String profile, String introduction) {
        this.nickName = nickName;
        this.major = major;
        this.profile = profile;
        this.introduction = introduction;
    }
}