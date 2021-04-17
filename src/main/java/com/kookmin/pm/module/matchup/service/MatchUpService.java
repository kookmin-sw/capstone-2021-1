package com.kookmin.pm.module.matchup.service;

import com.kookmin.pm.module.league.domain.League;
import com.kookmin.pm.module.league.repository.LeagueRepository;
import com.kookmin.pm.module.matching.domain.Matching;
import com.kookmin.pm.module.matching.repository.MatchingRepository;
import com.kookmin.pm.module.matchup.domain.MatchUp;
import com.kookmin.pm.module.matchup.domain.MatchUpRecord;
import com.kookmin.pm.module.matchup.domain.MatchUpStatus;
import com.kookmin.pm.module.matchup.domain.RecordType;
import com.kookmin.pm.module.matchup.dto.MatchUpCreateInfo;
import com.kookmin.pm.module.matchup.dto.MatchUpDetails;
import com.kookmin.pm.module.matchup.repository.MatchUpRecordRepository;
import com.kookmin.pm.module.matchup.repository.MatchUpRepository;
import com.kookmin.pm.module.member.domain.Member;
import com.kookmin.pm.module.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class MatchUpService {
    private final MatchUpRepository matchUpRepository;
    private final MatchingRepository matchingRepository;
    private final MemberRepository memberRepository;
    private final MatchUpRecordRepository matchUpRecordRepository;
    private final LeagueRepository leagueRepository;

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

    public void createIndividualTournamentMatchUp(@NonNull League league,
                                                  @NonNull List<Member> participantList) {

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
    public void giveUpMatchUp(@NonNull Long usn, @NonNull Long matchUpId, @NonNull Long matchingId) {
        Matching matching = getMatchingEntity(matchingId);
        MatchUp matchUp = getMatchUpEntity(matchUpId);

        Member first = matchUp.getFirstMember();
        Member second = matchUp.getSecondMember();

        if(!(first.getId().equals(usn) || second.getId().equals(usn)))
            throw new RuntimeException();

        if(!matchUp.getMatching().getId().equals(matchingId))
            throw new RuntimeException();

        matchUp.endMatchUp();
        matching.endMatching();

        Member loser = first.getId().equals(usn)? first : second;
        Member winner = first.getId().equals(usn)? second : first;

        MatchUpRecord record = MatchUpRecord.builder()
                .matchUp(matchUp)
                .winner(winner)
                .loser(loser)
                .type(RecordType.ABSTENTION)
                .build();

        matchUpRecordRepository.save(record);
    }

    //TODO::매치업 끝내기(승/패 처리)
    public void endMathUp(@NonNull Long winnerUsn, @NonNull Long loserUsn, @NonNull Long matchUpId) {
        MatchUp matchUp = getMatchUpEntity(matchUpId);
        Member firstMember = matchUp.getFirstMember();
        Member secondMember = matchUp.getSecondMember();
        Matching matching = matchUp.getMatching();

        //TODO::진행중인 매치업이 아닌 경우
        if(!matchUp.getStatus().equals(MatchUpStatus.PROCEEDING))
            throw new RuntimeException();

        //TODO::winner 혹은 loser가 매치업에 속해있지 않은 경우
        if(!(firstMember.getId().equals(winnerUsn)
                || secondMember.getId().equals(winnerUsn)))
            throw  new RuntimeException();

        if(!(firstMember.getId().equals(loserUsn)
                || secondMember.getId().equals(loserUsn)))
            throw  new RuntimeException();

        //TODO::둘다 같은 회원을 winner와 loser로 요청했을 경우
        if(winnerUsn.equals(loserUsn))
            throw new RuntimeException();

        matchUp.endMatchUp();
        matching.endMatching();

        Member winner = firstMember.getId().equals(winnerUsn)?firstMember:secondMember;
        Member loser = firstMember.getId().equals(loserUsn)?firstMember:secondMember;

        MatchUpRecord matchUpRecord = MatchUpRecord.builder()
                .winner(winner)
                .loser(loser)
                .matchUp(matchUp)
                .type(RecordType.MATCH_UP)
                .build();

        matchUpRecordRepository.save(matchUpRecord);
    }

    //TODO::매치업 목록 조회
    public List<MatchUpDetails> getMatchUpListByLeague(@NonNull Long leagueId) {
        League league = getLeagueEntity(leagueId);

        List<MatchUp> matchUpList = matchUpRepository.findByLeague(league);
        List<MatchUpDetails> matchUpDetailsList = new ArrayList<>();

        for(MatchUp matchUp : matchUpList) {
            MatchUpRecord record = getMatchUpRecordEntityByMatchUp(matchUp);
            matchUpDetailsList.add(new MatchUpDetails(matchUp, record));
        }

        return matchUpDetailsList;
    }

    //TODO::내 매치업 목록 조회
    public List<MatchUpDetails> searchMyMatchUp(@NonNull Long usn) {
        Member member = getMemberEntity(usn);
        List<MatchUp> matchUpList = matchUpRepository.findByFirstMemberOrSecondMember(member, member);

        List<MatchUpDetails> matchUpDetailsList = new ArrayList<>();

        for(MatchUp matchUp : matchUpList) {
            matchUpDetailsList.add(new MatchUpDetails(matchUp));
        }

        return matchUpDetailsList;
    }

    public MatchUpDetails lookUpMatchUp(@NonNull Long matchUpId, @NonNull MatchUpLookUpType lookUpType) {
        MatchUpDetails matchUpDetails = null;
        MatchUp matchUp = getMatchUpEntity(matchUpId);

        if(lookUpType.equals(MatchUpLookUpType.WITH_RECORD)) {
            MatchUpRecord record = getMatchUpRecordEntityByMatchUp(matchUp);
            matchUpDetails = new MatchUpDetails(matchUp, record);
        } else if(lookUpType.equals(MatchUpLookUpType.WITH_ALL_INFOS)) {
            MatchUpRecord record = getMatchUpRecordEntityByMatchUp(matchUp);
            League league = matchUp.getLeague();
            matchUpDetails = new MatchUpDetails(matchUp, record, league);
        } else {
            matchUpDetails = new MatchUpDetails(matchUp);
        }

        return matchUpDetails;
    }

    private Member getMemberEntity(Long usn) {
        return memberRepository.findById(usn).orElseThrow(EntityNotFoundException::new);
    }

    private MatchUp getMatchUpEntity(Long matchUpId) {
        return matchUpRepository.findById(matchUpId).orElseThrow(EntityNotFoundException::new);
    }

    private Matching getMatchingEntity(Long matchingId) {
        return matchingRepository.findById(matchingId).orElseThrow(EntityNotFoundException::new);
    }

    private League getLeagueEntity(Long leagueId) {
        return leagueRepository.findById(leagueId).orElseThrow(EntityNotFoundException::new);
    }

    private MatchUpRecord getMatchUpRecordEntityByMatchUp(MatchUp matchUp) {
        return matchUpRecordRepository.findByMatchUp(matchUp).orElse(null);
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
