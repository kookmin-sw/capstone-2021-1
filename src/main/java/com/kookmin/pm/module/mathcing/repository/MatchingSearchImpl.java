package com.kookmin.pm.module.mathcing.repository;

import com.kookmin.pm.module.mathcing.domain.Matching;
import com.kookmin.pm.module.mathcing.domain.QMatching;
import com.kookmin.pm.module.mathcing.domain.QMatchingParticipant;
import com.kookmin.pm.module.member.domain.Member;
import com.kookmin.pm.module.member.domain.QMember;
import com.kookmin.pm.support.util.PmQuerydslRepositorySupport;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.springframework.data.domain.Page;

import java.util.List;

import static com.kookmin.pm.module.mathcing.domain.QMatchingParticipant.*;
import static com.kookmin.pm.module.member.domain.QMember.*;

public class MatchingSearchImpl extends PmQuerydslRepositorySupport {
    public MatchingSearchImpl(Class<?> domainClass) {
        super(Matching.class);
    }

    public List<Member> searchMemberInMatchingParticipant(Long matchingId) {
        return select(member)
                .from(matchingParticipant)
                .leftJoin(matchingParticipant.member, member)
                .where(matchingIdEq(matchingId))
                .fetch();
    }

    private BooleanExpression matchingIdEq(Long matchingId) {
        return matchingId==null? null : matchingParticipant.matching.id.eq(matchingId);
    }
}
