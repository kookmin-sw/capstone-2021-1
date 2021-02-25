package com.kookmin.pm.module.matchup.service;

import com.kookmin.pm.module.category.domain.Category;
import com.kookmin.pm.module.category.repository.CategoryRepository;
import com.kookmin.pm.module.league.domain.League;
import com.kookmin.pm.module.league.domain.LeagueParticipantsStatus;
import com.kookmin.pm.module.league.dto.LeagueCreateInfo;
import com.kookmin.pm.module.league.repository.LeagueParticipantsRepository;
import com.kookmin.pm.module.league.repository.LeagueRepository;
import com.kookmin.pm.module.league.repository.LeagueSearchRepository;
import com.kookmin.pm.module.league.service.LeagueService;
import com.kookmin.pm.module.matching.domain.Matching;
import com.kookmin.pm.module.matching.domain.MatchingStatus;
import com.kookmin.pm.module.matching.repository.MatchingRepository;
import com.kookmin.pm.module.matchup.domain.MatchUp;
import com.kookmin.pm.module.matchup.domain.MatchUpRecord;
import com.kookmin.pm.module.matchup.domain.MatchUpStatus;
import com.kookmin.pm.module.matchup.domain.RecordType;
import com.kookmin.pm.module.matchup.dto.MatchUpCreateInfo;
import com.kookmin.pm.module.matchup.repository.MatchUpRecordRepository;
import com.kookmin.pm.module.matchup.repository.MatchUpRepository;
import com.kookmin.pm.module.member.domain.Member;
import com.kookmin.pm.module.member.dto.MemberCreateInfo;
import com.kookmin.pm.module.member.repository.MemberRepository;
import com.kookmin.pm.module.member.service.MemberService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("local")
@SpringBootTest
@Transactional
class MatchUpServiceTest {
    @Autowired
    private MatchUpService matchUpService;
    @Autowired
    private MemberService memberService;
    @Autowired
    private LeagueService leagueService;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private LeagueRepository leagueRepository;
    @Autowired
    private LeagueParticipantsRepository leagueParticipantsRepository;
    @Autowired
    private LeagueSearchRepository leagueSearchRepositoryImpl;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private MatchUpRepository matchUpRepository;
    @Autowired
    private MatchingRepository matchingRepository;
    @Autowired
    private MatchUpRecordRepository matchUpRecordRepository;

    private Long leagueId;
    private Long usn;

    @BeforeEach
    public void setUp() {
        MemberCreateInfo memberCreateInfo = new MemberCreateInfo();
        memberCreateInfo.setUid("dlwlsrn9412@kookmin.ac.kr");
        memberCreateInfo.setPassword("1234");
        memberCreateInfo.setNickname("jingu2");
        memberCreateInfo.setAddress("서울시~~~");
        memberCreateInfo.setName("이진구");
        memberCreateInfo.setPhoneNumber("010-8784-3827");

        usn = memberService.joinMember(memberCreateInfo);

        MemberCreateInfo memberCreateInfo2 = new MemberCreateInfo();
        memberCreateInfo2.setUid("dlwlsrn10@kookmin.ac.kr");
        memberCreateInfo2.setPassword("1234");
        memberCreateInfo2.setNickname("jingu2");
        memberCreateInfo2.setAddress("서울시~~~");
        memberCreateInfo2.setName("이진팔");
        memberCreateInfo2.setPhoneNumber("010-8784-3827");

        Long usn2 = memberService.joinMember(memberCreateInfo2);

        MemberCreateInfo memberCreateInfo3 = new MemberCreateInfo();
        memberCreateInfo3.setUid("dlwlsrn7@kookmin.ac.kr");
        memberCreateInfo3.setPassword("1234");
        memberCreateInfo3.setNickname("jingu3");
        memberCreateInfo3.setAddress("서울시~~~");
        memberCreateInfo3.setName("이진칠");
        memberCreateInfo3.setPhoneNumber("010-8784-3827");

        Long usn3 = memberService.joinMember(memberCreateInfo3);

        MemberCreateInfo memberCreateInfo4 = new MemberCreateInfo();
        memberCreateInfo4.setUid("dlwlsrn6@kookmin.ac.kr");
        memberCreateInfo4.setPassword("1234");
        memberCreateInfo4.setNickname("jingu4");
        memberCreateInfo4.setAddress("서울시~~~");
        memberCreateInfo4.setName("이진육");
        memberCreateInfo4.setPhoneNumber("010-8784-3827");

        Long usn4 = memberService.joinMember(memberCreateInfo4);

        Category category = new Category("BOARD_GAME");
        categoryRepository.save(category);

        Category category2 = new Category("ROOM_ESCAPE");
        categoryRepository.save(category2);

        LocalDateTime startTime = LocalDateTime.of(2021, 11, 20, 12, 0, 0);

        LeagueCreateInfo leagueCreateInfo = new LeagueCreateInfo();
        leagueCreateInfo.setActivityArea("서울");
        leagueCreateInfo.setCategory("BOARD_GAME");
        leagueCreateInfo.setDescription("리그 소개 서울");
        leagueCreateInfo.setLeagueType("LEAGUE");
        leagueCreateInfo.setMaxCount(30);
        leagueCreateInfo.setParticipantType("INDIVIDUAL");
        leagueCreateInfo.setStartTime(startTime);
        leagueCreateInfo.setTitle("서울 섯다 리그");

        leagueId = leagueService.openLeague(usn, leagueCreateInfo);

        long requestId = leagueService.participateLeague(usn2, leagueId);
        long requestId2 = leagueService.participateLeague(usn3, leagueId);
        long requestId3 = leagueService.participateLeague(usn4, leagueId);
        leagueService.approveParticipationRequest(usn, requestId);
        leagueService.approveParticipationRequest(usn, requestId2);
        leagueService.approveParticipationRequest(usn, requestId3);
    }

    @Test
    @DisplayName("createIndividualLeagueMatchUp 메소드 성공 테스트")
    public void createIndividualLeagueMatchUp_success_test() {
        League league = leagueRepository.findById(leagueId).get();

        List<Member> participants =
                leagueSearchRepositoryImpl.findMemberInLeague(leagueId, LeagueParticipantsStatus.PARTICIPATING);

        Member host = memberRepository.findById(usn).get();
        participants.add(host);

        assertThat(participants.size())
                .isEqualTo(4);

        matchUpService.createIndividualLeagueMatchUp(league, participants);

        assertThat(matchUpRepository.findAll().size())
                .isEqualTo(6);
    }

    @Test
    @DisplayName("startMatching 메소드 성공 테스트")
    public void startMatching_success_test() {
        League league = leagueRepository.findById(leagueId).get();

        List<Member> participants =
                leagueSearchRepositoryImpl.findMemberInLeague(leagueId, LeagueParticipantsStatus.PARTICIPATING);

        Member host = memberRepository.findById(usn).get();
        participants.add(host);

        assertThat(participants.size())
                .isEqualTo(4);

        matchUpService.createIndividualLeagueMatchUp(league, participants);

        List<MatchUp> matchUpList = matchUpRepository
                .findByFirstMemberOrSecondMemberAndLeague(host, host,league);
        MatchUp matchUp = matchUpList.get(0);

        MatchUpCreateInfo matchUpCreateInfo = new MatchUpCreateInfo();
        LocalDateTime startTime = LocalDateTime.of(2021, 12, 12, 12,0,0);

        matchUpCreateInfo.setLatitude(30.500);
        matchUpCreateInfo.setLongitude(128.123);
        matchUpCreateInfo.setStartTime(startTime);

        Long matchingId = matchUpService.startMatching(usn, matchUp.getId(), matchUpCreateInfo);

        Matching matching = matchingRepository.findById(matchingId).get();

        assertThat(matching)
                .hasFieldOrPropertyWithValue("title", league.getTitle())
                .hasFieldOrPropertyWithValue("status", MatchingStatus.SCHEDULED);
    }

    @Test
    @DisplayName("approveMatchUp 메소드 성공 테스트")
    public void approveMatchUp_success_test() {
        League league = leagueRepository.findById(leagueId).get();

        List<Member> participants =
                leagueSearchRepositoryImpl.findMemberInLeague(leagueId, LeagueParticipantsStatus.PARTICIPATING);

        Member host = memberRepository.findById(usn).get();
        participants.add(host);

        assertThat(participants.size())
                .isEqualTo(4);

        matchUpService.createIndividualLeagueMatchUp(league, participants);

        List<MatchUp> matchUpList = matchUpRepository
                .findByFirstMemberOrSecondMemberAndLeague(host, host, league);
        MatchUp matchUp = matchUpList.get(0);

        MatchUpCreateInfo matchUpCreateInfo = new MatchUpCreateInfo();
        LocalDateTime startTime = LocalDateTime.of(2021, 12, 12, 12,0,0);

        matchUpCreateInfo.setLatitude(30.500);
        matchUpCreateInfo.setLongitude(128.123);
        matchUpCreateInfo.setStartTime(startTime);

        Long matchingId = matchUpService.startMatching(usn, matchUp.getId(), matchUpCreateInfo);
        matchUpService.approveMatchUp(host.getId(), matchUp.getId(), matchingId);

        Matching matching = matchingRepository.findById(matchingId).get();
        MatchUp recentMatchUp = matchUpRepository.findById(matchUp.getId()).get();

        assertThat(matching)
                .hasFieldOrPropertyWithValue("status", MatchingStatus.PROCEEDING);

        assertThat(recentMatchUp)
                .hasFieldOrPropertyWithValue("status", MatchUpStatus.PROCEEDING)
                .hasFieldOrPropertyWithValue("matching", matching);
    }

    @Test
    @DisplayName("giveUpMatchUp 메소드 성공 테스트")
    public void giveUpMatchUp_success_test() {
        League league = leagueRepository.findById(leagueId).get();

        List<Member> participants =
                leagueSearchRepositoryImpl.findMemberInLeague(leagueId, LeagueParticipantsStatus.PARTICIPATING);

        Member host = memberRepository.findById(usn).get();
        participants.add(host);

        assertThat(participants.size())
                .isEqualTo(4);

        matchUpService.createIndividualLeagueMatchUp(league, participants);

        List<MatchUp> matchUpList = matchUpRepository
                .findByFirstMemberOrSecondMemberAndLeague(host, host, league);
        MatchUp matchUp = matchUpList.get(0);

        MatchUpCreateInfo matchUpCreateInfo = new MatchUpCreateInfo();
        LocalDateTime startTime = LocalDateTime.of(2021, 12, 12, 12,0,0);

        matchUpCreateInfo.setLatitude(30.500);
        matchUpCreateInfo.setLongitude(128.123);
        matchUpCreateInfo.setStartTime(startTime);

        Long matchingId = matchUpService.startMatching(usn, matchUp.getId(), matchUpCreateInfo);
        matchUpService.giveUpMatchUp(host.getId(), matchUp.getId(), matchingId);

        Matching matching = matchingRepository.findById(matchingId).get();
        MatchUp recentMatchUp = matchUpRepository.findById(matchUp.getId()).get();

        MatchUpRecord record = matchUpRecordRepository.findByWinnerOrLoser(host,host).get(0);

        assertThat(matching).hasFieldOrPropertyWithValue("status", MatchingStatus.END);
        assertThat(recentMatchUp).hasFieldOrPropertyWithValue("status", MatchUpStatus.END);
        assertThat(record)
                .hasFieldOrPropertyWithValue("type", RecordType.ABSTENTION)
                .hasFieldOrPropertyWithValue("matchUp", matchUp)
                .hasFieldOrPropertyWithValue("loser", host);
    }

    @Test
    @DisplayName("endMatchUp 메소드 성공 테스트")
    public void endMatchUp_success_test() {
        League league = leagueRepository.findById(leagueId).get();

        List<Member> participants =
                leagueSearchRepositoryImpl.findMemberInLeague(leagueId, LeagueParticipantsStatus.PARTICIPATING);

        Member host = memberRepository.findById(usn).get();
        participants.add(host);

        assertThat(participants.size())
                .isEqualTo(4);

        matchUpService.createIndividualLeagueMatchUp(league, participants);

        List<MatchUp> matchUpList = matchUpRepository
                .findByFirstMemberOrSecondMemberAndLeague(host, host, league);
        MatchUp matchUp = matchUpList.get(0);

        MatchUpCreateInfo matchUpCreateInfo = new MatchUpCreateInfo();
        LocalDateTime startTime = LocalDateTime.of(2021, 12, 12, 12,0,0);

        matchUpCreateInfo.setLatitude(30.500);
        matchUpCreateInfo.setLongitude(128.123);
        matchUpCreateInfo.setStartTime(startTime);

        Long matchingId = matchUpService.startMatching(usn, matchUp.getId(), matchUpCreateInfo);

        Member winner = matchUp.getFirstMember();
        Member loser = matchUp.getSecondMember();

        matchUpService.approveMatchUp(winner.getId(),matchUp.getId(), matchingId);

        matchUpService.endMathUp(winner.getId(),
                loser.getId(), matchUp.getId());

        Matching matching = matchingRepository.findById(matchingId).get();
        MatchUp recentMatchUp = matchUpRepository.findById(matchUp.getId()).get();

        MatchUpRecord record = matchUpRecordRepository.findByMatchUp(matchUp).get();

        assertThat(matching).hasFieldOrPropertyWithValue("status", MatchingStatus.END);
        assertThat(recentMatchUp).hasFieldOrPropertyWithValue("status", MatchUpStatus.END);
        assertThat(record)
                .hasFieldOrPropertyWithValue("type", RecordType.MATCH_UP)
                .hasFieldOrPropertyWithValue("matchUp", matchUp)
                .hasFieldOrPropertyWithValue("winner", winner)
                .hasFieldOrPropertyWithValue("loser", loser);
    }
}