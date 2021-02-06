package com.kookmin.pm.module.member.service;

import com.kookmin.pm.module.member.domain.MemberStats;
import com.kookmin.pm.module.member.dto.MemberCreateInfo;
import com.kookmin.pm.module.member.domain.Member;
import com.kookmin.pm.module.member.dto.MemberDetails;
import com.kookmin.pm.module.member.dto.MemberEditInfo;
import com.kookmin.pm.module.member.repository.MemberRepository;
import com.kookmin.pm.module.member.repository.MemberStatsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
@RequiredArgsConstructor
public class MemberService implements UserDetailsService {
    private final MemberRepository memberRepository;
    private final MemberStatsRepository memberStatsRepository;
    private final PasswordEncoder passwordEncoder;

    public Long joinMember(@NonNull MemberCreateInfo memberCreateInfo) {
        //TODO::RuntimeException 정의 해야함, 회원 이메일이 중복되었을 경우
        if(isDuplicated(memberCreateInfo.getEmail())) throw new RuntimeException();

        //TODO::회원 가입시, 이미지, 회원 능력치를 같이 추가해줘야함
        Member member = buildMemberEntity(memberCreateInfo);
        member.encodePassword(passwordEncoder);
        member = memberRepository.save(member);

        MemberStats memberStats = MemberStats.builder()
                .member(member)
                .build();
        memberStatsRepository.save(memberStats);

        return member.getId();
    }

    public void editMemberInfo(@NonNull String email, @NonNull MemberEditInfo memberEditInfo) {
        Member member = getMemberEntityByEmail(email);

        member.editNickname(memberEditInfo.getNickname());
        member.editPhoneNumber(memberEditInfo.getPhoneNumber());
        member.editAddress(memberEditInfo.getAddress());

        member.changeName(memberEditInfo.getName());
        member.changePassword(memberEditInfo.getPassword());
        member.encodePassword(passwordEncoder);
    }

    public MemberDetails lookUpMemberDetails(@NonNull String email) {
        Member member = getMemberEntityByEmail(email);

        //TODO::회원 정보, 회원 이미지 정보, 회원 능력치 정보를 조회해야 함

        return null;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        //TODO:: 그냥 UsernameNotFoundException 던져주는 걸로 끝나도 되는지 확인해야함
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(email));

        return User.builder()
                .username(member.getEmail())
                .password(member.getPassword())
                .roles(member.getRole().toString())
                .build();
    }

    private Member buildMemberEntity(MemberCreateInfo memberCreateInfo) {
        return Member.builder()
                .email(memberCreateInfo.getEmail())
                .name(memberCreateInfo.getName())
                .password(memberCreateInfo.getPassword())
                .nickname(memberCreateInfo.getNickname())
                .phoneNumber(memberCreateInfo.getPhoneNumber())
                .address(memberCreateInfo.getAddress())
                .build();
    }

    private boolean isDuplicated(String email) {
        Member member = memberRepository.findByEmail(email).orElse(null);
        return member!=null;
    }

    private Member getMemberEntityByEmail(String email) {
        return memberRepository.findByEmail(email).orElseThrow(EntityNotFoundException::new);
    }
}
