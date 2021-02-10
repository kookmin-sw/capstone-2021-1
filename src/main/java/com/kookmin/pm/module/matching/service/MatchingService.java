package com.kookmin.pm.module.matching.service;

import com.kookmin.pm.module.category.domain.Category;
import com.kookmin.pm.module.category.repository.CategoryRepository;
import com.kookmin.pm.module.matching.domain.Matching;
import com.kookmin.pm.module.matching.domain.MatchingParticipant;
import com.kookmin.pm.module.matching.domain.MatchingStatus;
import com.kookmin.pm.module.matching.dto.MatchingCreateInfo;
import com.kookmin.pm.module.matching.dto.MatchingDetails;
import com.kookmin.pm.module.matching.dto.MatchingEditInfo;
import com.kookmin.pm.module.matching.dto.MatchingSearchCondition;
import com.kookmin.pm.module.matching.repository.MatchingParticipantRepository;
import com.kookmin.pm.module.matching.repository.MatchingRepository;
import com.kookmin.pm.module.member.domain.Member;
import com.kookmin.pm.module.member.dto.MemberDetails;
import com.kookmin.pm.module.member.repository.MemberRepository;
import com.kookmin.pm.module.member.service.LookupType;
import com.kookmin.pm.module.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    private final CategoryRepository categoryRepository;
    private final MemberService memberService;
    private final MemberRepository memberRepository;

    //TODO::회원이 생성할 수 있는 매칭에 개수제한
    public Long startMatching(@NonNull String email, @NonNull MatchingCreateInfo matchingCreateInfo) {
        Member member = getMemberEntityByEmail(email);
        Category category = getCategoryEntityByName(matchingCreateInfo.getCategory());

        //TODO::현재보다 과거시간대를 startTime으로 정할경우 Exception
        if(matchingCreateInfo.getStartTime().isBefore(LocalDateTime.now()))
            throw new RuntimeException();

        Matching matching = buildMatchingEntity(matchingCreateInfo, member, category);

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

        //TODO::이미 회원이 다 찬 경우 참여 불가 예외처리 정의, count쿼리로 정의해줘야
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

    public void editMatching(@NonNull String email, @NonNull MatchingEditInfo matchingEditInfo) {
        Matching matching = getMatchingEntity(matchingEditInfo.getId());

        //TODO::아직 시작되지 않은 상태의 매칭만 수정가능
        if(!matching.getStatus().equals(MatchingStatus.SCHEDULED))
            throw new RuntimeException();

        //TODO::수정을 요청한 회원과 매칭을 생성한 회원이 일치하지 않는 경우 익셉션 정의 필요
        if(!matching.getMember().getEmail().equals(email))
            throw new RuntimeException();

        //TODO::이미 시작된 매칭의 경우, 현재보다 이전 시간을 매칭 시작시간으로 설정할 경우 정보 수정이 불가능하다. 익셉션 정의 필요
        if(matching.getStartTime().isBefore(LocalDateTime.now())
            || matchingEditInfo.getStartTime().isBefore(LocalDateTime.now()))
            throw new RuntimeException();

        //TODO::변경하려는 최대 인원수 보다 현재 참가 인원수가 더 많은 경우 익셉션 정의 필요
        if(!matching.getMaxCount().equals(matchingEditInfo.getMaxCount())) {
           List<Member> participants = matchingRepository.searchMemberInMatchingParticipant(matchingEditInfo.getId());
           if(participants.size() > matchingEditInfo.getMaxCount())
               throw new RuntimeException();
        }

        if(!matching.getCategory().getName().equals(matchingEditInfo.getCategory())) {
            Category category = getCategoryEntityByName(matchingEditInfo.getCategory());
            matching.changeCategory(category);
        }

        matching.editTitle(matchingEditInfo.getTitle());
        matching.editDescription(matchingEditInfo.getDescription());
        matching.editLocation(matchingEditInfo.getLatitude(), matchingEditInfo.getLongitude());
        matching.editMaxCount(matchingEditInfo.getMaxCount());
        matching.editStartTime(matchingEditInfo.getStartTime());
    }

    public void quitMatching(@NonNull String email, @NonNull Long matchingId) {
        Matching matching = getMatchingEntity(matchingId);

        //TODO::매칭 생성자와 요청한 회원의 이메일이 일치하지 않을때 익셉션 정의 필요
        if(!matching.getMember().getEmail().equals(email))
            throw new RuntimeException();

        //TODO::매칭에 참가한 다른 인원들에게 알림으로 알려주는 기능 필요
        List<Member> participants = matchingRepository.searchMemberInMatchingParticipant(matchingId);

        //TODO::조인문 발생하지 않나 검증 필
        matchingParticipantRepository.deleteAllByMatching(matching);

        matchingRepository.delete(matching);
    }

    public void cancelParticipation(@NonNull String email, @NonNull Long matchingId) {
        //TODO::참가 취소 신청회원이 해당 매칭에 없을 경우
        matchingParticipantRepository.deleteByMemberEmailAndMatchingId(email, matchingId);
    }

    public Page<MatchingDetails> searchMatching(@NonNull Pageable pageable,
                                                @NonNull MatchingSearchCondition searchCondition) {
        return matchingRepository.searchMatching(pageable, searchCondition);
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

            //TODO::status로 참여중인 회원만 조회하도록 비교해줘야함
            List<Member> participants = matchingRepository.searchMemberInMatchingParticipant(matchingId);
            List<MemberDetails> participantDetails = new ArrayList<>();

            //TODO::엔티티를 가져와서 다시 dto로 변환하는데... 회원관련 다른 테이블도 전부 조인해야함, 조금 비효율적이다. 개선 필요
            for(Member member : participants) {
                participantDetails.add(memberService.lookUpMemberDetails(member.getEmail(),
                        LookupType.WITHALLINFOS));
            }

            matchingDetails.setParticipants(participantDetails);
            matchingDetails.setParticipantsCount(participantDetails.size());
        }

        return matchingDetails;
    }

    //TODO:: 다른 회원들의 참가요청을 검색하는 메소드 필요

    //TODO:: 참가요청에 대한 참가를 승인하는 메소드 필요, 참가요청 상태를 참여중으로 변경하고 등등 처리해야

    //TODO:: 참가요청에 대한 참가를 거절하는 메소드 필요

    //TODO:: 해당 회원이 보낸 참가요청을 검색하는 메소드 필요

    //TODO:: 참가요청에 대한 상세 조회 메소드 필

    private Matching buildMatchingEntity(MatchingCreateInfo matchingCreateInfo, Member member, Category category) {
        return Matching.builder()
                .title(matchingCreateInfo.getTitle())
                .description(matchingCreateInfo.getDescription())
                .startTime(matchingCreateInfo.getStartTime())
                .latitude(matchingCreateInfo.getLatitude())
                .longitude(matchingCreateInfo.getLongitude())
                .maxCount(matchingCreateInfo.getMaxCount())
                .member(member)
                .category(category)
                .build();
    }

    private Member getMemberEntityByEmail(String email) {
        return memberRepository.findByEmail(email)
                .orElseThrow(EntityNotFoundException::new);
    }

    private Category getCategoryEntityByName(String categoryName) {
        return categoryRepository.findByName(categoryName)
                .orElseThrow(EntityNotFoundException::new);
    }

    //TODO::익셉션 정의 필요
    private Matching getMatchingEntity(Long id) {
        return matchingRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
    }
}
