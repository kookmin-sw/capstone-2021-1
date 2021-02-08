package com.kookmin.pm.module.mathcing.repository;

import com.kookmin.pm.module.mathcing.domain.Matching;
import com.kookmin.pm.module.mathcing.domain.MatchingParticipant;
import com.kookmin.pm.module.mathcing.domain.QMatching;
import com.kookmin.pm.module.mathcing.dto.MatchingDetails;
import com.kookmin.pm.module.mathcing.dto.MatchingSearchCondition;
import com.kookmin.pm.module.member.domain.Member;
import com.kookmin.pm.support.util.PmQuerydslRepositorySupport;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.kookmin.pm.module.mathcing.domain.QMatching.*;
import static com.kookmin.pm.module.mathcing.domain.QMatchingParticipant.*;
import static com.kookmin.pm.module.member.domain.QMember.*;
import static com.querydsl.core.types.dsl.MathExpressions.*;

@Repository
public class MatchingSearchRepositoryImpl extends PmQuerydslRepositorySupport implements MatchingSearchRepository{
    public MatchingSearchRepositoryImpl() {
        super(Matching.class);
    }

    public List<Member> searchMemberInMatchingParticipant(Long matchingId) {
        return getQueryFactory()
                .select(member)
                .from(matchingParticipant)
                .leftJoin(matchingParticipant.member, member)
                .where(matchingIdEq(matchingId))
                .fetch();
    }

    public Page<MatchingDetails> searchMatching(Pageable pageable, MatchingSearchCondition condition) {
        return applyPagination(pageable, contentQuery -> contentQuery
        .select()
        .from(matching)
        .leftJoin(matching.member, member)
        .where(titleLike(condition.getTitle()),
                statusEq(condition.getStatus()),
                maxCountLt(condition.getMaxCount()),
                hostEmailEq(condition.getHostEmail())));
    }

    private BooleanExpression titleLike(String title) {
        return title==null? null : matching.title.contains(title);
    }

    private BooleanExpression statusEq(String status) {
        return status==null? null : matching.status.stringValue().eq(status);
    }

    private BooleanExpression maxCountLt(Integer maxCount) {
        return maxCount==null? null : matching.maxCount.loe(maxCount);
    }

    private BooleanExpression hostEmailEq(String email) {
        return email==null? null : member.email.eq(email);
    }

    private BooleanExpression matchingIdEq(Long matchingId) {
        return matchingId==null? null : matchingParticipant.matching.id.eq(matchingId);
    }
}
