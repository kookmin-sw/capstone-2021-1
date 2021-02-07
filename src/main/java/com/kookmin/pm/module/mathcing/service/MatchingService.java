package com.kookmin.pm.module.mathcing.service;

import com.kookmin.pm.module.mathcing.domain.Matching;
import com.kookmin.pm.module.mathcing.domain.MatchingParticipant;
import com.kookmin.pm.module.mathcing.dto.MatchingCreateInfo;
import com.kookmin.pm.module.mathcing.dto.MatchingDetails;
import com.kookmin.pm.module.mathcing.repository.MatchingParticipantRepository;
import com.kookmin.pm.module.mathcing.repository.MatchingRepository;
import com.kookmin.pm.module.member.domain.Member;
import com.kookmin.pm.module.member.dto.MemberDetails;
import com.kookmin.pm.module.member.repository.MemberRepository;
import com.kookmin.pm.module.member.service.LookupType;
import com.kookmin.pm.module.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class MatchingService {
    private final MatchingRepository matchingRepository;
    private final MatchingParticipantRepository matchingParticipantRepository;
    private final MemberService memberService;
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

    public MatchingDetails lookupMatching(@NonNull Long matchingId, @NonNull MatchingLookUpType lookUpType) {
        Matching matching = getMatchingEntity(matchingId);

        MatchingDetails matchingDetails = null;

        if(lookUpType.equals(MatchingLookUpType.DEFAULT)) {
            matchingDetails = new MatchingDetails(matching);

        } else if(lookUpType.equals(MatchingLookUpType.WITH_HOST)){
            matchingDetails = new MatchingDetails(matching);

            MemberDetails memberDetails = memberService
                    .lookUpMemberDetails(matching.getMember().getEmail(), LookupType.WITHALLINFOS);

            matchingDetails.setHost(memberDetails);

        } else if(lookUpType.equals(MatchingLookUpType.WITH_PARTICIPANTS)) {
            matchingDetails = new MatchingDetails(matching);

            MemberDetails memberDetails = memberService
                    .lookUpMemberDetails(matching.getMember().getEmail(), LookupType.WITHALLINFOS);

            matchingDetails.setHost(memberDetails);

            //TODO::Querydsl을 이용한 조인으로 방향을 정함, Member관련 querydsl 레포지토리 구현 필요
            List<Member> participants = matchingRepository.searchMemberInMatchingParticipant(matchingId);
            List<MemberDetails> participantDetails = new ArrayList<>();

            //TODO::엔티티를 가져와서 다시 dto로 변환하는데... 회원관련 다른 테이블도 전부 조인해야함, 조금 비효율적이다. 개선 필요
            for(Member member : participants)
                participantDetails.add(memberService.lookUpMemberDetails(member.getEmail(),
                        LookupType.WITHALLINFOS));

            matchingDetails.setParticipants(participantDetails);
            matchingDetails.setParticipantsCount(participantDetails.size());
        }

        return matchingDetails;
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
