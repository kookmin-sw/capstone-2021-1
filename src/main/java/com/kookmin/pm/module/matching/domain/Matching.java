package com.kookmin.pm.module.matching.domain;

import com.kookmin.pm.module.category.domain.Category;
import com.kookmin.pm.module.member.domain.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@Table(name="MATCHING")
public class Matching {
    @Id @GeneratedValue
    @Column(name = "MATCHING_ID")
    private Long id;

    @Column(name="TITLE")
    private String title;

    @Column(name="DESCRIPTION")
    private String description;

    @Column(name="START_TIME")
    private LocalDateTime startTime;

    @Column(name="END_TIME")
    private LocalDateTime endTime;

    @Column(name="LATITUDE")
    private Double latitude;

    @Column(name="LONGITUDE")
    private Double longitude;

    @Enumerated(EnumType.STRING)
    @Column(name="STATUS")
    private MatchingStatus status;

    @Column(name="MAX_COUNT")
    private Integer maxCount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "MATCHING_ID")
    private Category category;

    @Builder
    public Matching(String title, String description, LocalDateTime startTime, Double latitude, Double longitude,
                    Integer maxCount, Member member, Category category) {
        this.title = title;
        this.description = description;
        this.startTime = startTime;
        this.latitude = latitude;
        this.longitude = longitude;
        this.status = MatchingStatus.SCHEDULED;
        this.maxCount = maxCount;
        this.member = member;
        this.category = category;
    }

    public void startMatching() {
        this.status = MatchingStatus.PROCEEDING;
    }

    public void endMatching() {
        this.status = MatchingStatus.END;
        this.endTime = LocalDateTime.now();
    }

    public void editLocation(Double latitude, Double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public void editTitle(String title) {
        this.title = title;
    }

    public void editDescription(String description) {
        this.description = description;
    }

    public void editStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public void editMaxCount(Integer maxCount) {
        this.maxCount = maxCount;
    }

    public void changeCategory(Category category) {
        this.category = category;
    }
}
