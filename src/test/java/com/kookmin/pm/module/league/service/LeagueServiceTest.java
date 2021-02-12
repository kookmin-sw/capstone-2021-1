package com.kookmin.pm.module.league.service;

import com.kookmin.pm.module.category.domain.Category;
import com.kookmin.pm.module.category.repository.CategoryRepository;
import com.kookmin.pm.module.league.domain.League;
import com.kookmin.pm.module.league.domain.LeagueParticipants;
import com.kookmin.pm.module.league.domain.LeagueParticipantsStatus;
import com.kookmin.pm.module.league.domain.LeagueType;
import com.kookmin.pm.module.league.dto.LeagueCreateInfo;
import com.kookmin.pm.module.league.dto.LeagueDetails;
import com.kookmin.pm.module.league.dto.LeagueEditInfo;
import com.kookmin.pm.module.league.repository.LeagueParticipantsRepository;
import com.kookmin.pm.module.league.repository.LeagueRepository;
import com.kookmin.pm.module.member.domain.Member;
import com.kookmin.pm.module.member.dto.MemberCreateInfo;
import com.kookmin.pm.module.member.repository.MemberRepository;
import com.kookmin.pm.module.member.service.MemberService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("local")
@SpringBootTest
@Transactional
class LeagueServiceTest {
    @Autowired
    private LeagueService leagueService;
    @Autowired
    private MemberService memberService;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private LeagueRepository leagueRepository;
    @Autowired
    private LeagueParticipantsRepository leagueParticipantsRepository;

    @BeforeEach
    public void setup() {
        MemberCreateInfo memberCreateInfo = new MemberCreateInfo();
        memberCreateInfo.setUid("dlwlsrn9412@kookmin.ac.kr");
        memberCreateInfo.setPassword("1234");
        memberCreateInfo.setNickname("jingu2");
        memberCreateInfo.setAddress("서울시~~~");
        memberCreateInfo.setName("이진구");
        memberCreateInfo.setPhoneNumber("010-8784-3827");

        Long usn = memberService.joinMember(memberCreateInfo);

        MemberCreateInfo memberCreateInfo2 = new MemberCreateInfo();
        memberCreateInfo2.setUid("dlwlsrn10@kookmin.ac.kr");
        memberCreateInfo2.setPassword("1234");
        memberCreateInfo2.setNickname("jingu2");
        memberCreateInfo2.setAddress("서울시~~~");
        memberCreateInfo2.setName("이진팔");
        memberCreateInfo2.setPhoneNumber("010-8784-3827");

        memberService.joinMember(memberCreateInfo2);

        MemberCreateInfo memberCreateInfo3 = new MemberCreateInfo();
        memberCreateInfo3.setUid("dlwlsrn7@kookmin.ac.kr");
        memberCreateInfo3.setPassword("1234");
        memberCreateInfo3.setNickname("jingu3");
        memberCreateInfo3.setAddress("서울시~~~");
        memberCreateInfo3.setName("이진칠");
        memberCreateInfo3.setPhoneNumber("010-8784-3827");

        memberService.joinMember(memberCreateInfo3);

        MemberCreateInfo memberCreateInfo4 = new MemberCreateInfo();
        memberCreateInfo4.setUid("dlwlsrn6@kookmin.ac.kr");
        memberCreateInfo4.setPassword("1234");
        memberCreateInfo4.setNickname("jingu4");
        memberCreateInfo4.setAddress("서울시~~~");
        memberCreateInfo4.setName("이진육");
        memberCreateInfo4.setPhoneNumber("010-8784-3827");

        memberService.joinMember(memberCreateInfo4);

        Category category = new Category("BOARD_GAME");
        categoryRepository.save(category);

        Category category2 = new Category("ROOM_ESCAPE");
        categoryRepository.save(category2);
    }

    @Test
    @DisplayName("openLeague 메소드 성공 테스트")
    public void openLeague_success_test() {
        Member host = memberRepository.findByUid("dlwlsrn9412@kookmin.ac.kr").get();

        LocalDateTime startTime = LocalDateTime.of(2021, 11, 20, 12, 0, 0);

        LeagueCreateInfo leagueCreateInfo = new LeagueCreateInfo();
        leagueCreateInfo.setActivityArea("서울");
        leagueCreateInfo.setCategory("BOARD_GAME");
        leagueCreateInfo.setDescription("리그 소개");
        leagueCreateInfo.setLeagueType("LEAGUE");
        leagueCreateInfo.setMaxCount(30);
        leagueCreateInfo.setParticipantType("INDIVIDUAL");
        leagueCreateInfo.setStartTime(startTime);
        leagueCreateInfo.setTitle("서울 체스 리그");

        Long id = leagueService.openLeague(host.getId(), leagueCreateInfo);

        League league = leagueRepository.findById(id).get();

        Category category = categoryRepository.findByName(leagueCreateInfo.getCategory()).get();

       assertThat(league)
               .hasFieldOrPropertyWithValue("title", leagueCreateInfo.getTitle())
               .hasFieldOrPropertyWithValue("description", leagueCreateInfo.getDescription())
               .hasFieldOrPropertyWithValue("type", LeagueType.valueOf(leagueCreateInfo.getLeagueType()))
               .hasFieldOrPropertyWithValue("maxCount", leagueCreateInfo.getMaxCount())
               .hasFieldOrPropertyWithValue("category", category)
               .hasFieldOrPropertyWithValue("member", host)
                .hasFieldOrPropertyWithValue("startTime", startTime);
    }

    @Test
    @DisplayName("editLeague 메소드 성공 테스트")
    public void editLeague_success_test() {
        Member host = memberRepository.findByUid("dlwlsrn9412@kookmin.ac.kr").get();

        LocalDateTime startTime = LocalDateTime.of(2021, 11, 20, 12, 0, 0);

        LeagueCreateInfo leagueCreateInfo = new LeagueCreateInfo();
        leagueCreateInfo.setActivityArea("서울");
        leagueCreateInfo.setCategory("BOARD_GAME");
        leagueCreateInfo.setDescription("리그 소개");
        leagueCreateInfo.setLeagueType("LEAGUE");
        leagueCreateInfo.setMaxCount(30);
        leagueCreateInfo.setParticipantType("INDIVIDUAL");
        leagueCreateInfo.setStartTime(startTime);
        leagueCreateInfo.setTitle("서울 체스 리그");

        Long id = leagueService.openLeague(host.getId(), leagueCreateInfo);

        League league = leagueRepository.findById(id).get();

        LeagueEditInfo leagueEditInfo = new LeagueEditInfo();
        leagueEditInfo.setActivityArea("경기");
        leagueEditInfo.setCategory("ROOM_ESCAPE");
        leagueEditInfo.setDescription("리그 소개 수정");
        leagueEditInfo.setId(league.getId());
        leagueEditInfo.setMaxCount(10);
        leagueEditInfo.setStartTime(startTime);

        leagueService.editLeague(host.getId(), leagueEditInfo);

        league = leagueRepository.findById(id).get();
        Category category = categoryRepository.findByName(leagueEditInfo.getCategory()).get();

        assertThat(league)
                .hasFieldOrPropertyWithValue("description", leagueEditInfo.getDescription())
                .hasFieldOrPropertyWithValue("category", category);
    }

    @Test
    @DisplayName("deleteLeague 메소드 성공 테스트")
    public void deleteLeague_success_test() {
        Member host = memberRepository.findByUid("dlwlsrn9412@kookmin.ac.kr").get();

        LocalDateTime startTime = LocalDateTime.of(2021, 11, 20, 12, 0, 0);

        LeagueCreateInfo leagueCreateInfo = new LeagueCreateInfo();
        leagueCreateInfo.setActivityArea("서울");
        leagueCreateInfo.setCategory("BOARD_GAME");
        leagueCreateInfo.setDescription("리그 소개");
        leagueCreateInfo.setLeagueType("LEAGUE");
        leagueCreateInfo.setMaxCount(30);
        leagueCreateInfo.setParticipantType("INDIVIDUAL");
        leagueCreateInfo.setStartTime(startTime);
        leagueCreateInfo.setTitle("서울 체스 리그");

        Long id = leagueService.openLeague(host.getId(), leagueCreateInfo);

        leagueService.deleteLeague(host.getId(), id);

        League league = leagueRepository.findById(id).orElse(null);

        assertThat(league).isNull();
    }

    @Test
    @DisplayName("participateLeague 메소드 성공 테스트")
    public void participateLeague_success_test() {
        Member host = memberRepository.findByUid("dlwlsrn9412@kookmin.ac.kr").get();
        Member participant = memberRepository.findByUid("dlwlsrn10@kookmin.ac.kr").get();

        LocalDateTime startTime = LocalDateTime.of(2021, 11, 20, 12, 0, 0);

        LeagueCreateInfo leagueCreateInfo = new LeagueCreateInfo();
        leagueCreateInfo.setActivityArea("서울");
        leagueCreateInfo.setCategory("BOARD_GAME");
        leagueCreateInfo.setDescription("리그 소개");
        leagueCreateInfo.setLeagueType("LEAGUE");
        leagueCreateInfo.setMaxCount(30);
        leagueCreateInfo.setParticipantType("INDIVIDUAL");
        leagueCreateInfo.setStartTime(startTime);
        leagueCreateInfo.setTitle("서울 체스 리그");

        Long id = leagueService.openLeague(host.getId(), leagueCreateInfo);

        Long requestId = leagueService.participateLeague(participant.getId(), id);

        LeagueParticipants request = leagueParticipantsRepository.findById(requestId).get();
        League league = leagueRepository.findById(id).get();

        assertThat(request)
                .hasFieldOrPropertyWithValue("member", participant)
                .hasFieldOrPropertyWithValue("league", league);
    }

    @Test
    @DisplayName("approveParticipationRequest 메소드 성공 테스트")
    public void approveParticipationRequest_success_test() {
        Member host = memberRepository.findByUid("dlwlsrn9412@kookmin.ac.kr").get();
        Member participant = memberRepository.findByUid("dlwlsrn10@kookmin.ac.kr").get();

        LocalDateTime startTime = LocalDateTime.of(2021, 11, 20, 12, 0, 0);

        LeagueCreateInfo leagueCreateInfo = new LeagueCreateInfo();
        leagueCreateInfo.setActivityArea("서울");
        leagueCreateInfo.setCategory("BOARD_GAME");
        leagueCreateInfo.setDescription("리그 소개");
        leagueCreateInfo.setLeagueType("LEAGUE");
        leagueCreateInfo.setMaxCount(30);
        leagueCreateInfo.setParticipantType("INDIVIDUAL");
        leagueCreateInfo.setStartTime(startTime);
        leagueCreateInfo.setTitle("서울 체스 리그");

        Long id = leagueService.openLeague(host.getId(), leagueCreateInfo);

        Long requestId = leagueService.participateLeague(participant.getId(), id);

        leagueService.approveParticipationRequest(host.getId(), requestId);

        LeagueParticipants request = leagueParticipantsRepository.findById(requestId).get();

        assertThat(request)
                .hasFieldOrPropertyWithValue("status", LeagueParticipantsStatus.PARTICIPATING);
    }

    @Test
    @DisplayName("rejectParticipationRequest 메소드 성공 테스트")
    public void rejectParticipationRequest_success_test() {
        Member host = memberRepository.findByUid("dlwlsrn9412@kookmin.ac.kr").get();
        Member participant = memberRepository.findByUid("dlwlsrn10@kookmin.ac.kr").get();

        LocalDateTime startTime = LocalDateTime.of(2021, 11, 20, 12, 0, 0);

        LeagueCreateInfo leagueCreateInfo = new LeagueCreateInfo();
        leagueCreateInfo.setActivityArea("서울");
        leagueCreateInfo.setCategory("BOARD_GAME");
        leagueCreateInfo.setDescription("리그 소개");
        leagueCreateInfo.setLeagueType("LEAGUE");
        leagueCreateInfo.setMaxCount(30);
        leagueCreateInfo.setParticipantType("INDIVIDUAL");
        leagueCreateInfo.setStartTime(startTime);
        leagueCreateInfo.setTitle("서울 체스 리그");

        Long id = leagueService.openLeague(host.getId(), leagueCreateInfo);

        Long requestId = leagueService.participateLeague(participant.getId(), id);

        leagueService.rejectParticipationRequest(host.getId(), requestId);

        LeagueParticipants request = leagueParticipantsRepository.findById(requestId).orElse(null);

        assertThat(request).isNull();
    }

    @Test
    @DisplayName("quitParticipationRequest 메소드 성공 테스트")
    public void quitParticipationRequest_success_test() {
        Member host = memberRepository.findByUid("dlwlsrn9412@kookmin.ac.kr").get();
        Member participant = memberRepository.findByUid("dlwlsrn10@kookmin.ac.kr").get();

        LocalDateTime startTime = LocalDateTime.of(2021, 11, 20, 12, 0, 0);

        LeagueCreateInfo leagueCreateInfo = new LeagueCreateInfo();
        leagueCreateInfo.setActivityArea("서울");
        leagueCreateInfo.setCategory("BOARD_GAME");
        leagueCreateInfo.setDescription("리그 소개");
        leagueCreateInfo.setLeagueType("LEAGUE");
        leagueCreateInfo.setMaxCount(30);
        leagueCreateInfo.setParticipantType("INDIVIDUAL");
        leagueCreateInfo.setStartTime(startTime);
        leagueCreateInfo.setTitle("서울 체스 리그");

        Long id = leagueService.openLeague(host.getId(), leagueCreateInfo);

        Long requestId = leagueService.participateLeague(participant.getId(), id);

        leagueService.quitParticipationRequest(participant.getId(), requestId);

        LeagueParticipants request = leagueParticipantsRepository.findById(requestId).orElse(null);

        assertThat(request).isNull();
    }

    @Test
    @DisplayName("lookupLeague 메소드 성공 테스트")
    public void lookupLeague_success_test() {
        Member host = memberRepository.findByUid("dlwlsrn9412@kookmin.ac.kr").get();
        Member participant = memberRepository.findByUid("dlwlsrn10@kookmin.ac.kr").get();

        LocalDateTime startTime = LocalDateTime.of(2021, 11, 20, 12, 0, 0);

        LeagueCreateInfo leagueCreateInfo = new LeagueCreateInfo();
        leagueCreateInfo.setActivityArea("서울");
        leagueCreateInfo.setCategory("BOARD_GAME");
        leagueCreateInfo.setDescription("리그 소개");
        leagueCreateInfo.setLeagueType("LEAGUE");
        leagueCreateInfo.setMaxCount(30);
        leagueCreateInfo.setParticipantType("INDIVIDUAL");
        leagueCreateInfo.setStartTime(startTime);
        leagueCreateInfo.setTitle("서울 체스 리그");

        Long id = leagueService.openLeague(host.getId(), leagueCreateInfo);

        LeagueDetails defaultInfo = leagueService.lookupLeague(id, LeagueLookupType.DEFAULT);
        LeagueDetails details = leagueService.lookupLeague(id, LeagueLookupType.WITH_PARTICIPANTS);

        System.out.println(defaultInfo);
        System.out.println(details);
    }
}