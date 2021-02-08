package com.kookmin.pm.module.crew.service;

import com.kookmin.pm.module.category.domain.Category;
import com.kookmin.pm.module.category.repository.CategoryRepository;
import com.kookmin.pm.module.crew.domain.Crew;
import com.kookmin.pm.module.crew.dto.CrewCreateInfo;
import com.kookmin.pm.module.crew.repository.CrewRepository;
import com.kookmin.pm.module.member.domain.Member;
import com.kookmin.pm.module.member.dto.MemberCreateInfo;
import com.kookmin.pm.module.member.repository.MemberRepository;
import com.kookmin.pm.module.member.service.MemberService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class CrewServiceTest {
    @Autowired
    private CrewService crewService;
    @Autowired
    private MemberService memberService;
    @Autowired
    private CrewRepository crewRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private MemberRepository memberRepository;

    @BeforeEach
    public void setUp() {
        MemberCreateInfo memberCreateInfo = new MemberCreateInfo();
        memberCreateInfo.setEmail("dlwlsrn9412@kookmin.ac.kr");
        memberCreateInfo.setPassword("1234");
        memberCreateInfo.setNickname("jingu2");
        memberCreateInfo.setAddress("서울시~~~");
        memberCreateInfo.setName("이진구");
        memberCreateInfo.setPhoneNumber("010-8784-3827");

        memberService.joinMember(memberCreateInfo);

        MemberCreateInfo memberCreateInfo2 = new MemberCreateInfo();
        memberCreateInfo2.setEmail("dlwlsrn10@kookmin.ac.kr");
        memberCreateInfo2.setPassword("1234");
        memberCreateInfo2.setNickname("jingu2");
        memberCreateInfo2.setAddress("서울시~~~");
        memberCreateInfo2.setName("이진팔");
        memberCreateInfo2.setPhoneNumber("010-8784-3827");

        memberService.joinMember(memberCreateInfo2);

        MemberCreateInfo memberCreateInfo3 = new MemberCreateInfo();
        memberCreateInfo3.setEmail("dlwlsrn7@kookmin.ac.kr");
        memberCreateInfo3.setPassword("1234");
        memberCreateInfo3.setNickname("jingu3");
        memberCreateInfo3.setAddress("서울시~~~");
        memberCreateInfo3.setName("이진칠");
        memberCreateInfo3.setPhoneNumber("010-8784-3827");

        memberService.joinMember(memberCreateInfo3);

        MemberCreateInfo memberCreateInfo4 = new MemberCreateInfo();
        memberCreateInfo4.setEmail("dlwlsrn6@kookmin.ac.kr");
        memberCreateInfo4.setPassword("1234");
        memberCreateInfo4.setNickname("jingu4");
        memberCreateInfo4.setAddress("서울시~~~");
        memberCreateInfo4.setName("이진육");
        memberCreateInfo4.setPhoneNumber("010-8784-3827");

        memberService.joinMember(memberCreateInfo4);

        Category category = new Category("BOARD_GAME");
        categoryRepository.save(category);

        Category category2 = new Category("ROOM_ESCAPE");
        categoryRepository.save(category2);
    }
    @Test
    @DisplayName("establishGuild 성공 테스트")
    public void establishGuild_success_test() {
        CrewCreateInfo crewCreateInfo = new CrewCreateInfo();
        crewCreateInfo.setName("크루1");
        crewCreateInfo.setDescription("크루 설명1");
        crewCreateInfo.setActivityArea("서울");
        crewCreateInfo.setCategory("BOARD_GAME");
        crewCreateInfo.setMaxCount(5);

        Long id = crewService.establishCrew("dlwlsrn9412@kookmin.ac.kr", crewCreateInfo);
        Crew crew = crewRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        Member member = memberRepository.findByEmail("dlwlsrn9412@kookmin.ac.kr")
                .orElseThrow(EntityNotFoundException::new);


        assertThat(crew)
                .hasFieldOrPropertyWithValue("name", crewCreateInfo.getName())
                .hasFieldOrPropertyWithValue("description", crewCreateInfo.getDescription())
                .hasFieldOrPropertyWithValue("activityArea", crewCreateInfo.getActivityArea())
                .hasFieldOrPropertyWithValue("maxCount", crewCreateInfo.getMaxCount());

        assertThat(crew.getCategory())
                .hasFieldOrPropertyWithValue("name", crewCreateInfo.getCategory());

        assertThat(crew.getMember())
                .isEqualTo(member);
    }


}