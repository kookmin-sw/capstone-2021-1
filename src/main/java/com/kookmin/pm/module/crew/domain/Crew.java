package com.kookmin.pm.module.crew.domain;

import com.kookmin.pm.module.category.domain.Category;
import com.kookmin.pm.module.member.domain.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@Table(name="CREW")
public class Crew {
    @Id @GeneratedValue
    @Column(name="CREW_ID")
    private Long id;

    @Column(name="NAME")
    private String name;

    @Column(name="DESCRIPTION")
    private String description;

    @Column(name="MAX_COUNT")
    private Integer maxCount;

    @Column(name="ACTIVITY_AREA")
    private String activityArea;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="CATEGORY_ID")
    private Category category;

    @CreationTimestamp
    @Column(name = "CREATED_AT")
    private LocalDateTime createdAt;

    @Builder
    public Crew(String name, String description, Integer maxCount, String activityArea,
                Member member, Category category) {
        this.name = name;
        this.description = description;
        this.maxCount = maxCount;
        this.activityArea = activityArea;
        this.member = member;
        this.category = category;
    }
}
