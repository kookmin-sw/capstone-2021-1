package com.kookmin.pm.module.member.dto;

import com.kookmin.pm.module.member.domain.MemberStats;
import lombok.Data;

@Data
public class MemberStatsInfo {
    private double manner;
    private double affinity;
    private double physical;
    private double intellect;
    private double comprehension;

    public MemberStatsInfo(MemberStats memberStats) {
        if(memberStats.getEvaluateCount() != 0) {
            this.manner = memberStats.getManner() / (double) memberStats.getEvaluateCount();
            this.affinity = memberStats.getAffinity() / (double) memberStats.getEvaluateCount();
            this.physical = memberStats.getPhysical() / (double) memberStats.getEvaluateCount();
            this.intellect = memberStats.getIntellect() / (double) memberStats.getEvaluateCount();
            this.comprehension = memberStats.getComprehension() / (double) memberStats.getComprehension();
        }
    }
}
