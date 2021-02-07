package com.kookmin.pm.module.mathcing.domain;

import com.kookmin.pm.module.member.domain.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity(name = "MATCHING_PARTICIPANT")
@Getter
@NoArgsConstructor
public class MatchingParticipant {
    @Id @GeneratedValue
    @Column(name = "MATCHING_PARTICIPANT_ID")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name="STATUS")
    private ParticipantStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="MEMBER_ID")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="MATCHING_ID")
    private Matching matching;
}
