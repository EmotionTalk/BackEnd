package com.tutorial.backend.repository.friend;

import com.tutorial.backend.entity.Friend;
import com.tutorial.backend.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FriendRepository extends JpaRepository<Friend, Long>, FriendQueryDSL {
    List<Friend> findByMemberId(Long memberId);
    boolean existsByMemberAndFriendMember(Member member, Member friend);
}
