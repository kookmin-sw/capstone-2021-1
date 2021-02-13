package com.kookmin.pm.module.league.repository;

import com.kookmin.pm.module.category.domain.QCategory;
import com.kookmin.pm.module.league.domain.*;
import com.kookmin.pm.module.league.dto.LeagueDetails;
import com.kookmin.pm.module.league.dto.LeagueSearchCondition;
import com.kookmin.pm.module.league.dto.QLeagueDetails;
import com.kookmin.pm.module.member.domain.Member;
import com.kookmin.pm.module.member.domain.QMember;
import com.kookmin.pm.support.util.PmQuerydslRepositorySupport;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.kookmin.pm.module.category.domain.QCategory.*;
import static com.kookmin.pm.module.league.domain.QLeague.*;
import static com.kookmin.pm.module.league.domain.QLeagueParticipants.*;
import static com.kookmin.pm.module.member.domain.QMember.*;

@Repository
public class LeagueSearchRepositoryImpl extends PmQuerydslRepositorySupport implements LeagueSearchRepository {
    public LeagueSearchRepositoryImpl() {
        super(League.class);
    }

    @Override
    public List<Member> findMemberInLeague(Long leagueId, LeagueParticipantsStatus status) {
        return getQueryFactory()
                .select(member)
                .from(leagueParticipants)
                .leftJoin(leagueParticipants.member, member)
                .where(leagueParticipantsLeagueEq(leagueId),
                        leagueParticipantsStatusEq(status))
                .fetch();
    }

    @Override
    public Page<LeagueDetails> searchLeague(Pageable pageable, LeagueSearchCondition searchCondition) {
        return applyPagination(pageable, contentQuery -> contentQuery
        .select(new QLeagueDetails(league.id, league.title, league.description, league.activityArea,
                league.type, league.maxCount, league.participantType, league.createdAt, league.startTime,
                league.endTime, league.status, category.name))
        .from(league)
        .leftJoin(league.category, category)
        .leftJoin(league.member, member)
        .where(titleContains(searchCondition.getTitle()),
                activityAreaEq(searchCondition.getActivityArea()),
                leagueTypeEq(searchCondition.getLeagueType()),
                maxCountLoe(searchCondition.getMaxCount()),
                participantTypeEq(searchCondition.getParticipantType()),
                statusEq(searchCondition.getStatus()),
                hostEq(searchCondition.getHost()),
                categoryEq(searchCondition.getCategory()))
        );
    }

    public BooleanExpression leagueParticipantsStatusEq(LeagueParticipantsStatus status) {
        return status==null ? null : leagueParticipants.status.eq(status);
    }

    public BooleanExpression leagueParticipantsLeagueEq(Long leagueId) {
        return leagueId==null ? null : leagueParticipants.league.id.eq(leagueId);
    }

    public BooleanExpression titleContains(String title) {
        return title==null? null : league.title.contains(title);
    }

    public BooleanExpression activityAreaEq(String activityArea) {
        return activityArea==null? null : league.activityArea.eq(activityArea);
    }

    public BooleanExpression leagueTypeEq(String leagueType) {
        return leagueType==null? null : league.type.eq(LeagueType.valueOf(leagueType));
    }

    public BooleanExpression maxCountLoe(Integer maxCount) {
        return maxCount==null? null : league.maxCount.loe(maxCount);
    }

    public BooleanExpression participantTypeEq(String participantType) {
        return participantType==null? null : league.participantType.eq(ParticipantType.valueOf(participantType));
    }

    public BooleanExpression statusEq(String status) {
        return status==null? null : league.status.eq(LeagueStatus.valueOf(status));
    }

    public BooleanExpression hostEq(Long host) {
        return host==null? null : league.member.id.eq(host);
    }

    public BooleanExpression categoryEq(String category) {
        return category==null? null : league.category.name.eq(category);
    }
}
