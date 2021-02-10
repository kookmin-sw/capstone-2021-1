package com.kookmin.pm.module.member.repository;

import com.kookmin.pm.module.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.swing.text.html.Option;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member,Long> {
    public Optional<Member> findByUid(String uid);
    public Optional<Member> findByUidAndProvider(String uid, String provider);
}
