package com.kookmin.pm.module.league.domain;

import com.kookmin.pm.module.category.domain.Category;
import com.kookmin.pm.module.member.domain.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.tomcat.jni.Local;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "LEAGUE")
public class League {
    @Id @GeneratedValue
    @Column(name = "LEAGUE_ID")
    private Long id;

    @Column(name = "TITLE")
    private String title;

    @Column(name="DESCRIPTION")
    private String description;

    @Column(name="ACTIVITY_AREA")
    private String activityArea;

    @Enumerated(EnumType.STRING)
    @Column(name="TYPE")
    private LeagueType type;

    @Column(name = "MAX_COUNT")
    private Integer maxCount;

    @Enumerated(EnumType.STRING)
    @Column(name="PARTICIPANT_TYPE")
    private ParticipantType participantType;

    @CreationTimestamp
    @Column(name = "CREATED_AT")
    private LocalDateTime createdAt;

    @Column(name="START_TIME")
    private LocalDateTime startTime;

    @Column(name="END_TIME")
    private LocalDateTime endTime;

    @Enumerated(EnumType.STRING)
    @Column(name="LEAGUE_STATUS")
    private LeagueStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="MEMBER_ID")
    private Member member;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "CATEGORY_ID")
    private Category category;

    @Builder
    public League(String title, String description, String activityArea, Integer maxCount, LeagueType leagueType,
                  ParticipantType participantType, LocalDateTime startTime, Member member, Category category) {
         this.title = title;
         this.description = description;
         this.activityArea = activityArea;
         this.maxCount = maxCount;
         this.type = leagueType;
         this.participantType = participantType;
         this.startTime = startTime;
         this.member = member;
         this.category = category;
         this.status = LeagueStatus.SCHEDULED;
    }

    public void editTitle(String title) {
        this.title = title;
    }

    public void editDescription(String description) {
        this.description = description;
    }

    public void changeActivityArea(String activityArea) {
        this.activityArea = activityArea;
    }

    public void changeMaxCount(Integer maxCount) {
        this.maxCount = maxCount;
    }

    public void changeStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public void changeCategory(Category category) {
        this.category = category;
    }

    public void startLeague() {
        this.status = LeagueStatus.PROCEEDING;
    }

    public void endLeague() {
        this.status = LeagueStatus.END;
    }
}
