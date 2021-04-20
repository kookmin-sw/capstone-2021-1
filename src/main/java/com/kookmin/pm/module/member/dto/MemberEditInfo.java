package com.kookmin.pm.module.member.dto;

import lombok.Data;

@Data
public class MemberEditInfo {
    private String nickname;
    private String name;
    private String address;
    private String phoneNumber;
    private String description;
}
