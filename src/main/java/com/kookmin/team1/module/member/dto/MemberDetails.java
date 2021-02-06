package com.kookmin.team1.module.member.dto;
치
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
