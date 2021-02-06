package com.kookmin.pm.module.member.dto;

import lombok.Data;

@Data
//TODO::Validate 설정해야함
public class MemberCreateInfo {
    private String email;
    private String password;
    private String nickname;
    private String name;
    private String address;
    private String phoneNumber;
}
