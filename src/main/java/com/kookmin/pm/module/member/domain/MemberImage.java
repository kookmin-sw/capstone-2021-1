package com.kookmin.pm.module.member.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity(name="MEMBER_IMAGE")
@Getter
@NoArgsConstructor
public class MemberImage {
    @Id @GeneratedValue
    @Column(name = "MEMBER_IMAGE_ID")
    private Long id;

    @Column(name = "IMAGE_PATH")
    private String imagePath;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @Builder
    public MemberImage(Member member) {
        this.member = member;
    }

    public void editImagePath(String imagePath) {
        this.imagePath=imagePath;
    }
}
