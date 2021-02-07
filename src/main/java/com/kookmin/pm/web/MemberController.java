package com.kookmin.pm.web;

import com.kookmin.pm.module.member.domain.Member;
import com.kookmin.pm.module.member.dto.MemberCreateInfo;
import com.kookmin.pm.module.member.repository.MemberRepository;
import com.kookmin.pm.module.member.service.MemberService;
import com.kookmin.pm.support.util.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import javax.persistence.EntityExistsException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;
    private final MemberRepository memberRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

    @PostMapping(value = "/signin")
    public ResponseEntity<Map<String,String>> signIn(@RequestBody Map<String, String> user) {
        String email = user.get("email");
        String password = user.get("password");

        Member member = memberRepository.findByEmail(email).orElseThrow(EntityExistsException::new);
        if(!passwordEncoder.matches(password,member.getPassword())) {
            //TODO:: 로그인 실패 처리
            throw new RuntimeException();
        }

        List<String> roles = new ArrayList<>();
        roles.add("USER");

        Map<String,String> userInfos = new HashMap<>();
        userInfos.put("access-token", jwtTokenProvider.createToken(email, roles));
        userInfos.put("nickname", member.getNickname());

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userInfos);
    }

    @PostConstruct
    public void setUp() {
        MemberCreateInfo memberCreateInfo = new MemberCreateInfo();
        memberCreateInfo.setAddress("서울시~~");
        memberCreateInfo.setEmail("dlwlsrn9411@kookmin.ac.kr");
        memberCreateInfo.setName("이진구");
        memberCreateInfo.setPassword("1234");
        memberCreateInfo.setNickname("LJG");
        memberCreateInfo.setPhoneNumber("010-8784-3827");

        memberService.joinMember(memberCreateInfo);
    }
}
