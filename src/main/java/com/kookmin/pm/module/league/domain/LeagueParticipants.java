package com.kookmin.pm.module.league.domain;

import com.kookmin.pm.module.member.domain.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@Table(name="LEAGUE_PARTICIPANTS")
public class LeagueParticipants {
    @Id @GeneratedValue
    @Column(name = "LEAGUE_PARTICIPANTS_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="LEAGUE_ID")
    private League league;

    @Enumerated(EnumType.STRING)
    @Column(name="STATUS")
    private LeagueParticipantsStatus status;

    @Builder
    public LeagueParticipants(Member member, League league) {
        this.member = member;
        this.league = league;
        this.status = LeagueParticipantsStatus.PENDING;
    }
}
