package com.kookmin.team1.module.member.domain;

import lombok.Getter;

import javax.persistence.*;

@Entity(name = "MEMBER")
@Getter
public class Member {
    @Id @GeneratedValue
    private Long id;

    @Column(name="EMAIL")
    private String email;

    @Column(name="PASSWORD")
    private String password;

    @Column(name="NICKNAME")
    private String nickname;

    @Column(name="NAME")
    private String name;

    @Column(name="ADDRESS")
    private String address;

    @Column(name="PHONE_NUMBER")
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    @Column(name="STATUS")
    private MemberStatus status;

    @Enumerated(EnumType.STRING)
    @Column(name="ROLE")
    private MemberRole role;
}
