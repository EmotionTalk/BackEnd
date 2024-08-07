package com.tutorial.backend.controller;


import com.tutorial.backend.controller.dto.*;
import com.tutorial.backend.provider.MemberDetail;
import com.tutorial.backend.service.auth.AuthServiceImpl;
import com.tutorial.backend.service.member.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth/*")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final AuthServiceImpl authServiceImpl;
    private final MemberService memberService;


    @PostMapping("reissue")
    public ResponseEntity<TokenDto> reissue(@RequestBody TokenRequestDto tokenRequestDto) {
        return ResponseEntity.ok(authServiceImpl.reissue(tokenRequestDto));
    }

    @GetMapping("user")
    public ResponseEntity<ResultDto<MemberDto>> getUserDetails(Authentication authentication, MemberDto memberDto) {
        // 사용자 정보를 기반으로 회원 정보를 조회하여 반환
        MemberDetail principal = (MemberDetail) authentication.getPrincipal();
        memberDto.setName(principal.getName());
        memberDto.setProfileUrl(principal.getMember().getProfileImageUrl());
        try {
            return ResponseEntity.ok()
                    .body(ResultDto.res(HttpStatus.ACCEPTED, "회원정보를 조회해왔습니다",memberDto));
        } catch (Exception exception) {
            log.error("회원 정보를 조회하는 중에 오류가 발생했습니다.", exception);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ResultDto.res(HttpStatus.INTERNAL_SERVER_ERROR, "회원 정보를 조회하는 중에 오류가 발생했습니다."));
        }
    }




}
