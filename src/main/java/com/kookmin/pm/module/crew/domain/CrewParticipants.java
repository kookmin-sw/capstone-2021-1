package com.kookmin.pm.module.crew.domain;

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
@Table(name="CREW_PARTICIPANTS")
public class CrewParticipants {
    @Id @GeneratedValue
    @Column(name="CREW_PARTICIPANTS_ID")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS")
    private CrewParticipantStatus status;

    @CreationTimestamp
    @Column(name = "CREATED_AT")
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="MEMBER_ID")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="CREW_ID")
    private Crew crew;

    @Builder
    public CrewParticipants(Member member, Crew crew) {
        this.member = member;
        this.crew = crew;
        this.status = CrewParticipantStatus.PENDING;
    }
}
