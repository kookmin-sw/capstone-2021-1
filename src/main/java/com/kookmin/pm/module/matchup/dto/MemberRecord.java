package com.kookmin.pm.module.matchup.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberRecord {
    private int winCount;
    private int loseCount;

    public MemberRecord() {

    }

    public MemberRecord(int winCount, int loseCount) {
        this.winCount = winCount;
        this.loseCount = loseCount;
    }
}
