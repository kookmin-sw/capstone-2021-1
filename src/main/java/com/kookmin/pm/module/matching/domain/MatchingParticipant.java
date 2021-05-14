package com.kookmin.pm.module.matching.domain;

import com.kookmin.pm.module.member.domain.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "MATCHING_PARTICIPANT")
public class MatchingParticipant {
    @Id @GeneratedValue
    @Column(name = "MATCHING_PARTICIPANT_ID")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name="STATUS")
    private ParticipantStatus status;

    @CreationTimestamp
    @Column(name="CREATED_AT")
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="MEMBER_ID")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="MATCHING_ID")
    private Matching matching;

    @Builder
    public MatchingParticipant(Member member, Matching matching) {
        this.status = ParticipantStatus.PENDING_ACCEPTANCE;
        this.member = member;
        this.matching = matching;
    }

    public void approveMatching() {
        this.status = ParticipantStatus.PARTICIPATING;
    }
}
