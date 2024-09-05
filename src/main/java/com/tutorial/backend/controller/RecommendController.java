package com.tutorial.backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tutorial.backend.service.member.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/recomend/*")
@RequiredArgsConstructor
@Slf4j
public class RecommendController {
    private final MemberService memberService;

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    // 요청 헤더를 설정하는 메서드
    private HttpHeaders getHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }
}
