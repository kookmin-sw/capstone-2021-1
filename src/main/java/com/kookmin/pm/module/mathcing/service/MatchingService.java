package com.kookmin.pm.module.mathcing.service;

import com.kookmin.pm.module.mathcing.domain.Matching;
import com.kookmin.pm.module.mathcing.domain.MatchingParticipant;
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
    //TODO::회원이 생성할 수 있는 매칭에 개수제한
    public Long startMatching(@NonNull String email, @NonNull MatchingCreateInfo matchingCreateInfo) {
        Member member = getMemberEntityByEmail(email);

        Matching matching = buildMatchingEntity(matchingCreateInfo, member);

        matchingRepository.save(matching);

        return matching.getId();
    }

    public Long participateMatching(@NonNull String participantEmail,
                                    @NonNull Long matchingId) {
        Member member = getMemberEntityByEmail(participantEmail);
        Matching matching = getMatchingEntity(matchingId);

        //TODO::이미 참여한 회원일 경우 예외처리 정의
        if(matchingParticipantRepository.findByMemberAndMatching(member, matching).orElse(null) != null)
            throw new RuntimeException();

        //TODO::매칭을 생성한 사람일 경우 예외처리 정의
        if(matching.getMember().getEmail().equals(participantEmail))
            throw new RuntimeException();

        //TODO::이미 회원이 다 찬 경우 참여 불가 예외처리 정의
        if(matchingParticipantRepository.findByMatching(matching).size()+1 >= matching.getMaxCount())
            throw new RuntimeException();

        //TODO::이미 시작한 매칭의 경우 + 요청 시간이 매칭의 시작시간을 초과했을 경우 로직 추가

        MatchingParticipant matchingParticipant = MatchingParticipant.builder()
                .member(member)
                .matching(matching)
                .build();

        matchingParticipant = matchingParticipantRepository.save(matchingParticipant);

        return matchingParticipant.getId();
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

    //TODO::익셉션 정의 필요
    private Matching getMatchingEntity(Long id) {
        return matchingRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
    }
}
