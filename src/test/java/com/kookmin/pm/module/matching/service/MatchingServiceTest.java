package com.kookmin.pm.module.matching.service;

import com.kookmin.pm.module.category.domain.Category;
import com.kookmin.pm.module.category.repository.CategoryRepository;
import com.kookmin.pm.module.matching.domain.Matching;
import com.kookmin.pm.module.matching.domain.MatchingParticipant;
import com.kookmin.pm.module.matching.domain.MatchingStatus;
import com.kookmin.pm.module.matching.domain.ParticipantStatus;
import com.kookmin.pm.module.matching.dto.MatchingCreateInfo;
import com.kookmin.pm.module.matching.dto.MatchingDetails;
import com.kookmin.pm.module.matching.dto.MatchingEditInfo;
import com.kookmin.pm.module.matching.dto.MatchingSearchCondition;
import com.kookmin.pm.module.matching.repository.MatchingMapper;
import com.kookmin.pm.module.matching.repository.MatchingParticipantRepository;
import com.kookmin.pm.module.matching.repository.MatchingRepository;
import com.kookmin.pm.module.member.domain.Member;
import com.kookmin.pm.module.member.dto.MemberCreateInfo;
import com.kookmin.pm.module.member.repository.MemberRepository;
import com.kookmin.pm.module.member.service.MemberService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Transactional
class MatchingServiceTest {
    @Autowired
    private MatchingService matchingService;
    @Autowired
    private MemberService memberService;
    @Autowired
    private MatchingRepository matchingRepository;
    @Autowired
    private MatchingParticipantRepository matchingParticipantRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private MatchingMapper matchingMapper;
    @Autowired
    private EntityManager entityManager;
    @Autowired
    private CategoryRepository categoryRepository;

    @BeforeEach
    public void setup() {
        MemberCreateInfo memberCreateInfo = new MemberCreateInfo();
        memberCreateInfo.setUid("dlwlsrn9412@kookmin.ac.kr");
        memberCreateInfo.setPassword("1234");
        memberCreateInfo.setNickname("jingu2");
        memberCreateInfo.setAddress("서울시~~~");
        memberCreateInfo.setName("이진구");
        memberCreateInfo.setPhoneNumber("010-8784-3827");

        memberService.joinMember(memberCreateInfo);

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

        MatchingCreateInfo matchingCreateInfo = new MatchingCreateInfo();
        matchingCreateInfo.setTitle("title");
        matchingCreateInfo.setDescription("desc");
        matchingCreateInfo.setLatitude(38.05);
        matchingCreateInfo.setLongitude(120.05);
        matchingCreateInfo.setStartTime(LocalDateTime.of(2021, 12, 12, 12,0,0));
        matchingCreateInfo.setMaxCount(5);
        matchingCreateInfo.setCategory("BOARD_GAME");

        matchingService.openMatching("dlwlsrn9412@kookmin.ac.kr", matchingCreateInfo);

        MatchingCreateInfo matchingCreateInfo2 = new MatchingCreateInfo();
        matchingCreateInfo2.setTitle("title");
        matchingCreateInfo2.setDescription("desc");
        matchingCreateInfo2.setLatitude(38.05);
        matchingCreateInfo2.setLongitude(120.05);
        matchingCreateInfo2.setStartTime(LocalDateTime.of(2021, 11, 12, 12,0,0));
        matchingCreateInfo2.setMaxCount(5);
        matchingCreateInfo2.setCategory("BOARD_GAME");

        matchingService.openMatching("dlwlsrn9412@kookmin.ac.kr", matchingCreateInfo2);
    }

    @Test
    @DisplayName("openMatching 성공 테스트")
    public void openMatching_success_test() {
        Member member = memberRepository.findByUid("dlwlsrn10@kookmin.ac.kr")
                .orElseThrow(EntityNotFoundException::new);

        MatchingCreateInfo matchingCreateInfo = new MatchingCreateInfo();
        matchingCreateInfo.setTitle("test_title");
        matchingCreateInfo.setDescription("test_desc");
        matchingCreateInfo.setStartTime(LocalDateTime.of(2021, 12, 12, 12,0,0));
        matchingCreateInfo.setLatitude(38.05);
        matchingCreateInfo.setLongitude(120.05);
        matchingCreateInfo.setMaxCount(5);
        matchingCreateInfo.setCategory("BOARD_GAME");

        Long id = matchingService.openMatching(member.getUid(), matchingCreateInfo);
        Matching matching = matchingRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        Category category = categoryRepository.findByName("BOARD_GAME").orElseThrow(EntityNotFoundException::new);

        assertThat(matching)
                .hasFieldOrPropertyWithValue("title", matchingCreateInfo.getTitle())
                .hasFieldOrPropertyWithValue("description", matchingCreateInfo.getDescription())
                .hasFieldOrPropertyWithValue("latitude", matchingCreateInfo.getLatitude())
                .hasFieldOrPropertyWithValue("longitude", matchingCreateInfo.getLongitude())
                .hasFieldOrPropertyWithValue("maxCount", matchingCreateInfo.getMaxCount())
                .hasFieldOrPropertyWithValue("status", MatchingStatus.SCHEDULED)
                .hasFieldOrPropertyWithValue("category", category);
    }

    @Test
    @DisplayName("participateMatching 성공 테스트")
    public void participateMatching_success_test() {
        Member creater = memberRepository.findByUid("dlwlsrn9412@kookmin.ac.kr")
                .orElseThrow(EntityNotFoundException::new);

        Member participant = memberRepository.findByUid("dlwlsrn10@kookmin.ac.kr")
                .orElseThrow(EntityNotFoundException::new);

        Matching matching = matchingRepository.findByMember(creater).get(0);

        Long id = matchingService.participateMatching(participant.getUid(), matching.getId());

        MatchingParticipant matchingParticipant = matchingParticipantRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);

        assertThat(matchingParticipant)
                .hasFieldOrPropertyWithValue("member", participant)
                .hasFieldOrPropertyWithValue("matching", matching);
    }

    @Test
    @DisplayName("participateMathcing 실패 테스트 - 매칭 생성자가 매칭 참가 요청시")
    public void participantMatching_fail_test() {
        Member creater = memberRepository.findByUid("dlwlsrn9412@kookmin.ac.kr")
                .orElseThrow(EntityNotFoundException::new);

        Matching matching = matchingRepository.findByMember(creater).get(0);

        assertThatThrownBy(() -> {
            Long id = matchingService.participateMatching(creater.getUid(), matching.getId());
        }).isInstanceOf(RuntimeException.class);
    }

    @Test
    @DisplayName("matching관련 조인 테스트")
    public void matching_participants_join_test() {
        Member creater = memberRepository.findByUid("dlwlsrn9412@kookmin.ac.kr")
                .orElseThrow(EntityNotFoundException::new);

        Member participant = memberRepository.findByUid("dlwlsrn10@kookmin.ac.kr")
                .orElseThrow(EntityNotFoundException::new);

        Member participant2 = memberRepository.findByUid("dlwlsrn7@kookmin.ac.kr")
                .orElseThrow(EntityNotFoundException::new);

        Member participant3 = memberRepository.findByUid("dlwlsrn6@kookmin.ac.kr")
                .orElseThrow(EntityNotFoundException::new);

        Matching matching = matchingRepository.findByMember(creater).get(0);

        matchingService.participateMatching(participant.getUid(), matching.getId());
        matchingService.participateMatching(participant2.getUid(), matching.getId());
        matchingService.participateMatching(participant3.getUid(), matching.getId());

        List<Member> members = matchingRepository.searchMemberInMatchingParticipant(matching.getId(),
                ParticipantStatus.PENDING_ACCEPTANCE);
        System.out.println(members.size());


        for(Member member : members)
            System.out.println(member);
    }

    @Test
    @DisplayName("lookupMatching 성공 테스트")
    public void lookupMatching_success_test() {
        Member creater = memberRepository.findByUid("dlwlsrn9412@kookmin.ac.kr")
                .orElseThrow(EntityNotFoundException::new);

        Member participant = memberRepository.findByUid("dlwlsrn10@kookmin.ac.kr")
                .orElseThrow(EntityNotFoundException::new);

        Member participant2 = memberRepository.findByUid("dlwlsrn7@kookmin.ac.kr")
                .orElseThrow(EntityNotFoundException::new);

        Member participant3 = memberRepository.findByUid("dlwlsrn6@kookmin.ac.kr")
                .orElseThrow(EntityNotFoundException::new);

        Matching matching = matchingRepository.findByMember(creater).get(0);

        matchingService.participateMatching(participant.getUid(), matching.getId());
        matchingService.participateMatching(participant2.getUid(), matching.getId());
        matchingService.participateMatching(participant3.getUid(), matching.getId());

        MatchingDetails matchingDetails = matchingService.lookupMatching(matching.getId(),
                MatchingLookUpType.DEFAULT);

        System.out.println("DEFAULT: " + matchingDetails);

        MatchingDetails matchingDetails2 = matchingService.lookupMatching(matching.getId(),
                MatchingLookUpType.WITH_HOST);

        System.out.println("WITH HOST: " + matchingDetails2);

        MatchingDetails matchingDetails3 = matchingService.lookupMatching(matching.getId(),
                MatchingLookUpType.WITH_PARTICIPANTS);

        System.out.println("WITH PARTICIPANTS: " + matchingDetails3);
    }

    @Test
    @DisplayName("editMatching 메소드 성공 테스트")
    public void editMatching_success_test() {
        Member creater = memberRepository.findByUid("dlwlsrn9412@kookmin.ac.kr")
                .orElseThrow(EntityNotFoundException::new);

        Matching matching = matchingRepository.findByMember(creater).get(0);

        MatchingEditInfo matchingEditInfo = new MatchingEditInfo();
        matchingEditInfo.setId(matching.getId());
        matchingEditInfo.setTitle("수정 제목");
        matchingEditInfo.setDescription("수정 설명");
        matchingEditInfo.setCategory("ROOM_ESCAPE");
        matchingEditInfo.setLatitude(50.0);
        matchingEditInfo.setLongitude(50.0);
        matchingEditInfo.setMaxCount(5);
        matchingEditInfo.setStartTime(LocalDateTime.of(2021, 3, 12, 12,0,0));

       matchingService.editMatching(creater.getUid(), matchingEditInfo);

       matching = matchingRepository.findByMember(creater).get(0);
        Category category = categoryRepository.findByName("ROOM_ESCAPE").orElseThrow(EntityNotFoundException::new);

       assertThat(matching)
               .hasFieldOrPropertyWithValue("title", matchingEditInfo.getTitle());

       assertThat(matching.getCategory())
               .hasFieldOrPropertyWithValue("name", "ROOM_ESCAPE");
    }

    @Test
    @DisplayName("quitMatching 성공 테스트")
    public void quitMatching_success_test() {
        Member creater = memberRepository.findByUid("dlwlsrn9412@kookmin.ac.kr")
                .orElseThrow(EntityNotFoundException::new);

        Member participant = memberRepository.findByUid("dlwlsrn10@kookmin.ac.kr")
                .orElseThrow(EntityNotFoundException::new);

        Matching matching = matchingRepository.findByMember(creater).get(0);

        Long id = matchingService.participateMatching(participant.getUid(), matching.getId());

        matchingService.quitMatching(creater.getUid(), matching.getId());

        List<MatchingParticipant> result = matchingParticipantRepository.findByMatching(matching);

        assertThat(result.size())
                .isEqualTo(0);
    }

    @Test
    @DisplayName("cancelParticipation 성공 테스트")
    public void cancelParticipation_success_test() {
        Member host = memberRepository.findByUid("dlwlsrn9412@kookmin.ac.kr")
                .orElseThrow(EntityNotFoundException::new);

        Member participant = memberRepository.findByUid("dlwlsrn10@kookmin.ac.kr")
                .orElseThrow(EntityNotFoundException::new);

        Matching matching = matchingRepository.findByMember(host).get(0);

        Long id = matchingService.participateMatching(participant.getUid(), matching.getId());
        matchingService.approveParticipationRequest(host.getUid(), id);

        matchingService.cancelParticipation(participant.getUid(), matching.getId());

        List<MatchingParticipant> participants = matchingParticipantRepository.findByMatching(matching);

        assertThat(participants.size())
                .isEqualTo(0);
    }

    @Test
    @DisplayName("searchMatching 성공 테스트")
    public void searchMatching_success_test() {
        MatchingSearchCondition searchCondition = new MatchingSearchCondition();
        searchCondition.setHost("dlwlsrn9412@kookmin.ac.kr");
        searchCondition.setCategory("BOARD_GAME");
        Pageable pageable = PageRequest.of(0, 10);

        Page<MatchingDetails> matchingDetails = matchingService.searchMatching(pageable, searchCondition);

        assertThat(matchingDetails.getContent().size())
                .isEqualTo(2);

        for(MatchingDetails matching : matchingDetails.getContent())
            System.out.println(matching);
    }

    @Test
    @DisplayName("MatchingMapper 성공 테스트")
    public void matchingMapper_test() {
        entityManager.flush();
        entityManager.clear();

        MatchingSearchCondition searchCondition = new MatchingSearchCondition();

        searchCondition.setLatitude(30.0);
        searchCondition.setLongitude(120.0);
        searchCondition.setDistance(1000.0);

        Map<String, Object> map = new HashMap<>();
        map.put("latitude", searchCondition.getLatitude());
        map.put("longitude", searchCondition.getLongitude());
        map.put("distance", searchCondition.getDistance());

        List<MatchingDetails> matchingDetailsList = matchingMapper.searchMatchingWithLocationInfo(map);

        for(MatchingDetails details : matchingDetailsList)
            System.out.println(details);
    }

    @Test
    @DisplayName("approveParticipationRequest 성공 테스트")
    public void approveParticipationRequest_success_test() {
        Member host = memberRepository.findByUid("dlwlsrn9412@kookmin.ac.kr")
                .orElseThrow(EntityNotFoundException::new);

        Member participant = memberRepository.findByUid("dlwlsrn10@kookmin.ac.kr")
                .orElseThrow(EntityNotFoundException::new);

        Matching matching = matchingRepository.findByMember(host).get(0);

        Long id = matchingService.participateMatching(participant.getUid(), matching.getId());
        matchingService.approveParticipationRequest(host.getUid(), id);

        MatchingParticipant matchingParticipant = matchingParticipantRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);

        assertThat(matchingParticipant)
                .hasFieldOrPropertyWithValue("status", ParticipantStatus.PARTICIPATING);
    }

    @Test
    @DisplayName("rejectParticipationRequest 성공 테스트")
    public void rejectParticipationRequest_success_test() {
        Member host = memberRepository.findByUid("dlwlsrn9412@kookmin.ac.kr")
                .orElseThrow(EntityNotFoundException::new);

        Member participant = memberRepository.findByUid("dlwlsrn10@kookmin.ac.kr")
                .orElseThrow(EntityNotFoundException::new);

        Matching matching = matchingRepository.findByMember(host).get(0);

        Long id = matchingService.participateMatching(participant.getUid(), matching.getId());
        matchingService.rejectParticipationRequest(host.getUid(), id);

        boolean result = matchingParticipantRepository.findByMemberAndMatching(participant, matching).isPresent();

        assertThat(result)
                .isFalse();
    }

    @Test
    @DisplayName("quitParticipationRequest 성공 테스트")
    public void quitParticipationRequest_success_test() {
        Member host = memberRepository.findByUid("dlwlsrn9412@kookmin.ac.kr")
                .orElseThrow(EntityNotFoundException::new);

        Member participant = memberRepository.findByUid("dlwlsrn10@kookmin.ac.kr")
                .orElseThrow(EntityNotFoundException::new);

        Matching matching = matchingRepository.findByMember(host).get(0);

        Long id = matchingService.participateMatching(participant.getUid(), matching.getId());

        matchingService.quitParticipationRequest(participant.getUid(), id);

        MatchingParticipant matchingParticipant = matchingParticipantRepository.findById(id)
                .orElse(null);

        assertThat(matchingParticipant)
                .isNull();
    }

    @Test
    @DisplayName("startMatching 성공 테스트")
    public void startMatching_success_test() {
        Member host = memberRepository.findByUid("dlwlsrn9412@kookmin.ac.kr")
                .orElseThrow(EntityNotFoundException::new);

        Matching matching = matchingRepository.findByMember(host).get(0);

        matchingService.startMatching(host.getUid(), matching.getId());

        Matching startedMatching = matchingRepository.findById(matching.getId()).orElse(null);

        assertThat(startedMatching)
                .hasFieldOrPropertyWithValue("status", MatchingStatus.PROCEEDING);
    }
}