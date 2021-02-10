package com.kookmin.pm.module.member.dto;

import com.kookmin.pm.module.member.domain.MemberStats;
import com.kookmin.pm.module.member.domain.Member;
import com.kookmin.pm.module.member.domain.MemberImage;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MemberDetails {
    private Long id;
    private String uid;
    private String nickname;
    private String name;
    private String address;
    private String phoneNumber;
    private MemberStatsInfo memberStats;
    private String imagePath;

    public MemberDetails(Member member) {
        this.id = member.getId();
        this.uid = member.getUid();
        this.nickname = member.getNickname();
        this.name = member.getName();
        this.address = member.getAddress();
        this.phoneNumber = member.getPhoneNumber();
    }

    public MemberDetails(Member member, MemberImage memberImage) {
        this(member);
        if(memberImage == null) {
            this.imagePath = null;
        } else {
            this.imagePath = memberImage.getImagePath();
        }
    }

    public MemberDetails(Member member, MemberImage memberImage, MemberStats memberStats) {
        this(member, memberImage);
        this.memberStats = new MemberStatsInfo(memberStats);
    }
}
