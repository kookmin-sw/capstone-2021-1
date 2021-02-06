package com.kookmin.team1.member;

import com.kookmin.team1.module.member.domain.Member;
import com.kookmin.team1.module.member.dto.MemberCreateInfo;
import com.kookmin.team1.module.member.dto.MemberEditInfo;
import com.kookmin.team1.module.member.repository.MemberRepository;
import com.kookmin.team1.module.member.service.MemberService;
import org.junit.jupiter.api.BeforeEach;
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

    @BeforeEach
    public void setUp() {
        MemberCreateInfo memberCreateInfo = new MemberCreateInfo();
        memberCreateInfo.setEmail("dlwlsrn9412@kookmin.ac.kr");
        memberCreateInfo.setPassword("1234");
        memberCreateInfo.setNickname("jingu2");
        memberCreateInfo.setAddress("서울시~~~");
        memberCreateInfo.setName("이진구");
        memberCreateInfo.setPhoneNumber("010-8784-3827");

        MemberCreateInfo memberCreateInfo2 = new MemberCreateInfo();
        memberCreateInfo2.setEmail("dlwlsrn9413@kookmin.ac.kr");
        memberCreateInfo2.setPassword("12345");
        memberCreateInfo2.setNickname("jingu2");
        memberCreateInfo2.setAddress("서울시~~~");
        memberCreateInfo2.setName("이진팔");
        memberCreateInfo2.setPhoneNumber("010-8784-3827");

        MemberCreateInfo memberCreateInfo3 = new MemberCreateInfo();
        memberCreateInfo3.setEmail("dlwlsrn9414@kookmin.ac.kr");
        memberCreateInfo3.setPassword("12346");
        memberCreateInfo3.setNickname("jingu3");
        memberCreateInfo3.setAddress("서울시~~~");
        memberCreateInfo3.setName("이진칠");
        memberCreateInfo3.setPhoneNumber("010-8784-3827");

        memberService.joinMember(memberCreateInfo);
        memberService.joinMember(memberCreateInfo2);
        memberService.joinMember(memberCreateInfo3);
    }

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

    @Test
    @DisplayName("MemberService 회원정보 수정 성공 테스트")
    public void editMemberInfo_success_test() {
        MemberEditInfo memberEditInfo = new MemberEditInfo();
        memberEditInfo.setAddress("수정 주소");
        memberEditInfo.setName("수정 이름");
        memberEditInfo.setPassword("123456");
        memberEditInfo.setPhoneNumber("010-8888-8888");
        memberEditInfo.setNickname("수정 닉네임");

        memberService.editMemberInfo("dlwlsrn9412@kookmin.ac.kr", memberEditInfo);
        Member member = memberRepository.findByEmail("dlwlsrn9412@kookmin.ac.kr").orElseThrow(EntityNotFoundException::new);

        assertThat(member)
                .hasFieldOrPropertyWithValue("address", memberEditInfo.getAddress())
                .hasFieldOrPropertyWithValue("name", memberEditInfo.getName())
                .hasFieldOrPropertyWithValue("phoneNumber", memberEditInfo.getPhoneNumber())
                .hasFieldOrPropertyWithValue("nickname", memberEditInfo.getNickname());

        boolean isPasswordMatches = passwordEncoder.matches(memberEditInfo.getPassword(), member.getPassword());
        assertThat(isPasswordMatches).isTrue();
    }
}
