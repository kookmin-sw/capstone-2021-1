package com.kookmin.pm.module.mathcing.service;

import com.kookmin.pm.module.mathcing.domain.Matching;
import com.kookmin.pm.module.mathcing.domain.MatchingParticipant;
import com.kookmin.pm.module.mathcing.domain.MatchingStatus;
import com.kookmin.pm.module.mathcing.dto.MatchingCreateInfo;
import com.kookmin.pm.module.mathcing.repository.MatchingParticipantRepository;
import com.kookmin.pm.module.mathcing.repository.MatchingRepository;
import com.kookmin.pm.module.member.domain.Member;
import com.kookmin.pm.module.member.dto.MemberCreateInfo;
import com.kookmin.pm.module.member.repository.MemberRepository;
import com.kookmin.pm.module.member.service.MemberService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

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

    @BeforeEach
    public void setup() {
        MemberCreateInfo memberCreateInfo = new MemberCreateInfo();
        memberCreateInfo.setEmail("dlwlsrn9412@kookmin.ac.kr");
        memberCreateInfo.setPassword("1234");
        memberCreateInfo.setNickname("jingu2");
        memberCreateInfo.setAddress("서울시~~~");
        memberCreateInfo.setName("이진구");
        memberCreateInfo.setPhoneNumber("010-8784-3827");

        memberService.joinMember(memberCreateInfo);

        MemberCreateInfo memberCreateInfo2 = new MemberCreateInfo();
        memberCreateInfo2.setEmail("dlwlsrn10@kookmin.ac.kr");
        memberCreateInfo2.setPassword("1234");
        memberCreateInfo2.setNickname("jingu2");
        memberCreateInfo2.setAddress("서울시~~~");
        memberCreateInfo2.setName("이진구");
        memberCreateInfo2.setPhoneNumber("010-8784-3827");

        memberService.joinMember(memberCreateInfo2);

        MatchingCreateInfo matchingCreateInfo = new MatchingCreateInfo();
        matchingCreateInfo.setTitle("title");
        matchingCreateInfo.setDescription("desc");
        matchingCreateInfo.setLatitude(38.05);
        matchingCreateInfo.setLongitude(120.05);
        matchingCreateInfo.setMaxCount(5);

        matchingService.startMatching("dlwlsrn9412@kookmin.ac.kr", matchingCreateInfo);

        MatchingCreateInfo matchingCreateInfo2 = new MatchingCreateInfo();
        matchingCreateInfo2.setTitle("title");
        matchingCreateInfo2.setDescription("desc");
        matchingCreateInfo2.setLatitude(38.05);
        matchingCreateInfo2.setLongitude(120.05);
        matchingCreateInfo2.setMaxCount(5);

        matchingService.startMatching("dlwlsrn9412@kookmin.ac.kr", matchingCreateInfo2);
    }

    @Test
    @DisplayName("startMatching 성공 테스트")
    public void startMatching_success_test() {
        Member member = memberRepository.findByEmail("dlwlsrn10@kookmin.ac.kr")
                .orElseThrow(EntityNotFoundException::new);

        MatchingCreateInfo matchingCreateInfo = new MatchingCreateInfo();
        matchingCreateInfo.setTitle("test_title");
        matchingCreateInfo.setDescription("test_desc");
        matchingCreateInfo.setLatitude(38.05);
        matchingCreateInfo.setLongitude(120.05);
        matchingCreateInfo.setMaxCount(5);

        Long id = matchingService.startMatching(member.getEmail(), matchingCreateInfo);

        Matching matching = matchingRepository.findById(id).orElseThrow(EntityNotFoundException::new);

        assertThat(matching)
                .hasFieldOrPropertyWithValue("title", matchingCreateInfo.getTitle())
                .hasFieldOrPropertyWithValue("description", matchingCreateInfo.getDescription())
                .hasFieldOrPropertyWithValue("latitude", matchingCreateInfo.getLatitude())
                .hasFieldOrPropertyWithValue("longitude", matchingCreateInfo.getLongitude())
                .hasFieldOrPropertyWithValue("maxCount", matchingCreateInfo.getMaxCount())
                .hasFieldOrPropertyWithValue("status", MatchingStatus.SCHEDULED);
    }

    @Test
    @DisplayName("participateMatching 성공 테스트")
    public void participateMatching_success_test() {
        Member creater = memberRepository.findByEmail("dlwlsrn9412@kookmin.ac.kr")
                .orElseThrow(EntityNotFoundException::new);

        Member participant = memberRepository.findByEmail("dlwlsrn10@kookmin.ac.kr")
                .orElseThrow(EntityNotFoundException::new);

        Matching matching = matchingRepository.findByMember(creater).get(0);

        Long id = matchingService.participateMatching(participant.getEmail(), matching.getId());

        MatchingParticipant matchingParticipant = matchingParticipantRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);

        assertThat(matchingParticipant)
                .hasFieldOrPropertyWithValue("member", participant)
                .hasFieldOrPropertyWithValue("matching", matching);
    }

    @Test
    @DisplayName("participateMathcing 실패 테스트 - 매칭 생성자가 매칭 참가 요청시")
    public void participantMatching_fail_test() {
        Member creater = memberRepository.findByEmail("dlwlsrn9412@kookmin.ac.kr")
                .orElseThrow(EntityNotFoundException::new);

        Matching matching = matchingRepository.findByMember(creater).get(0);

        assertThatThrownBy(() -> {
            Long id = matchingService.participateMatching(creater.getEmail(), matching.getId());
        }).isInstanceOf(RuntimeException.class);
    }
}