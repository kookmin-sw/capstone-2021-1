package com.kookmin.pm.module.crew.repository;

import com.kookmin.pm.module.crew.domain.Crew;
import com.kookmin.pm.module.crew.domain.QCrew;
import com.kookmin.pm.module.crew.domain.QCrewParticipants;
import com.kookmin.pm.module.member.domain.Member;
import com.kookmin.pm.support.util.PmQuerydslRepositorySupport;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.stereotype.Repository;

import java.util.List;

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

    public BooleanExpression crewIdEq(Long crewId) {
        return crewId==null? null : crewParticipants.crew.id.eq(crewId);
    }
}
