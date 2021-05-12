package com.kookmin.pm.module.matching.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kookmin.pm.module.category.domain.Category;
import com.kookmin.pm.module.category.repository.CategoryRepository;
import com.kookmin.pm.module.image.service.DomainImageService;
import com.kookmin.pm.module.image.service.DomainType;
import com.kookmin.pm.module.image.service.FileUploadService;
import com.kookmin.pm.module.matching.domain.Matching;
import com.kookmin.pm.module.matching.domain.MatchingParticipant;
import com.kookmin.pm.module.matching.domain.MatchingStatus;
import com.kookmin.pm.module.matching.domain.ParticipantStatus;
import com.kookmin.pm.module.matching.dto.*;
import com.kookmin.pm.module.matching.repository.MatchingMapper;
import com.kookmin.pm.module.matching.repository.MatchingParticipantRepository;
import com.kookmin.pm.module.matching.repository.MatchingRepository;
import com.kookmin.pm.module.member.domain.Member;
import com.kookmin.pm.module.member.dto.MemberDetails;
import com.kookmin.pm.module.member.repository.MemberRepository;
import com.kookmin.pm.module.member.service.LookupType;
import com.kookmin.pm.module.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
@RequiredArgsConstructor
public class MatchingService {
    private final MatchingRepository matchingRepository;
    private final MatchingParticipantRepository matchingParticipantRepository;
    private final CategoryRepository categoryRepository;
    private final MemberService memberService;
    private final MemberRepository memberRepository;
    private final MatchingMapper matchingMapper;
    private final DomainImageService domainImageService;

    //TODO::회원이 생성할 수 있는 매칭에 개수제한
    public Long openMatching(@NonNull Long usn, @NonNull MatchingCreateInfo matchingCreateInfo) {
        Member member = getMemberEntity(usn);
        Category category = getCategoryEntityByName(matchingCreateInfo.getCategory());

        //TODO::현재보다 과거시간대를 startTime으로 정할경우 Exception
        if(matchingCreateInfo.getStartTime().isBefore(LocalDateTime.now()))
            throw new RuntimeException();

        Matching matching = buildMatchingEntity(matchingCreateInfo, member, category);

        matchingRepository.save(matching);

        return matching.getId();
    }

    public Long participateMatching(@NonNull Long participantUsn,
                                    @NonNull Long matchingId) {
        Member member = getMemberEntity(participantUsn);
        Matching matching = getMatchingEntity(matchingId);

        //TODO::이미 참여한 회원일 경우 예외처리 정의
        if(matchingParticipantRepository.findByMemberAndMatching(member, matching).orElse(null) != null)
            throw new RuntimeException();

        //TODO::매칭을 생성한 사람일 경우 예외처리 정의
        if(matching.getMember().getId().equals(participantUsn))
            throw new RuntimeException();

        if(matchingParticipantRepository
                .countByMatchingAndStatus(matching, ParticipantStatus.PARTICIPATING)+1L >= matching.getMaxCount())
            throw new RuntimeException();

        //TODO::이미 시작한 매칭의 경우 + 요청 시간이 매칭의 시작시간을 초과했을 경우 로직 추가

        MatchingParticipant matchingParticipant = MatchingParticipant.builder()
                .member(member)
                .matching(matching)
                .build();

        matchingParticipant = matchingParticipantRepository.save(matchingParticipant);

        return matchingParticipant.getId();
    }

    public void editMatching(@NonNull Long usn, @NonNull MatchingEditInfo matchingEditInfo) {
        Matching matching = getMatchingEntity(matchingEditInfo.getId());

        //TODO::아직 시작되지 않은 상태의 매칭만 수정가능
        if(!matching.getStatus().equals(MatchingStatus.SCHEDULED))
            throw new RuntimeException();

        //TODO::수정을 요청한 회원과 매칭을 생성한 회원이 일치하지 않는 경우 익셉션 정의 필요
        if(!matching.getMember().getId().equals(usn))
            throw new RuntimeException();

        //TODO::이미 시작된 매칭의 경우, 현재보다 이전 시간을 매칭 시작시간으로 설정할 경우 정보 수정이 불가능하다. 익셉션 정의 필요
        if(matching.getStartTime().isBefore(LocalDateTime.now())
            || matchingEditInfo.getStartTime().isBefore(LocalDateTime.now()))
            throw new RuntimeException();

        //TODO::변경하려는 최대 인원수 보다 현재 참가 인원수가 더 많은 경우 익셉션 정의 필요
        if(!matching.getMaxCount().equals(matchingEditInfo.getMaxCount())) {
           List<Member> participants = matchingRepository
                   .searchMemberInMatchingParticipant(matchingEditInfo.getId(), ParticipantStatus.PARTICIPATING);
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

    public void quitMatching(@NonNull Long usn, @NonNull Long matchingId) {
        Matching matching = getMatchingEntity(matchingId);

        //TODO::매칭 생성자와 요청한 회원의 이메일이 일치하지 않을때 익셉션 정의 필요
        if(!matching.getMember().getId().equals(usn))
            throw new RuntimeException();

        //TODO::매칭에 참가한 다른 인원들에게 알림으로 알려주는 기능 필요
        List<Member> participants = matchingRepository
                .searchMemberInMatchingParticipant(matchingId, ParticipantStatus.PARTICIPATING);

        matchingParticipantRepository.deleteAllByMatching(matching);

        matchingRepository.delete(matching);
    }

    public Page<MatchingDetails> searchMatching(@NonNull Pageable pageable,
                                                @NonNull MatchingSearchCondition searchCondition) {
        if(searchCondition.getLongitude() == null || searchCondition.getLatitude() == null
            || searchCondition.getDistance() == null) {
            return matchingRepository.searchMatching(pageable, searchCondition);
        }

        ObjectMapper objectMapper = new ObjectMapper();
        Map map = objectMapper.convertValue(searchCondition, Map.class);

        return new PageImpl(matchingMapper.searchMatchingWithLocationInfo(map));
    }

    public MatchingDetails lookupMatching(@NonNull Long matchingId, @NonNull MatchingLookUpType lookUpType) {
        Matching matching = getMatchingEntity(matchingId);
        Category category = matching.getCategory();

        MatchingDetails matchingDetails = null;

        if(lookUpType.equals(MatchingLookUpType.DEFAULT)) {
            matchingDetails = new MatchingDetails(matching);

            List<String> imageList = this.getMatchingImageUrl(matchingId, category);
            matchingDetails.setImageList(imageList);
        } else if(lookUpType.equals(MatchingLookUpType.WITH_HOST)){
            matchingDetails = new MatchingDetails(matching);

            MemberDetails memberDetails = memberService
                    .lookUpMemberDetails(matching.getMember().getId(), LookupType.WITHALLINFOS);

            matchingDetails.setHost(memberDetails);

            List<String> imageList = this.getMatchingImageUrl(matchingId, category);
            matchingDetails.setImageList(imageList);
        } else if(lookUpType.equals(MatchingLookUpType.WITH_PARTICIPANTS)) {
            matchingDetails = new MatchingDetails(matching);

            List<String> imageList = this.getMatchingImageUrl(matchingId, category);
            matchingDetails.setImageList(imageList);

            if(matching.getMember() != null) {
                MemberDetails memberDetails = memberService
                        .lookUpMemberDetails(matching.getMember().getId(), LookupType.WITHALLINFOS);

                matchingDetails.setHost(memberDetails);
            }

            List<Member> participants = matchingRepository
                    .searchMemberInMatchingParticipant(matchingId, ParticipantStatus.PARTICIPATING);
            List<MemberDetails> participantDetails = new ArrayList<>();

            //TODO::엔티티를 가져와서 다시 dto로 변환하는데... 회원관련 다른 테이블도 전부 조인해야함, 조금 비효율적이다. 개선 필요
            for(Member member : participants) {
                participantDetails.add(memberService.lookUpMemberDetails(member.getId(),
                        LookupType.WITHALLINFOS));
            }

            matchingDetails.setParticipants(participantDetails);
            matchingDetails.setParticipantsCount(participantDetails.size());
        }

        return matchingDetails;
    }

    public void approveParticipationRequest (@NonNull Long usn, @NonNull Long requestId) {
        MatchingParticipant request = getMatchingParticipantEntity(requestId);
        Matching matching = request.getMatching();

        //TODO::이미 매칭이 시작된 경우
        if(!matching.getStatus().equals(MatchingStatus.SCHEDULED))
            throw new RuntimeException();

        //TODO::이미 매칭에 참가중인 회원일 경우
        if(request.getStatus().equals(ParticipantStatus.PARTICIPATING))
            throw new RuntimeException();

        //TODO::참가요청을 승인하는 회원이 매칭의 호스트가 아닐 경우
        if(!matching.getMember().getId().equals(usn))
            throw new RuntimeException();

        //TODO::매칭의 최대인원수에 도달한 경우
        if(matchingParticipantRepository.countByMatchingAndStatus(matching, ParticipantStatus.PARTICIPATING) + 1L
                >= matching.getMaxCount())
            throw new RuntimeException();

        request.approveMatching();
    }

    public void rejectParticipationRequest(@NonNull Long usn, @NonNull Long requestId) {
        MatchingParticipant request = getMatchingParticipantEntity(requestId);
        Matching matching = request.getMatching();

        //TODO::이미 매칭에 참가한 회원인 경우
        if(request.getStatus().equals(ParticipantStatus.PARTICIPATING))
            throw new RuntimeException();

        //TODO::요청에 대한 거절을 하는 회원이 매칭의 호스트가 아닌 경우
        if(!matching.getMember().getId().equals(usn))
            throw new RuntimeException();

        matchingParticipantRepository.delete(request);
    }

    public void quitParticipationRequest(@NonNull Long usn, @NonNull Long requestId) {
        MatchingParticipant request = getMatchingParticipantEntity(requestId);

        //TODO:: 이미 참가중인 경우
        if(request.getStatus().equals(ParticipantStatus.PARTICIPATING))
            throw new RuntimeException();

        //TODO::참가요청 취소를 보낸 회원이 주체가 아닌 경우
        if(!request.getMember().getId().equals(usn))
            throw new RuntimeException();

        matchingParticipantRepository.delete(request);
    }

    public void cancelParticipation(@NonNull Long usn, @NonNull Long matchingId) {
        Matching matching = getMatchingEntity(matchingId);
        Member member = getMemberEntity(usn);

        MatchingParticipant participant = matchingParticipantRepository.findByMemberAndMatching(member, matching)
                .orElseThrow(EntityNotFoundException::new);

        //TODO::참가 취소 신청회원이 해당 매칭에 참가중이지 않은 경우
        if(!participant.getStatus().equals(ParticipantStatus.PARTICIPATING))
            throw new RuntimeException();

        matchingParticipantRepository.delete(participant);
    }

    public void startMatching(@NonNull Long usn, @NonNull Long matchingId) {
        Matching matching = getMatchingEntity(matchingId);

        //TODO::매칭 호스트가 아닌데 매칭을 시작할 경우
        if(!matching.getMember().getId().equals(usn))
            throw new RuntimeException();

        //TODO::이미 매칭이 진행중이거나 종료된 매칭인데 시작을 요청할 경우
        if(!matching.getStatus().equals(MatchingStatus.SCHEDULED))
            throw new RuntimeException();

        //TODO::매칭에 참여한 다른 회원들에게 매칭이 시작되었음을 알려줘야

        matchingParticipantRepository.deleteAllByMatchingAndStatus(matching, ParticipantStatus.PENDING_ACCEPTANCE);

        matching.startMatching();
    }

    public void endMatching(@NonNull Long usn, @NonNull Long matchingId) {
        Matching matching = getMatchingEntity(matchingId);

        //TODO::진행중인 매칭이 아닌데 종료를 할 경우
        if(!matching.getStatus().equals(MatchingStatus.PROCEEDING))
            throw new RuntimeException();

        //TODO::매칭 호스트가 아닌 경우
        if(!matching.getMember().getId().equals(usn))
            throw new RuntimeException();

        matching.endMatching();
    }

    public List<MatchingParticipantDetails> findMyParticipationRequest(@NonNull Long usn) {
        Member member = getMemberEntity(usn);

        List<MatchingParticipant> participants = matchingParticipantRepository
                .findByMemberAndStatus(member, ParticipantStatus.PENDING_ACCEPTANCE);

        List<MatchingParticipantDetails> request = new ArrayList<>();

        for(MatchingParticipant matchingParticipant : participants)
            request.add(new MatchingParticipantDetails(matchingParticipant));

        return request;
    }

    public Map<String, Object> findMatchingParticipationRequest(@NonNull Long usn) {
        Map<String, Object> request = new HashMap<>();

        Member member = getMemberEntity(usn);

        List<Matching> scheduledMatchingList = matchingRepository.findByMemberAndStatus(member,
                MatchingStatus.SCHEDULED);

        List<String> matchingTitles = new ArrayList<>();

        for(Matching matching : scheduledMatchingList)
            matchingTitles.add(matching.getTitle());

        request.put("matching", matchingTitles);

        int index = 0;

        for(Matching matching : scheduledMatchingList) {
            List<MatchingParticipant> participants = matchingParticipantRepository
                    .findByMatchingAndStatus(matching, ParticipantStatus.PENDING_ACCEPTANCE);

            List<MatchingParticipantDetails> details = new ArrayList<>();


            for(MatchingParticipant participant : participants) {
                details.add(new MatchingParticipantDetails(participant));
            }

            request.put(String.valueOf(index), details);
            index++;
        }

        return request;
    }

    public MatchingParticipantDetails lookupMatchingParticipants(@NonNull Long participantsId) {
        MatchingParticipant matchingParticipant = getMatchingParticipantEntity(participantsId);

        return new MatchingParticipantDetails(matchingParticipant);
    }

    public String uploadMatchingImage(@NonNull Long matchingId, @NonNull MultipartFile file) {
        return this.domainImageService.uploadImage(matchingId, file);
    }

    private List<String> getMatchingImageUrl(@NonNull Long matchingId, Category category) {
        return this.domainImageService.getImageUrl(matchingId, category.getName());
    }

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

    //TODO::EntityNotFoundException extends한 익셉션 정의 필요
    private MatchingParticipant getMatchingParticipantEntity(Long id) {
        return matchingParticipantRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
    }

    private Member getMemberEntity(Long usn) {
        return memberRepository.findById(usn)
                .orElseThrow(EntityNotFoundException::new);
    }

    private Category getCategoryEntityByName(String categoryName) {
        return categoryRepository.findByName(categoryName)
                .orElseThrow(EntityNotFoundException::new);
    }

    private Matching getMatchingEntity(Long id) {
        return matchingRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
    }

    public List<MatchingDetails> findParticipatedMatching(MatchingSearchCondition searchCondition) {
        return matchingRepository.findParticipatedMatching(searchCondition);
    }
}
