package com.kookmin.pm.module.mathcing.repository;

import com.kookmin.pm.module.mathcing.domain.Matching;
import com.kookmin.pm.module.mathcing.domain.MatchingParticipant;
import com.kookmin.pm.module.mathcing.domain.QMatching;
import com.kookmin.pm.module.mathcing.dto.MatchingDetails;
import com.kookmin.pm.module.mathcing.dto.MatchingSearchCondition;
import com.kookmin.pm.module.mathcing.dto.QMatchingDetails;
import com.kookmin.pm.module.member.domain.Member;
import com.kookmin.pm.support.util.PmQuerydslRepositorySupport;
import com.querydsl.core.types.Constant;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Visitor;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.MathExpressions;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.jpa.JPAExpressions;
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
        .select(new QMatchingDetails(matching.id, matching.title, matching.description, matching.startTime,
                matching.endTime, matching.latitude, matching.longitude, matching.status.stringValue(),
                matching.maxCount))
        .from(matching)
        .leftJoin(matching.member, member)
        .where(titleLike(condition.getTitle()),
                statusEq(condition.getStatus()),
                maxCountLt(condition.getMaxCount()),
                hostEmailEq(condition.getHostEmail()))
        .orderBy(matching.startTime.desc()));
    }

    public List<MatchingDetails> searchMatchingWithLocationInformation(MatchingSearchCondition condition) {
        Expression<Double> latitude =
        return getQueryFactory()
                .select(new QMatchingDetails(matching.id, matching.title, matching.description, matching.startTime,
                        matching.endTime, matching.latitude, matching.longitude, matching.status.stringValue(),
                        matching.maxCount,
                        Expressions.as(MathExpressions.radians(), "distance")))
    }

    private NumberExpression<Double> latitude(Double latitude) {
        return latitude==null? null : MathExpressions.acos(matching.longitude-latitude);
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
