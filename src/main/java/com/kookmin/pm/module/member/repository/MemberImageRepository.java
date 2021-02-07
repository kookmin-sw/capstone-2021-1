package com.kookmin.pm.module.member.repository;

import com.kookmin.pm.module.member.domain.Member;
import com.kookmin.pm.module.member.domain.MemberImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberImageRepository extends JpaRepository<MemberImage, Long> {
    Optional<MemberImage> findByMember(Member member);
}
