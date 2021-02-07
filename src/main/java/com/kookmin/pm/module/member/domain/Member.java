package com.kookmin.pm.module.member.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
@Table(name = "MEMBER")
public class Member {
    @Id @GeneratedValue
    @Column(name="MEMBER_ID")
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

    @Builder
    public Member(String email, String password, String nickname, String name,
                  String phoneNumber, String address) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.status = MemberStatus.ACTIVE;
        this.role = MemberRole.USER;
    }

    public void encodePassword(PasswordEncoder passwordEncoder) {
        this.password = passwordEncoder.encode(password);
    }

    public void changePassword(String password) {
        this.password = password;
    }

    public void editNickname(String nickname) {
        this.nickname = nickname;
    }

    public void changeName(String name) {
        this.name = name;
    }

    public void editPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void editAddress(String address) {
        this.address = address;
    }

    public void changeStatus(MemberStatus status) {
        this.status = status;
    }
}
