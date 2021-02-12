package com.kookmin.pm.module.matching.repository;

import com.kookmin.pm.module.category.domain.QCategory;
import com.kookmin.pm.module.matching.domain.Matching;
import com.kookmin.pm.module.matching.domain.MatchingStatus;
import com.kookmin.pm.module.matching.domain.ParticipantStatus;
import com.kookmin.pm.module.matching.dto.MatchingDetails;
import com.kookmin.pm.module.matching.dto.MatchingSearchCondition;
import com.kookmin.pm.module.matching.dto.QMatchingDetails;
import com.kookmin.pm.module.member.domain.Member;
import com.kookmin.pm.support.util.PmQuerydslRepositorySupport;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.kookmin.pm.module.category.domain.QCategory.*;
import static com.kookmin.pm.module.matching.domain.QMatching.*;
import static com.kookmin.pm.module.matching.domain.QMatchingParticipant.*;
import static com.kookmin.pm.module.member.domain.QMember.*;

@Repository
public class MatchingSearchRepositoryImpl extends PmQuerydslRepositorySupport implements MatchingSearchRepository{
    public MatchingSearchRepositoryImpl() {
        super(Matching.class);
    }

    public List<Member> searchMemberInMatchingParticipant(Long matchingId, ParticipantStatus status) {
        return getQueryFactory()
                .select(member)
                .from(matchingParticipant)
                .leftJoin(matchingParticipant.member, member)
                .where(matchingIdEq(matchingId),
                        matchingParticipant.status.eq(status))
                .fetch();
    }

    public Page<MatchingDetails> searchMatching(Pageable pageable, MatchingSearchCondition condition) {
        return applyPagination(pageable, contentQuery -> contentQuery
        .select(new QMatchingDetails(matching.id, matching.title, matching.description, matching.startTime,
                matching.endTime, matching.latitude, matching.longitude, matching.status.stringValue(),
                matching.maxCount, category.name))
        .from(matching)
        .leftJoin(matching.member, member)
        .leftJoin(matching.category, category)
        .where(titleContains(condition.getTitle()),
                statusEq(condition.getStatus()),
                maxCountLt(condition.getMaxCount()),
                hostEq(condition.getHost()),
                categoryEq(condition.getCategory()))
        .orderBy(matching.startTime.desc()));
    }

    private BooleanExpression titleContains(String title) {
        return title==null? null : matching.title.contains(title);
    }

    private BooleanExpression categoryEq(String categoryName) {
        return categoryName==null? null : category.name.eq(categoryName);
    }

    private BooleanExpression statusEq(String status) {
        return status==null? null : matching.status.stringValue().eq(status);
    }

    private BooleanExpression maxCountLt(Integer maxCount) {
        return maxCount==null? null : matching.maxCount.loe(maxCount);
    }

    private BooleanExpression hostEq(Long id) {
        return id==null? null : member.id.eq(id);
    }

    private BooleanExpression matchingIdEq(Long matchingId) {
        return matchingId==null? null : matchingParticipant.matching.id.eq(matchingId);
    }
}
