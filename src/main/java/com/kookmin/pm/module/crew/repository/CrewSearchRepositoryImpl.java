package com.kookmin.pm.module.crew.repository;

import com.kookmin.pm.module.category.domain.QCategory;
import com.kookmin.pm.module.crew.domain.Crew;
import com.kookmin.pm.module.crew.domain.QCrew;
import com.kookmin.pm.module.crew.domain.QCrewParticipants;
import com.kookmin.pm.module.crew.dto.CrewDetails;
import com.kookmin.pm.module.crew.dto.CrewSearchCondition;
import com.kookmin.pm.module.crew.dto.QCrewDetails;
import com.kookmin.pm.module.member.domain.Member;
import com.kookmin.pm.support.util.PmQuerydslRepositorySupport;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.kookmin.pm.module.category.domain.QCategory.*;
import static com.kookmin.pm.module.crew.domain.QCrew.*;
import static com.kookmin.pm.module.crew.domain.QCrewParticipants.*;
import static com.kookmin.pm.module.member.domain.QMember.*;

@Repository
public class CrewSearchRepositoryImpl extends PmQuerydslRepositorySupport implements CrewSearchRepository{
    public CrewSearchRepositoryImpl() {
        super(Crew.class);
    }

    @Override
    public List<Member> findMemberInCrewParticipants(Long crewId) {
        return getQueryFactory()
                .select(member)
                .from(crewParticipants)
                .leftJoin(crewParticipants.member, member)
                .where(crewIdEq(crewId))
                .fetch();
    }

    @Override
    public Page<CrewDetails> searchCrew(Pageable pageable, CrewSearchCondition searchCondition) {
        return applyPagination(pageable, contentQuery -> contentQuery
        .select(new QCrewDetails(crew.id, crew.name, crew.description, crew.maxCount, crew.activityArea,
                category.name, crew.createdAt))
        .from(crew)
        .leftJoin(crew.category, category)
        .leftJoin(crew.member, member)
        .where(nameContains(searchCondition.getName()),
                maxCountLoe(searchCondition.getMaxCount()),
                activityAreaEq(searchCondition.getActivityArea()),
                categoryEq(searchCondition.getCategory()),
                hostEq(searchCondition.getHost()))
        .orderBy(crew.createdAt.desc())
        );
    }

    public BooleanExpression nameContains(String name) {
        return name==null? null : crew.name.contains(name);
    }

    public BooleanExpression maxCountLoe(Integer maxCount) {
        return maxCount==null? null : crew.maxCount.loe(maxCount);
    }

    public BooleanExpression activityAreaEq(String activityArea) {
        return activityArea==null? null : crew.activityArea.eq(activityArea);
    }

    public BooleanExpression categoryEq(String category) {
        return category==null? null : crew.category.name.eq(category);
    }

    public BooleanExpression hostEq(String host) {
        return host==null? null : crew.member.uid.eq(host);
    }

    public BooleanExpression crewIdEq(Long crewId) {
        return crewId==null? null : crewParticipants.crew.id.eq(crewId);
    }
}
