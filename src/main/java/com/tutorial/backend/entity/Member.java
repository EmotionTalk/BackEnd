package com.tutorial.backend.entity;

import com.tutorial.backend.entity.type.Authority;
import com.tutorial.backend.entity.type.StatusType;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "tbl_member")
@ToString
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String memberEmail;

    private String memberPhone;

    private String memberName;

    private String profileImageUrl;

    @Enumerated(EnumType.STRING)
    private StatusType status;

    @Enumerated(EnumType.STRING)
    private Authority authority;

    @OneToMany(mappedBy = "participant1")
    private List<ChatRoom> chatRoomsAsParticipant1;

    @OneToMany(mappedBy = "participant2")
    private List<ChatRoom> chatRoomsAsParticipant2;

    @OneToMany(mappedBy = "member")
    private List<Friend> friends;

    @OneToMany(mappedBy = "member")
    private List<Friend> addedFriends;

    @Builder
    public Member(Long id, String memberEmail, String memberPhone, String memberName, String profileImageUrl, StatusType status, Authority authority, List<ChatRoom> chatRoomsAsParticipant1, List<ChatRoom> chatRoomsAsParticipant2, List<Friend> friends, List<Friend> addedFriends) {
        this.id = id;
        this.memberEmail = memberEmail;
        this.memberPhone = memberPhone;
        this.memberName = memberName;
        this.profileImageUrl = profileImageUrl;
        this.status = status;
        this.authority = authority;
        this.chatRoomsAsParticipant1 = chatRoomsAsParticipant1;
        this.chatRoomsAsParticipant2 = chatRoomsAsParticipant2;
        this.friends = friends;
        this.addedFriends = addedFriends;
    }


    public Member update(String memberName, String memberPhoneNumber, String memberEmail, String profileImageUrl){
        this.setMemberName(memberName);
        this.setMemberPhone(memberPhoneNumber);
        this.setMemberEmail(memberEmail);
        this.setProfileImageUrl(profileImageUrl);
        return this;
    }

}
