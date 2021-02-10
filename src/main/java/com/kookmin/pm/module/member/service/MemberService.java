package com.kookmin.pm.module.member.service;

import com.kookmin.pm.module.member.domain.MemberImage;
import com.kookmin.pm.module.member.domain.MemberStats;
import com.kookmin.pm.module.member.domain.MemberStatus;
import com.kookmin.pm.module.member.dto.MemberCreateInfo;
import com.kookmin.pm.module.member.domain.Member;
import com.kookmin.pm.module.member.dto.MemberDetails;
import com.kookmin.pm.module.member.dto.MemberEditInfo;
import com.kookmin.pm.module.member.repository.MemberImageRepository;
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
    private final MemberImageRepository memberImageRepository;
    private final PasswordEncoder passwordEncoder;

    public Long joinMember(@NonNull MemberCreateInfo memberCreateInfo) {
        //TODO::RuntimeException 정의 해야함, 회원 이메일이 중복되었을 경우
       if(isDuplicated(memberCreateInfo.getUid()))
            throw new RuntimeException();

        Member member = buildMemberEntity(memberCreateInfo);
        member.encodePassword(passwordEncoder);
        member = memberRepository.save(member);

        MemberStats memberStats = MemberStats.builder()
                .member(member)
                .build();
        memberStatsRepository.save(memberStats);

        MemberImage memberImage = MemberImage.builder()
                .member(member)
                .build();

        memberImageRepository.save(memberImage);

        return member.getId();
    }

    public void editMemberInfo(@NonNull String uid, @NonNull MemberEditInfo memberEditInfo) {
        Member member = getMemberEntityByUid(uid);

        member.editNickname(memberEditInfo.getNickname());
        member.editPhoneNumber(memberEditInfo.getPhoneNumber());
        member.editAddress(memberEditInfo.getAddress());

        member.changeName(memberEditInfo.getName());
        member.changePassword(memberEditInfo.getPassword());
        member.encodePassword(passwordEncoder);
    }


    public MemberDetails lookUpMemberDetails(@NonNull String uid, @NonNull LookupType type) {
        Member member = getMemberEntityByUid(uid);
        //TODO::휴면 계정일 경우 조회 불가능, RuntimeException 정의해야
        if(member.getStatus().equals(MemberStatus.EXPIRED)) throw new RuntimeException();

        //TODO::더 나은 구조 구상해보기
        if(type==LookupType.DEFAULT) {
            return new MemberDetails(member);
        } else if(type==LookupType.WITHIMAGE) {
            return new MemberDetails(member,
                    getMemberImageEntityByUid(uid));
        } else {
            return new MemberDetails(member,
                    getMemberImageEntityByUid(uid),
                    getMemberStatsEntityByUid(uid));
        }
    }

    public boolean secessionMember(@NonNull String uid, @NonNull String password) {
        Member member = getMemberEntityByUid(uid);

        if(!passwordEncoder.matches(password, member.getPassword())) return false;

        member.changeStatus(MemberStatus.EXPIRED);

        return true;
    }

    public void changeMemberImage(@NonNull String uid, @NonNull String imagePath) {
        MemberImage memberImage = getMemberImageEntityByUid(uid);
        memberImage.editImagePath(imagePath);
    }

    public void removeMemberImage(@NonNull String uid) {
        MemberImage memberImage = getMemberImageEntityByUid(uid);
        memberImageRepository.delete(memberImage);
    }

    //TODO:: 회원 능력치 평가 메소드 필요

    @Override
    public UserDetails loadUserByUsername(String uid) throws UsernameNotFoundException {
        //TODO:: 그냥 UsernameNotFoundException 던져주는 걸로 끝나도 되는지 확인해야함
        Member member = memberRepository.findByUid(uid)
                .orElseThrow(() -> new UsernameNotFoundException(uid));

        return User.builder()
                .username(member.getUid())
                .password(member.getPassword())
                .roles(member.getRole().toString())
                .build();
    }

    private Member buildMemberEntity(MemberCreateInfo memberCreateInfo) {
        return Member.builder()
                .uid(memberCreateInfo.getUid())
                .name(memberCreateInfo.getName())
                .password(memberCreateInfo.getPassword())
                .nickname(memberCreateInfo.getNickname())
                .phoneNumber(memberCreateInfo.getPhoneNumber())
                .address(memberCreateInfo.getAddress())
                .build();
    }

    private boolean isDuplicated(String uid) {
        Member member = memberRepository.findByUid(uid).orElse(null);
        return member!=null;
    }

    private Member getMemberEntityByUid(String uid) {
        return memberRepository.findByUid(uid).orElseThrow(EntityNotFoundException::new);
    }

    private MemberImage getMemberImageEntityByUid(String uid) {
        Member member = getMemberEntityByUid(uid);
        return memberImageRepository.findByMember(member).orElse(null);
    }

    private MemberStats getMemberStatsEntityByUid(String uid) {
        Member member = getMemberEntityByUid(uid);
        return memberStatsRepository.findByMember(member).orElseThrow(EntityNotFoundException::new);
    }
}
