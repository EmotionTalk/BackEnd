package com.tutorial.backend.service.member;

import com.tutorial.backend.controller.dto.JoinForm;
import com.tutorial.backend.entity.Member;
import com.tutorial.backend.repository.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = false)
@Slf4j
public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;


    @Override
    public Optional<Member> getMemberByEmail(String memberEmail) {
        return memberRepository.findByMemberEmail(memberEmail);
    }

    @Override
    public Member saveMember(JoinForm loginForm) {
        memberRepository.save(toEntity(loginForm));
        return toEntity(loginForm);
    }

    @Override
    public Optional<Member> getMemberByPhoneNumber(String phoneNumber) {
        return memberRepository.findByMemberPhoneContaining(phoneNumber);
    }


    @Override
    public Optional<Member> getMemberByMemberEmailAndMemberName(String memberEmail, String memberName) {
        return memberRepository.findMemberByMemberEmailAndMemberName(memberEmail, memberName);
    }

    @Override
    public Optional<Member> getMemberById(Long id) {
        return memberRepository.findById(id);
    }

}
