package com.kookmin.pm.module.member.dto;

import lombok.Data;

@Data
public class MemberEditInfo {
    private String password;
    private String nickname;
    private String name;
    private String address;
    private String phoneNumber;
}
