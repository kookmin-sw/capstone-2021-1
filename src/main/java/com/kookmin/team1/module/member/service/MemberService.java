package com.kookmin.team1.module.member.service;

import com.kookmin.team1.module.member.domain.Member;
import com.kookmin.team1.module.member.dto.MemberCreateInfo;
import com.kookmin.team1.module.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService implements UserDetailsService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public Long joinMember(@NonNull MemberCreateInfo memberCreateInfo) {
        Member member = Member.builder()
                .email(memberCreateInfo.getEmail())
                .name(memberCreateInfo.getName())
                .password(memberCreateInfo.getPassword())
                .nickname(memberCreateInfo.getNickname())
                .phoneNumber(memberCreateInfo.getPhoneNumber())
                .address(memberCreateInfo.getAddress())
                .build();

        member.encodePassword(passwordEncoder);

        member = memberRepository.save(member);

        return member.getId();
    }

    private boolean isDuplicated(String email) {
        Member member = memberRepository.findByEmail(email).orElse(null);
        return member==null;
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
}
