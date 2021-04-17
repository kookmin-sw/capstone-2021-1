package com.kookmin.pm.web;

import com.kookmin.pm.module.crew.dto.CrewCreateInfo;
import com.kookmin.pm.module.matchup.domain.MatchUp;
import com.kookmin.pm.module.matchup.dto.MatchUpDetails;
import com.kookmin.pm.module.matchup.dto.MemberRecord;
import com.kookmin.pm.module.matchup.service.MatchUpLookUpType;
import com.kookmin.pm.module.matchup.service.MatchUpService;
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
    private final MatchUpService matchUpService;

    @PostMapping(value = "/signin")
    public ResponseEntity signIn(@RequestBody Map<String, String> user) {
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
    public ResponseEntity signUp(@RequestBody MemberCreateInfo memberCreateInfo) {
        System.out.println(memberCreateInfo);
        memberService.joinMember(memberCreateInfo);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body("회원가입이 완료되었습니다.");
    }

    @GetMapping(value = "/member/detail/{usn}")
    public ResponseEntity lookupMember(@PathVariable(name = "usn") Long usn) {
        MemberDetails details = memberService.lookUpMemberDetails(usn, LookupType.WITHALLINFOS);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(details);
    }

    @PutMapping(value = "/member/edit")
    public ResponseEntity editMember(Principal principal, @RequestBody MemberEditInfo memberEditInfo) {
        Long usn = Long.parseLong(principal.getName());
        memberService.editMemberInfo(usn, memberEditInfo);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body("정보 수정이 완료되었습니다.");
    }

    @GetMapping(value = "/member/all")
    public ResponseEntity getAllMember() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(memberRepository.findAll());
    }

    @GetMapping(value = "/member/match-up")
    public ResponseEntity getMyMatchUp(Principal principal) {
        Long usn = getPrincipalKey(principal);
        List<MatchUpDetails> matchUpList = matchUpService.searchMyMatchUp(usn);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(matchUpList);
    }

    @GetMapping(value = "/member/match-up/{matchUpId}")
    public ResponseEntity getMyMatchUpDetails(Principal principal,
                                              @PathVariable(name = "matchUpId") Long matchUpId) {
        Long usn = getPrincipalKey(principal);
        MatchUpDetails matchUpDetails = matchUpService.lookUpMatchUp(matchUpId, MatchUpLookUpType.WITH_ALL_INFOS);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(matchUpDetails);
    }

    @GetMapping(value = "/member/record")
    public ResponseEntity getMyRecord(Principal principal) {
        Long usn = getPrincipalKey(principal);
        MemberRecord record = matchUpService.lookUpMyRecord(usn);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(record);
    }



    private Long getPrincipalKey(Principal principal) {
        return Long.parseLong(principal.getName());
    }
}
