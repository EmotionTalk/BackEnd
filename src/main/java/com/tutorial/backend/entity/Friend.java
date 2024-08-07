package com.tutorial.backend.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "tbl_friend")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Friend {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne
    @JoinColumn(name = "friend_member_id", nullable = false)
    private Member friendMember;

    private String isRegistered;

    private String nickname;

    @Builder
    public Friend(Long id, Member member, Member friendMember, String isRegistered, String nickname) {
        this.id = id;
        this.member = member;
        this.friendMember = friendMember;
        this.isRegistered = isRegistered;
        this.nickname = nickname;
    }
}
