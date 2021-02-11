package com.kookmin.pm.web;

import com.kookmin.pm.module.crew.dto.CrewCreateInfo;
import com.kookmin.pm.module.member.domain.Member;
import com.kookmin.pm.module.member.domain.MemberRole;
import com.kookmin.pm.module.member.dto.MemberCreateInfo;
import com.kookmin.pm.module.member.dto.MemberDetails;
import com.kookmin.pm.module.member.dto.MemberEditInfo;
import com.kookmin.pm.module.member.repository.MemberRepository;
import com.kookmin.pm.module.member.service.LookupType;
import com.kookmin.pm.module.member.service.MemberService;
import com.kookmin.pm.support.util.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.persistence.EntityExistsException;
import java.security.Principal;
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
        String uid = user.get("uid");
        String password = user.get("password");

        Member member = memberRepository.findByUid(uid).orElseThrow(EntityExistsException::new);
        if(!passwordEncoder.matches(password,member.getPassword())) {
            //TODO:: 로그인 실패 처리
            throw new RuntimeException();
        }

        List<String> roles = new ArrayList<>();
        roles.add(MemberRole.USER.toString());

        Map<String,String> userInfos = new HashMap<>();

        userInfos.put("access-token", jwtTokenProvider.createToken(member.getId().toString(), roles));
        userInfos.put("nickname", member.getNickname());

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userInfos);
    }

    @PostMapping(value = "/signup")
    public ResponseEntity<String> signUp(@RequestBody MemberCreateInfo memberCreateInfo) {
        System.out.println(memberCreateInfo);
        memberService.joinMember(memberCreateInfo);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body("회원가입이 완료되었습니다.");
    }

    @GetMapping(value = "/member/detail/{usn}")
    public ResponseEntity<MemberDetails> lookupMember(@PathVariable(name = "usn") Long usn) {
        MemberDetails details = memberService.lookUpMemberDetails(usn, LookupType.WITHALLINFOS);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(details);
    }

    @PutMapping(value = "/member/edit")
    public ResponseEntity<String> editMember(Principal principal, @RequestBody MemberEditInfo memberEditInfo) {
        Long usn = Long.parseLong(principal.getName());
        memberService.editMemberInfo(usn, memberEditInfo);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body("정보 수정이 완료되었습니다.");
    }

    @GetMapping(value = "/member/all")
    public ResponseEntity<List<Member>> getAllMember() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(memberRepository.findAll());
    }
}
