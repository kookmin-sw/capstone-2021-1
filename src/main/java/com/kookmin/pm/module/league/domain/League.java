package com.kookmin.pm.module.league.domain;

import com.kookmin.pm.module.category.domain.Category;
import com.kookmin.pm.module.member.domain.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;
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
}
