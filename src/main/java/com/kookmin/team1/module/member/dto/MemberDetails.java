package com.kookmin.team1.module.member.dto;

import com.kookmin.team1.module.member.domain.MemberImage;
import com.kookmin.team1.module.member.domain.MemberStats;
import lombok.Data;

@Data
public class MemberDetails {
    private Long id;
    private String email;
    private String password;
    private String nickname;
    private String name;
    private String address;
    private String phoneNumber;
    private MemberStats memberStats;
    private MemberImage memberImage;
}
