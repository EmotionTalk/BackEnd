package com.tutorial.backend.service.friend;

import com.tutorial.backend.controller.dto.FriendDto;
import com.tutorial.backend.entity.Friend;
import com.tutorial.backend.entity.Member;
import com.tutorial.backend.repository.friend.FriendRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class FriendServiceImpl implements FriendService {

    private final FriendRepository friendRepository;

    @Override
    public Friend addNewFriend(String name, Member friend, Member me) {

        return friendRepository.save(Friend.builder()
                .nickname(name)
                .isRegistered("true")
                .member(me)
                .friendMember(friend)
                .build());
    }

    @Override
    public List<FriendDto> getAllFriendsByMemberId(Long memberId) {

        return friendRepository.findByMemberId(memberId)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private FriendDto convertToDTO(Friend friend) {
        return new FriendDto(
                friend.getFriendMember().getId(),
                friend.getIsRegistered(),
                friend.getNickname(),
                friend.getFriendMember().getMemberName(),
                friend.getFriendMember().getProfileImageUrl()
        );
    }
}
