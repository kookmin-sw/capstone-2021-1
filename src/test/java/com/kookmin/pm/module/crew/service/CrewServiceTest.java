package com.kookmin.pm.module.crew.service;

import com.kookmin.pm.module.category.domain.Category;
import com.kookmin.pm.module.category.repository.CategoryRepository;
import com.kookmin.pm.module.crew.domain.Crew;
import com.kookmin.pm.module.crew.domain.CrewParticipantStatus;
import com.kookmin.pm.module.crew.domain.CrewParticipants;
import com.kookmin.pm.module.crew.dto.*;
import com.kookmin.pm.module.crew.repository.CrewParticipantsRepository;
import com.kookmin.pm.module.crew.repository.CrewRepository;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("local")
@SpringBootTest
@Transactional
class CrewServiceTest {
    @Autowired
    private CrewService crewService;
    @Autowired
    private MemberService memberService;
    @Autowired
    private CrewRepository crewRepository;
    @Autowired
    private CrewParticipantsRepository crewParticipantsRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private MemberRepository memberRepository;

    @BeforeEach
    public void setUp() {
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

        CrewCreateInfo crewCreateInfo = new CrewCreateInfo();
        crewCreateInfo.setName("초기 크루");
        crewCreateInfo.setDescription("크루 설명1");
        crewCreateInfo.setActivityArea("서울");
        crewCreateInfo.setCategory("BOARD_GAME");
        crewCreateInfo.setMaxCount(5);

        crewService.establishCrew(usn,crewCreateInfo);
    }

    @Test
    @DisplayName("establishGuild 성공 테스트")
    public void establishGuild_success_test() {
        CrewCreateInfo crewCreateInfo = new CrewCreateInfo();
        crewCreateInfo.setName("크루1");
        crewCreateInfo.setDescription("크루 설명1");
        crewCreateInfo.setActivityArea("서울");
        crewCreateInfo.setCategory("BOARD_GAME");
        crewCreateInfo.setMaxCount(5);

        Member member = memberRepository.findByUid("dlwlsrn9412@kookmin.ac.kr").get();

        Long id = crewService.establishCrew(member.getId(), crewCreateInfo);
        Crew crew = crewRepository.findById(id).orElseThrow(EntityNotFoundException::new);

        member = memberRepository.findByUid("dlwlsrn9412@kookmin.ac.kr")
                .orElseThrow(EntityNotFoundException::new);

        assertThat(crew)
                .hasFieldOrPropertyWithValue("name", crewCreateInfo.getName())
                .hasFieldOrPropertyWithValue("description", crewCreateInfo.getDescription())
                .hasFieldOrPropertyWithValue("activityArea", crewCreateInfo.getActivityArea())
                .hasFieldOrPropertyWithValue("maxCount", crewCreateInfo.getMaxCount());

        assertThat(crew.getCategory())
                .hasFieldOrPropertyWithValue("name", crewCreateInfo.getCategory());

        assertThat(crew.getMember())
                .isEqualTo(member);
    }

    @Test
    @DisplayName("editCrewInfo 성공 테스트")
    public void editCrewInfo_success_test() {
        Crew crew = crewRepository.findAll().get(0);

        CrewEditInfo crewEditInfo = new CrewEditInfo();
        crewEditInfo.setName("수정 크루");
        crewEditInfo.setDescription("수정 크루 설명");
        crewEditInfo.setId(crew.getId());

        Member member = memberRepository.findByUid("dlwlsrn9412@kookmin.ac.kr").get();

        crewService.editCrewInfo(member.getId(), crewEditInfo);

        crew = crewRepository.findById(crew.getId()).orElseThrow(EntityNotFoundException::new);

        assertThat(crew)
                .hasFieldOrPropertyWithValue("name", crewEditInfo.getName())
                .hasFieldOrPropertyWithValue("description", crewEditInfo.getDescription());
    }

    @Test
    @DisplayName("lookupCrew 성공 테스트")
    public void lookupCrew_success_test() {
        Member host = memberRepository.findByUid("dlwlsrn9412@kookmin.ac.kr")
                .orElseThrow(EntityNotFoundException::new);

        Crew crew = crewRepository.findByMember(host).get(0);

        CrewDetails crewDetails = crewService.lookupCrew(crew.getId(),CrewLookupType.DEFAULT);

        System.out.println(crewDetails);
    }

    @Test
    @DisplayName("participateCrew 성공 테스트")
    public void participateCrew_success_test() {
        Member participant = memberRepository.findByUid("dlwlsrn10@kookmin.ac.kr")
                .orElseThrow(EntityNotFoundException::new);

        Member host = memberRepository.findByUid("dlwlsrn9412@kookmin.ac.kr")
                .orElseThrow(EntityNotFoundException::new);

        Crew crew = crewRepository.findByMember(host).get(0);

        crewService.participateCrew(participant.getId(), crew.getId());

        CrewParticipants crewParticipants = crewParticipantsRepository.findByMemberAndCrew(participant, crew)
                .orElseThrow(EntityNotFoundException::new);

        assertThat(crewParticipantsRepository.countCrewParticipantsByCrewAndStatus(crew, CrewParticipantStatus.PENDING))
                .isEqualTo(1L);

        assertThat(crewParticipants.getMember())
                .isEqualTo(participant);

        assertThat(crewParticipants.getCrew())
                .isEqualTo(crew);
    }

    @Test
    @DisplayName("approveParticipationRequest 성공 테스트")
    public void approveParticipationRequest_success_test() {
        Member participant = memberRepository.findByUid("dlwlsrn10@kookmin.ac.kr")
                .orElseThrow(EntityNotFoundException::new);

        Member host = memberRepository.findByUid("dlwlsrn9412@kookmin.ac.kr")
                .orElseThrow(EntityNotFoundException::new);

        Crew crew = crewRepository.findByMember(host).get(0);

        crewService.participateCrew(participant.getId(), crew.getId());

        CrewParticipants crewParticipants = crewParticipantsRepository.findByMemberAndCrew(participant, crew)
                .orElseThrow(EntityNotFoundException::new);

        crewService.approveParticipationRequest(host.getId(),crewParticipants.getId());

        assertThat(crewParticipantsRepository.countCrewParticipantsByCrewAndStatus(crew, CrewParticipantStatus.PARTICIPATING))
                .isEqualTo(1L);
    }

    @Test
    @DisplayName("rejectParticipationRequest 성공 테스트")
    public void rejectParticipationRequest_success_test() {
        Member participant = memberRepository.findByUid("dlwlsrn10@kookmin.ac.kr")
                .orElseThrow(EntityNotFoundException::new);

        Member host = memberRepository.findByUid("dlwlsrn9412@kookmin.ac.kr")
                .orElseThrow(EntityNotFoundException::new);

        Crew crew = crewRepository.findByMember(host).get(0);

        crewService.participateCrew(participant.getId(), crew.getId());

        CrewParticipants crewParticipants = crewParticipantsRepository.findByMemberAndCrew(participant, crew)
                .orElseThrow(EntityNotFoundException::new);

        crewService.rejectParticipationRequest(host.getId(),crewParticipants.getId());

        assertThat(crewParticipantsRepository.countCrewParticipantsByCrewAndStatus(crew, CrewParticipantStatus.PENDING))
                .isEqualTo(0L);
    }

    @Test
    @DisplayName("cancelParticipation 성공 테스트")
    public void cancelParticipation_success_test() {
        Member participant = memberRepository.findByUid("dlwlsrn10@kookmin.ac.kr")
                .orElseThrow(EntityNotFoundException::new);

        Member host = memberRepository.findByUid("dlwlsrn9412@kookmin.ac.kr")
                .orElseThrow(EntityNotFoundException::new);

        Crew crew = crewRepository.findByMember(host).get(0);

        crewService.participateCrew(participant.getId(), crew.getId());
        crewService.leaveCrew(participant.getId(), crew.getId());

        assertThat(crewParticipantsRepository.countCrewParticipantsByCrewAndStatus(crew, CrewParticipantStatus.PENDING))
                .isEqualTo(0L);
    }

    @Test
    @DisplayName("deportParticipant 성공 테스트")
    public void deportParticipant_success_test() {
        Member participant = memberRepository.findByUid("dlwlsrn10@kookmin.ac.kr")
                .orElseThrow(EntityNotFoundException::new);

        Member host = memberRepository.findByUid("dlwlsrn9412@kookmin.ac.kr")
                .orElseThrow(EntityNotFoundException::new);

        Crew crew = crewRepository.findByMember(host).get(0);

        crewService.participateCrew(participant.getId(), crew.getId());

        CrewParticipants crewParticipants = crewParticipantsRepository.findByMemberAndCrew(participant, crew)
                .orElseThrow(EntityNotFoundException::new);

        crewService.approveParticipationRequest(host.getId(), crewParticipants.getId());
        crewService.deportParticipant(host.getId(), crew.getId(), participant.getId());

        assertThat(crewParticipantsRepository.countCrewParticipantsByCrewAndStatus(crew, CrewParticipantStatus.PENDING))
                .isEqualTo(0L);

        assertThat(crewParticipantsRepository.countCrewParticipantsByCrewAndStatus(crew, CrewParticipantStatus.PARTICIPATING))
                .isEqualTo(0L);
    }

    @Test
    @DisplayName("removeCrew 성공 테스트")
    public void removeCrew_success_test() {
        Member participant = memberRepository.findByUid("dlwlsrn10@kookmin.ac.kr")
                .orElseThrow(EntityNotFoundException::new);

        Member participant2 = memberRepository.findByUid("dlwlsrn7@kookmin.ac.kr")
                .orElseThrow(EntityNotFoundException::new);

        Member host = memberRepository.findByUid("dlwlsrn9412@kookmin.ac.kr")
                .orElseThrow(EntityNotFoundException::new);

        Crew crew = crewRepository.findByMember(host).get(0);

        crewService.participateCrew(participant.getId(), crew.getId());
        crewService.participateCrew(participant2.getId(), crew.getId());


        CrewParticipants crewParticipants = crewParticipantsRepository.findByMemberAndCrew(participant, crew)
                .orElseThrow(EntityNotFoundException::new);

        CrewParticipants crewParticipants2 = crewParticipantsRepository.findByMemberAndCrew(participant2, crew)
                .orElseThrow(EntityNotFoundException::new);

        crewService.approveParticipationRequest(host.getId(), crewParticipants.getId());
        crewService.approveParticipationRequest(host.getId(), crewParticipants2.getId());

        crewService.removeCrew(host.getId(), crew.getId());

        assertThat(crewRepository.findById(crew.getId()).orElse(null))
                .isNull();

        assertThat(crewParticipantsRepository.countCrewParticipantsByCrewAndStatus(crew, CrewParticipantStatus.PARTICIPATING))
                .isEqualTo(0L);
    }

    @Test
    @DisplayName("searchCrew 성공 테스트")
    public void searchCrew_success_test() {
        Pageable pageable = PageRequest.of(0, 10);
        CrewSearchCondition searchCondition = new CrewSearchCondition();
        searchCondition.setActivityArea("서울");

        Page<CrewDetails> crewDetailsList = crewService.searchCrew(pageable, searchCondition);

        for(CrewDetails crewDetails : crewDetailsList)
            System.out.println(crewDetails);
    }

    @Test
    @DisplayName("findCrewParticipateRequest")
    public void findCrewParticipateRequest_success_test() {
        Member participant = memberRepository.findByUid("dlwlsrn10@kookmin.ac.kr")
                .orElseThrow(EntityNotFoundException::new);

        Member host = memberRepository.findByUid("dlwlsrn9412@kookmin.ac.kr")
                .orElseThrow(EntityNotFoundException::new);

        Crew crew = crewRepository.findByMember(host).get(0);

        crewService.participateCrew(participant.getId(), crew.getId());

        List<CrewParticipantsDetails> request = crewService.findCrewParticipateRequest(host.getId());

        System.out.println(request);
    }

    @Test
    @DisplayName("findMyParticipateRequest")
    public void findMyParticipateRequest_success_test() {
        Member participant = memberRepository.findByUid("dlwlsrn10@kookmin.ac.kr")
                .orElseThrow(EntityNotFoundException::new);

        Member host = memberRepository.findByUid("dlwlsrn9412@kookmin.ac.kr")
                .orElseThrow(EntityNotFoundException::new);

        Crew crew = crewRepository.findByMember(host).get(0);

        crewService.participateCrew(participant.getId(), crew.getId());

        List<CrewParticipantsDetails> result = crewService.findMyParticiPateRequest(participant.getId());

        for(CrewParticipantsDetails detail : result)
            System.out.println(detail);
    }
}