package com.tutorial.backend.service.friend;

import com.tutorial.backend.controller.dto.FriendDto;
import com.tutorial.backend.entity.Friend;
import com.tutorial.backend.entity.Member;

import java.util.List;

public interface FriendService {
    Friend addNewFriend(String name, Member friend, Member me);
    List<FriendDto> getAllFriendsByMemberId(Long memberId);
}
