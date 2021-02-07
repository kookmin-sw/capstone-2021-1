package com.kookmin.pm.module.mathcing.service;

import com.kookmin.pm.module.mathcing.dto.MatchingCreateInfo;
import com.kookmin.pm.module.mathcing.repository.MatchingParticipantRepository;
import com.kookmin.pm.module.mathcing.repository.MatchingRepository;
import com.kookmin.pm.module.member.dto.MemberCreateInfo;
import com.kookmin.pm.module.member.repository.MemberRepository;
import com.kookmin.pm.module.member.service.MemberService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

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

        MatchingCreateInfo matchingCreateInfo = new MatchingCreateInfo();
        matchingCreateInfo.setTitle("title");
        matchingCreateInfo.setDescription("desc");
        matchingCreateInfo.setStartTime(LocalDateTime.now());
        matchingCreateInfo.setLatitude(38.05);
        matchingCreateInfo.setLongitude(120.05);
        matchingCreateInfo.setMaxCount(5);

        matchingService.startMatching("dlwlsrn9412@kookmin.ac.kr", matchingCreateInfo);

        MatchingCreateInfo matchingCreateInfo2 = new MatchingCreateInfo();
        matchingCreateInfo2.setTitle("title");
        matchingCreateInfo2.setDescription("desc");
        matchingCreateInfo2.setStartTime(LocalDateTime.now());
        matchingCreateInfo2.setLatitude(38.05);
        matchingCreateInfo2.setLongitude(120.05);
        matchingCreateInfo2.setMaxCount(5);

        matchingService.startMatching("dlwlsrn9412@kookmin.ac.kr", matchingCreateInfo2);
    }

    @Test
    public void test() {

    }
}