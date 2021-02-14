package com.kookmin.pm.module.matchup.domain;

import com.kookmin.pm.module.league.domain.League;
import com.kookmin.pm.module.matching.domain.Matching;
import com.kookmin.pm.module.member.domain.Member;
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
    @JoinColumn(name = "MEMBER_ID")
    private Member participant1;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_ID")
    private Member participant2;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="LEAGUE_ID")
    private League league;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="MATCHING_ID")
    private Matching matching;
}
