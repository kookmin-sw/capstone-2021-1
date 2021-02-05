package com.kookmin.team1.module.member.repository;

import com.kookmin.team1.module.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member,Long> {
    public Optional<Member> findByEmail(String email);
}
