package com.kookmin.pm.module.matchup.domain;

import com.kookmin.pm.module.league.domain.League;
import com.kookmin.pm.module.matching.domain.Matching;
import com.kookmin.pm.module.member.domain.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
@Table(name="MATCH_UP")
public class MatchUp {

    @Id @GeneratedValue
    @Column(name="MATCH_UP_ID")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name="STATUS")
    private MatchUpStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FIRST_MEMBER_ID")
    private Member firstMember;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SECOND_MEMBER_ID")
    private Member secondMember;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="LEAGUE_ID")
    private League league;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="MATCHING_ID")
    private Matching matching;

    @Builder
    public MatchUp(League league, Member first, Member second) {
        this.league = league;
        this.firstMember = first;
        this.secondMember = second;
        this.status = MatchUpStatus.PENDING;
    }

    public void startMatchUp() {
        this.status = MatchUpStatus.PROCEEDING;
    }

    public void setMatching(Matching matching) {
        this.matching = matching;
    }
}
