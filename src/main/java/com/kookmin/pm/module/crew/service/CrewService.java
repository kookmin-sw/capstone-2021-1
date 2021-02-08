package com.kookmin.pm.module.crew.service;

import com.kookmin.pm.module.category.domain.Category;
import com.kookmin.pm.module.category.repository.CategoryRepository;
import com.kookmin.pm.module.crew.domain.Crew;
import com.kookmin.pm.module.crew.dto.CrewCreateInfo;
import com.kookmin.pm.module.crew.dto.CrewDetails;
import com.kookmin.pm.module.crew.dto.CrewEditInfo;
import com.kookmin.pm.module.crew.repository.CrewParticipantsRepository;
import com.kookmin.pm.module.crew.repository.CrewRepository;
import com.kookmin.pm.module.member.domain.Member;
import com.kookmin.pm.module.member.dto.MemberDetails;
import com.kookmin.pm.module.member.repository.MemberRepository;
import com.kookmin.pm.module.member.service.LookupType;
import com.kookmin.pm.module.member.service.MemberService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class CrewService {
    private final CrewRepository crewRepository;
    private final CrewParticipantsRepository crewParticipantsRepository;
    private final MemberRepository memberRepository;
    private final CategoryRepository categoryRepository;
    private final MemberService memberService;

    //TODO::크루명이 유일할 필요가 있는지
    public Long establishCrew(@NonNull String email, @NonNull CrewCreateInfo crewCreateInfo) {
        Member member = getMemberEntityByEmail(email);
        Category category = getCategoryEntityByName(crewCreateInfo.getCategory());

        Crew crew = buildCrewEntity(crewCreateInfo, member, category);

        crew = crewRepository.save(crew);

        return crew.getId();
    }

    //TODO::활동지역은 여러개 인가
    public void editCrewInfo(@NonNull String email, @NonNull CrewEditInfo crewEditInfo) {
        Member member = getMemberEntityByEmail(email);
        Crew crew = getCrewEntity(crewEditInfo.getId());

        //TODO::이메일이 서로 다른 경우
        if(!crew.getMember().getEmail().equals(email))
            throw new RuntimeException();

        crew.editName(crewEditInfo.getName());
        crew.editDescription(crewEditInfo.getDescription());
    }

    public CrewDetails lookupCrew(@NonNull Long crewId, @NonNull CrewLookupType type) {
        CrewDetails crewDetails = null;
        Crew crew = getCrewEntity(crewId);

        if(type.equals(CrewLookupType.DEFAULT)) {
            crewDetails = new CrewDetails(crew);
        } else if(type.equals(CrewLookupType.WITH_HOST)) {
            crewDetails = new CrewDetails(crew);
            MemberDetails host = memberService.lookUpMemberDetails(crew.getMember().getEmail(),
                    LookupType.WITHALLINFOS);
            crewDetails.setHost(host);
        } else if(type.equals(CrewLookupType.WITH_PARTICIPANTS)){
            crewDetails = new CrewDetails(crew);
            MemberDetails host = memberService.lookUpMemberDetails(crew.getMember().getEmail(),
                    LookupType.WITHALLINFOS);
            crewDetails.setHost(host);

            List<Member> participants = crewRepository.findMemberInCrewParticipants(crew.getId());
            List<MemberDetails> participantList = new ArrayList<>();

            for(Member participant : participants) {
                participantList.add(memberService.lookUpMemberDetails(participant.getEmail(),
                        LookupType.WITHALLINFOS));
            }

            crewDetails.setParticipants(participantList);
        }

        return crewDetails;
    }

    private Crew getCrewEntity(Long id) {
        return crewRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
    }

    private Member getMemberEntityByEmail(String email) {
        return memberRepository.findByEmail(email)
                .orElseThrow(EntityNotFoundException::new);
    }

    private Category getCategoryEntityByName(String categoryName) {
        return categoryRepository.findByName(categoryName)
                .orElseThrow(EntityNotFoundException::new);
    }

    private Crew buildCrewEntity(CrewCreateInfo crewCreateInfo, Member member, Category category) {
        return Crew.builder()
                .name(crewCreateInfo.getName())
                .description(crewCreateInfo.getDescription())
                .activityArea(crewCreateInfo.getActivityArea())
                .maxCount(crewCreateInfo.getMaxCount())
                .member(member)
                .category(category)
                .build();
    }
}
