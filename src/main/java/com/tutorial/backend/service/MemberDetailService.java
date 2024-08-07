package com.tutorial.backend.service;

import com.tutorial.backend.entity.Member;
import com.tutorial.backend.provider.MemberDetail;
import com.tutorial.backend.repository.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service @Slf4j
@RequiredArgsConstructor
public class MemberDetailService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    @Transactional
    public MemberDetail loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = memberRepository.findByMemberEmail(username).get();
        if (member == null) {
            throw new UsernameNotFoundException("User not found with email: " + username);
        }
        log.info(member.getMemberName());

        // MemberDetail 객체 생성시 Member와 권한 정보를 함께 전달
        return new MemberDetail(member);
    }

}
