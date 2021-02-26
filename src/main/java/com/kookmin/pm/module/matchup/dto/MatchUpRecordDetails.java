package com.kookmin.pm.module.matchup.dto;

import com.kookmin.pm.module.matchup.domain.MatchUp;
import com.kookmin.pm.module.matchup.domain.MatchUpRecord;
import com.kookmin.pm.module.matchup.domain.RecordType;
import com.kookmin.pm.module.member.domain.Member;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
public class MatchUpRecordDetails {
    private Long id;
    private Long winnerUsn;
    private Long loserUsn;
    private String recordType;

    public MatchUpRecordDetails(MatchUpRecord matchUpRecord) {
        this.id = matchUpRecord.getId();
        this.winnerUsn = matchUpRecord.getWinner().getId();
        this.loserUsn = matchUpRecord.getLoser().getId();
        this.recordType = matchUpRecord.getType().toString();
    }
}
