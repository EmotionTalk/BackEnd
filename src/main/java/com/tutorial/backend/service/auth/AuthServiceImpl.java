package com.tutorial.backend.service.auth;

import com.tutorial.backend.controller.dto.TokenDto;
import com.tutorial.backend.controller.dto.TokenRequestDto;
import com.tutorial.backend.entity.Member;
import com.tutorial.backend.entity.RefreshToken;
import com.tutorial.backend.entity.type.Authority;
import com.tutorial.backend.entity.type.StatusType;
import com.tutorial.backend.jwt.TokenProvider;
import com.tutorial.backend.provider.MemberDetail;
import com.tutorial.backend.repository.member.MemberRepository;
import com.tutorial.backend.repository.refreshToken.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService{
    private final MemberRepository memberRepository;
    private final TokenProvider tokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;

    @Override
    @Transactional
    public Optional<Member> getMemberByEmail(String email) {
        return memberRepository.findByMemberEmail(email);
    }

    @Override
    @Transactional
    public TokenDto socialLogin(String email, String name, String phone) {
        Optional<Member> optionalMember = memberRepository.findByMemberEmail(email);
        return optionalMember.map(member -> updateAndLogin(member, name, phone)).orElseGet(() -> signupAndLogin(email, name, phone));
    }

    private TokenDto signupAndLogin(String email, String name, String phone) {
        log.info("Sign up new member: " + email);
        Member newMember = Member.builder()
                .memberEmail(email)
                .memberName(name)
                .memberPhone(phone)
                .status(StatusType.ABLE)
                .authority(Authority.USER)
                .build();
        memberRepository.save(newMember);

        MemberDetail memberDetail = new MemberDetail(newMember);
        TokenDto tokenDto = tokenProvider.generateTokenDto(memberDetail);
        saveRefreshToken(email, tokenDto.getRefreshToken());

        return tokenDto;
    }

    private TokenDto updateAndLogin(Member member, String name, String phone) {
        log.info("Updating member: " + member.getMemberEmail());
        member.setMemberName(name);
        member.setMemberPhone(phone);
        memberRepository.save(member);

        MemberDetail memberDetail = new MemberDetail(member);
        TokenDto tokenDto = tokenProvider.generateTokenDto(memberDetail);
        saveRefreshToken(member.getMemberEmail(), tokenDto.getRefreshToken());

        return tokenDto;
    }

    @Override
    @Transactional
    public TokenDto reissue(TokenRequestDto tokenRequestDto) {
        if (!tokenProvider.validateToken(tokenRequestDto.getRefreshToken())) {
            throw new RuntimeException("Refresh Token 이 유효하지 않습니다.");
        }

        MemberDetail authentication = (MemberDetail) tokenProvider.getAuthentication(tokenRequestDto.getAccessToken());
        RefreshToken refreshToken = refreshTokenRepository.findByKey(authentication.getName())
                .orElseThrow(() -> new RuntimeException("로그아웃 된 사용자입니다."));

        if (!refreshToken.getValue().equals(tokenRequestDto.getRefreshToken())) {
            throw new RuntimeException("토큰의 유저 정보가 일치하지 않습니다.");
        }

        TokenDto tokenDto = tokenProvider.generateTokenDto(authentication);
        RefreshToken newRefreshToken = refreshToken.updateValue(tokenDto.getRefreshToken());
        refreshTokenRepository.save(newRefreshToken);

        return tokenDto;
    }


    private void saveRefreshToken(String email, String refreshTokenValue) {
        RefreshToken refreshToken = RefreshToken.builder()
                .key(email)
                .value(refreshTokenValue)
                .build();
        refreshTokenRepository.save(refreshToken);
    }
}
