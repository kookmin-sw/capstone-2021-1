package com.kookmin.pm.module.league.domain;

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
}
