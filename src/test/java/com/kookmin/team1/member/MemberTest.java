package com.kookmin.team1.member;

import com.kookmin.team1.module.member.domain.Member;
import com.kookmin.team1.module.member.dto.MemberCreateInfo;
import com.kookmin.team1.module.member.repository.MemberRepository;
import com.kookmin.team1.module.member.service.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
//TODO::프로파일 적용으로 분리 필요
public class MemberTest {
    @Autowired
    MemberService memberService;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    EntityManager entityManager;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Test
    @DisplayName("MemberService 회원가입 성공 테스트")
    public void joinMember_success_test() {
        MemberCreateInfo memberCreateInfo = new MemberCreateInfo();
        memberCreateInfo.setEmail("dlwlsrn9411@kookmin.ac.kr");
        memberCreateInfo.setPassword("1234");
        memberCreateInfo.setNickname("jingu");
        memberCreateInfo.setAddress("서울시~~~");
        memberCreateInfo.setName("이진구");
        memberCreateInfo.setPhoneNumber("010-8784-3827");

        Long id = memberService.joinMember(memberCreateInfo);
        Member member = memberRepository.findByEmail(memberCreateInfo.getEmail()).orElseThrow(EntityNotFoundException::new);

        assertThat(member)
                .hasFieldOrPropertyWithValue("id", id)
                .hasFieldOrPropertyWithValue("email", memberCreateInfo.getEmail())
                .hasFieldOrPropertyWithValue("nickname", memberCreateInfo.getNickname())
                .hasFieldOrPropertyWithValue("name", memberCreateInfo.getName())
                .hasFieldOrPropertyWithValue("phoneNumber", memberCreateInfo.getPhoneNumber())
                .hasFieldOrPropertyWithValue("address", memberCreateInfo.getAddress());

        boolean isPasswordMatches = passwordEncoder.matches(memberCreateInfo.getPassword()
                , member.getPassword());

        assertThat(isPasswordMatches).isTrue();
    }
}
