package com.kookmin.pm.module.mathcing.service;

import com.kookmin.pm.module.mathcing.domain.Matching;
import com.kookmin.pm.module.mathcing.dto.MatchingCreateInfo;
import com.kookmin.pm.module.mathcing.repository.MatchingParticipantRepository;
import com.kookmin.pm.module.mathcing.repository.MatchingRepository;
import com.kookmin.pm.module.member.domain.Member;
import com.kookmin.pm.module.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;

@Service
@Transactional
@RequiredArgsConstructor
public class MatchingService {
    private final MatchingRepository matchingRepository;
    private final MatchingParticipantRepository matchingParticipantRepository;
    private final MemberRepository memberRepository;

    //TODO::회원 엔티티와 매핑되어야함
    public Long startMatching(@NonNull String email, @NonNull MatchingCreateInfo matchingCreateInfo) {
        Member member = getMemberEntityByEmail(email);

        Matching matching = buildMatchingEntity(matchingCreateInfo, member);

        matchingRepository.save(matching);

        return matching.getId();
    }

    private Matching buildMatchingEntity(MatchingCreateInfo matchingCreateInfo, Member member) {
        return Matching.builder()
                .title(matchingCreateInfo.getTitle())
                .description(matchingCreateInfo.getDescription())
                .startTime(LocalDateTime.now())
                .latitude(matchingCreateInfo.getLatitude())
                .longitude(matchingCreateInfo.getLongitude())
                .maxCount(matchingCreateInfo.getMaxCount())
                .member(member)
                .build();
    }

    private Member getMemberEntityByEmail(String email) {
        return memberRepository.findByEmail(email)
                .orElseThrow(EntityNotFoundException::new);
    }
}
