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
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

@Service
@Transactional
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

    public void editMemberInfo(@NonNull Long usn, @NonNull MemberEditInfo memberEditInfo) {
        Member member = getMemberEntity(usn);

        member.editNickname(memberEditInfo.getNickname());
        member.editPhoneNumber(memberEditInfo.getPhoneNumber());
        member.editAddress(memberEditInfo.getAddress());
        member.changeName(memberEditInfo.getName());
        member.editDescription(memberEditInfo.getDescription());
    }


    public MemberDetails lookUpMemberDetails(@NonNull Long usn, @NonNull LookupType type) {
        Member member = getMemberEntity(usn);
        //TODO::휴면 계정일 경우 조회 불가능, RuntimeException 정의해야
        if(member.getStatus().equals(MemberStatus.EXPIRED)) throw new RuntimeException();

        //TODO::더 나은 구조 구상해보기
        if(type==LookupType.DEFAULT) {
            return new MemberDetails(member);
        } else if(type==LookupType.WITHIMAGE) {
            return new MemberDetails(member,
                    getMemberImageEntity(usn));
        } else {
            return new MemberDetails(member,
                    getMemberImageEntity(usn),
                    getMemberStatsEntity(usn));
        }
    }

    public boolean secessionMember(@NonNull Long usn, @NonNull String password) {
        Member member = getMemberEntity(usn);

        if(!passwordEncoder.matches(password, member.getPassword())) return false;

        member.changeStatus(MemberStatus.EXPIRED);

        return true;
    }

    public void changeMemberImage(@NonNull Long usn, @NonNull String imagePath) {
        MemberImage memberImage = getMemberImageEntity(usn);
        memberImage.editImagePath(imagePath);
    }

    public void removeMemberImage(@NonNull Long usn) {
        MemberImage memberImage = getMemberImageEntity(usn);
        memberImageRepository.delete(memberImage);
    }

    //TODO:: 회원 능력치 평가 메소드 필요

    @Override
    public UserDetails loadUserByUsername(String usn) throws UsernameNotFoundException {
        //TODO:: 그냥 UsernameNotFoundException 던져주는 걸로 끝나도 되는지 확인해야함
        Member member = memberRepository.findById(Long.parseLong(usn))
                .orElseThrow(() -> new UsernameNotFoundException(usn));

        return User.builder()
                .username(member.getId().toString())
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
                .provider(memberCreateInfo.getProvider())
                .build();
    }

    public boolean isDuplicated(String uid) {
        Member member = memberRepository.findByUid(uid).orElse(null);
        return member!=null;
    }

    private Member getMemberEntity(Long usn) {
        return memberRepository.findById(usn).orElseThrow(EntityNotFoundException::new);
    }

    private MemberImage getMemberImageEntity(Long usn) {
        Member member = getMemberEntity(usn);
        return memberImageRepository.findByMember(member).orElse(null);
    }

    private MemberStats getMemberStatsEntity(Long usn) {
        Member member = getMemberEntity(usn);
        return memberStatsRepository.findByMember(member).orElseThrow(EntityNotFoundException::new);
    }
}
