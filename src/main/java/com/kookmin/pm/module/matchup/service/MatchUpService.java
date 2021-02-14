package com.kookmin.pm.module.matchup.service;

import com.kookmin.pm.module.league.domain.League;
import com.kookmin.pm.module.matchup.domain.MatchUp;
import com.kookmin.pm.module.matchup.repository.MatchUpRepository;
import com.kookmin.pm.module.member.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class MatchUpService {
    private final MatchUpRepository matchUpRepository;

    public void createIndividualLeagueMatchUp(@NonNull League league,
                                              @NonNull List<Member> participantList) {

        for(int i = 0; i < participantList.size(); ++i) {
            Member first = participantList.get(i);

            for(int j  = i+1; j < participantList.size(); ++j) {
                Member second = participantList.get(j);

                MatchUp matchUp = buildMatchUp(league, first, second);
                matchUpRepository.save(matchUp);
            }
        }
    }

    //TODO::토너먼트 매치업

    public void startMatching(@NonNull Long usn, @NonNull Long matchUpId) {
        //TODO::리그에 지역제한있었는데 어떻게 처리할거냐
        MatchUp matchUp = getMatchUpEntity(matchUpId);
    }

    private MatchUp getMatchUpEntity(Long matchUpId) {
        return matchUpRepository.findById(matchUpId).orElseThrow(EntityNotFoundException::new);
    }

    private MatchUp buildMatchUp(League league, Member first, Member second) {
        return MatchUp.builder()
                .league(league)
                .first(first)
                .second(second)
                .build();
    }
}
