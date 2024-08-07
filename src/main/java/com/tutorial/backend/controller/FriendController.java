package com.tutorial.backend.controller;

import com.tutorial.backend.controller.dto.FriendDto;
import com.tutorial.backend.controller.dto.NewFriendRequest;
import com.tutorial.backend.controller.dto.ResultDto;
import com.tutorial.backend.entity.Friend;
import com.tutorial.backend.entity.Member;
import com.tutorial.backend.provider.MemberDetail;
import com.tutorial.backend.service.friend.FriendService;
import com.tutorial.backend.service.member.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/friend/*")
@RequiredArgsConstructor
@Slf4j
public class FriendController {
    private final FriendService friendService;
    private final MemberService memberService;

    @PostMapping("newFriend")
    public ResponseEntity<ResultDto<String>> addNewFriend(@RequestBody NewFriendRequest newFriendRequest, Authentication authentication) {
        MemberDetail principal = (MemberDetail) authentication.getPrincipal();
        Member me = principal.getMember();

        String name = newFriendRequest.getName();
        String fullPhoneNumber = newFriendRequest.getFullPhoneNumber();
        Optional<Member> foundMember = memberService.getMemberByPhoneNumber(fullPhoneNumber);
        if (foundMember.isPresent()) {
            Friend friend = friendService.addNewFriend(name, foundMember.get(), me);
            return ResponseEntity.ok().body(ResultDto.res(HttpStatus.ACCEPTED, "성공!"));
        } else {
            return ResponseEntity.badRequest().body(ResultDto.res(HttpStatus.BAD_REQUEST, "실패!"));
        }
    }

    @GetMapping("getFriends")
    public ResponseEntity<ResultDto<List<FriendDto>>> getMyFriends(Authentication authentication){
        MemberDetail principal = (MemberDetail) authentication.getPrincipal();
        List<FriendDto> myFriends = friendService.getAllFriendsByMemberId(principal.getMember().getId());
        return ResponseEntity.ok().body(ResultDto.res(HttpStatus.ACCEPTED,"친구를 불러왔습니다!",myFriends));
    }
}
