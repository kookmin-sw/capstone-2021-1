package com.kookmin.pm.module.member.dto;

import lombok.Data;

@Data
//TODO::Validate 설정해야함, Provider 멤버 변수 추가헤줘야
public class MemberCreateInfo {
    private String uid;
    private String password;
    private String nickname;
    private String name;
    private String address;
    private String phoneNumber;
}
