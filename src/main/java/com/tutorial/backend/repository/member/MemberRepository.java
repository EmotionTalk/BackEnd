package com.tutorial.backend.repository.member;

import com.tutorial.backend.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByMemberEmail(String memberEmail);

    boolean existsByMemberEmail(String memberEmail);

    Optional<Member> findMemberByMemberEmailAndMemberName(String email, String name);

    Optional<Member> findByMemberPhoneContaining(String phoneNumber);
}
