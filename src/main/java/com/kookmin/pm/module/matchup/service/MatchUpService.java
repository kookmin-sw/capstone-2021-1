package com.kookmin.pm.module.matchup.service;

import com.kookmin.pm.module.league.domain.League;
import com.kookmin.pm.module.matching.domain.Matching;
import com.kookmin.pm.module.matching.domain.MatchingParticipant;
import com.kookmin.pm.module.matching.repository.MatchingRepository;
import com.kookmin.pm.module.matchup.domain.MatchUp;
import com.kookmin.pm.module.matchup.dto.MatchUpCreateInfo;
import com.kookmin.pm.module.matchup.repository.MatchUpRepository;
import com.kookmin.pm.module.member.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class MatchUpService {
    private final MatchUpRepository matchUpRepository;
    private final MatchingRepository matchingRepository;

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

    public Long startMatching(@NonNull Long usn,
                              @NonNull Long matchUpId,
                              @NonNull MatchUpCreateInfo matchUpCreateInfo) {
        //TODO::리그에 지역제한있었는데 어떻게 처리할거냐
        MatchUp matchUp = getMatchUpEntity(matchUpId);
        League league = matchUp.getLeague();

        if(!(matchUp.getFirstMember().getId().equals(usn) || matchUp.getSecondMember().getId().equals(usn)))
            throw new RuntimeException();

        //TODO::시작 시간 유효성 체크
        if(matchUpCreateInfo.getStartTime().isBefore(LocalDateTime.now()))
            throw new RuntimeException();

        Matching matching = buildMatchingByMatchUp(league, matchUpCreateInfo);
        matching = matchingRepository.save(matching);
        matchUp.setMatching(matching);

        return matching.getId();
    }

    //TODO::대회 매칭 수락 요청
    public void approveMatchUp(@NonNull Long usn, @NonNull Long matchUpId, @NonNull Long matchingId) {
        Matching matching = getMatchingEntity(matchingId);
        MatchUp matchUp = getMatchUpEntity(matchUpId);

        if(!(matchUp.getFirstMember().getId().equals(usn) || matchUp.getSecondMember().getId().equals(usn)))
            throw new RuntimeException();

        if(!matchUp.getMatching().getId().equals(matchingId))
            throw new RuntimeException();

        //TODO:: 매치업 참가들에게 알려줘야함

        matchUp.startMatchUp();
        matching.startMatching();
    }

    //TODO::대회 매칭 기권패 요청
    public void giveUpMatching() {

    }

    //TODO::매치업 목록 조회

    //TODO::매치업 목록 검색

    //TODO::내 매치업 목록 조회

    private MatchUp getMatchUpEntity(Long matchUpId) {
        return matchUpRepository.findById(matchUpId).orElseThrow(EntityNotFoundException::new);
    }

    private Matching getMatchingEntity(Long matchingId) {
        return matchingRepository.findById(matchingId).orElseThrow(EntityNotFoundException::new);
    }

    private MatchUp buildMatchUp(League league, Member first, Member second) {
        return MatchUp.builder()
                .league(league)
                .first(first)
                .second(second)
                .build();
    }

    private Matching buildMatchingByMatchUp(League league, MatchUpCreateInfo matchUpCreateInfo) {
        return Matching.builder()
                .title(league.getTitle())
                .category(league.getCategory())
                .latitude(matchUpCreateInfo.getLatitude())
                .longitude(matchUpCreateInfo.getLongitude())
                .maxCount(2)
                .startTime(matchUpCreateInfo.getStartTime())
                .build();
    }
}
