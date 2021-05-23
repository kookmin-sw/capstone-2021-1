package com.kookmin.pm.module.member.dto;

import com.kookmin.pm.module.member.domain.MemberStats;
import com.kookmin.pm.module.member.domain.Member;
import com.kookmin.pm.module.member.domain.MemberImage;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class MemberDetails {
    private Long id;
    private String uid;
    private String nickname;
    private String name;
    private String address;
    private String phoneNumber;
    private String description;
    private MemberStatsInfo memberStats;
    private List<String> imageList;

    public MemberDetails(Member member) {
        this.id = member.getId();
        this.uid = member.getUid();
        this.nickname = member.getNickname();
        this.name = member.getName();
        this.address = member.getAddress();
        this.phoneNumber = member.getPhoneNumber();
        this.description = member.getDescription();
    }

    public MemberDetails(Member member, List<String> imageList) {
        this(member);
        this.imageList = imageList;
    }

    public MemberDetails(Member member, List<String> imageList, MemberStats memberStats) {
        this(member, imageList);
        this.memberStats = new MemberStatsInfo(memberStats);
    }
}
