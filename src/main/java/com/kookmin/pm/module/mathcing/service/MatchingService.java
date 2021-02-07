package com.kookmin.pm.module.mathcing.service;

import com.kookmin.pm.module.mathcing.dto.MatchingCreateInfo;
import com.kookmin.pm.module.mathcing.repository.MatchingRepository;
import com.kookmin.pm.module.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class MatchingService {
    private final MatchingRepository matchingRepository;
    private final MemberRepository memberRepository;

    public Long startMatching(@NonNull String email, @NonNull MatchingCreateInfo matchingCreateInfo) {
        return null;
    }
}
