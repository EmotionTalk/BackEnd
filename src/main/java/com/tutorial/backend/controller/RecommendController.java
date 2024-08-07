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
//
//    @PostMapping("/getPlaceRecomend")
//    public ResponseEntity<ResultDto<List<Place>>> getPlaceRecomend(@RequestParam int passengerNum, Authentication authentication){
//        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
//        try {
//            Optional<Member> foundMember = memberService.getMemberByMemberEmailAndPassword(userDetails.getUsername(),userDetails.getPassword());
//
//            if(foundMember.isPresent()){
//                List<MyPlaceDto> placeList = placeService.getPlaceListByMemberId(foundMember.get().getId());
//
//                // JSON 형식으로 요청 파라미터를 만듬.
//                MultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
//                requestParams.add("passengerNum", String.valueOf(passengerNum));
//                requestParams.add("member", objectMapper.writeValueAsString(foundMember.get()));
//                requestParams.add("placeList", objectMapper.writeValueAsString(placeList));
//
//                // 요청을 보낼 URL을 만듬.
//                String requestUrl = "http://localhost:3030/getPlaceRecomend";
//
//                // POST 요청을 보냄.
//                ResponseEntity<String> response = restTemplate.exchange(
//                        requestUrl,
//                        HttpMethod.POST,
//                        new HttpEntity<>(requestParams, getHeaders()),
//                        String.class
//                );
//
//                // 응답을 처리함.
//                String responseBody = response.getBody();
//                log.info("Response from recommendation service: {}", responseBody);
//            }
//
//            return null;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
//
//    }

    // 요청 헤더를 설정하는 메서드
    private HttpHeaders getHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }
}
