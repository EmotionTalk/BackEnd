package com.tutorial.backend.service.auth;

import com.tutorial.backend.controller.dto.TokenDto;
import com.tutorial.backend.controller.dto.TokenRequestDto;
import com.tutorial.backend.entity.Member;

import java.util.Optional;

public interface AuthService {

    public Optional<Member> getMemberByEmail(String email);

    public TokenDto socialLogin(String email, String name, String phone);

    public TokenDto reissue(TokenRequestDto tokenRequestDto);

}
