package com.tutorial.backend.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class FriendDto {
    private Long friendMemberId;
    private String isRegistered;
    private String nickname;
    private String friendName;
    private String friendProfileImageUrl;
}
