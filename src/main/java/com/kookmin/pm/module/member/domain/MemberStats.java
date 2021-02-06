package com.kookmin.pm.module.member.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity(name = "MEMBER_STATS")
@Getter
@NoArgsConstructor
public class MemberStats {
    @Id @GeneratedValue
    @Column(name="MEMBER_STATS_ID")
    private Long id;

    @Column(name="MANNER")
    private Long manner;

    @Column(name="AFFINITY")
    private Long affinity;

    @Column(name="PHYSICAL")
    private Long physical;

    @Column(name="INTELLECT")
    private Long intellect;

    @Column(name="COMPREHENSION")
    private Long comprehension;

    @Column(name="EVALUATE_COUNT")
    private Long evaluateCount;

    @OneToOne
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @Builder
    public MemberStats(Member member) {
        this.manner = 0L;
        this.affinity = 0L;
        this.physical = 0L;
        this.physical = 0L;
        this.intellect = 0L;
        this.comprehension = 0L;
        this.evaluateCount = 0L;
        this.member = member;
    }

    public void evaluateStats(Long manner, Long affinity, Long physical, Long intellect, Long comprehension) {
        this.manner += manner;
        this.affinity += affinity;
        this.physical += physical;
        this.intellect += intellect;
        this.comprehension += comprehension;
        this.evaluateCount += 1L;
    }
}
