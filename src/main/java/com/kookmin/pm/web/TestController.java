package com.kookmin.pm.web;

import com.kookmin.pm.module.category.domain.Category;
import com.kookmin.pm.module.category.repository.CategoryRepository;
import com.kookmin.pm.module.crew.dto.CrewCreateInfo;
import com.kookmin.pm.module.crew.repository.CrewRepository;
import com.kookmin.pm.module.crew.service.CrewService;
import com.kookmin.pm.module.member.dto.MemberCreateInfo;
import com.kookmin.pm.module.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;

@RequiredArgsConstructor
@RestController
public class TestController {
    private final MemberService memberService;
    private final CrewService crewService;
    private final CategoryRepository categoryRepository;

    @PostConstruct
    public void setUp() {
        MemberCreateInfo memberCreateInfo = new MemberCreateInfo();
        memberCreateInfo.setUid("dlwlsrn94@naver.com");
        memberCreateInfo.setName("이진구");
        memberCreateInfo.setNickname("LJG070");
        memberCreateInfo.setPhoneNumber("010-8784-3827");
        memberCreateInfo.setPassword("1234");

        System.out.println("usn: " + memberService.joinMember(memberCreateInfo));

        Category category = new Category("BOARD GAME");
        Category category2 = new Category("ROOM ESCAPE");
        Category category3 = new Category("CRIME SCENE");

        categoryRepository.save(category);
        categoryRepository.save(category2);
        categoryRepository.save(category3);

        CrewCreateInfo crewCreateInfo = new CrewCreateInfo();
        crewCreateInfo.setName("크루명");
        crewCreateInfo.setMaxCount(10);
        crewCreateInfo.setCategory("BOARD GAME");
        crewCreateInfo.setActivityArea("서울");
        crewCreateInfo.setDescription("크루 소개");

        System.out.println("CREW : " + crewService.establishCrew(1L, crewCreateInfo));
    }
}
