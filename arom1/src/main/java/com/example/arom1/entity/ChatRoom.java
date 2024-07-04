package com.example.arom1.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "chatroom")
public class ChatRoom extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String chatRoomName;

    private int totalMembers = 0;

    private int participants = 0;

    @JsonBackReference
    @OneToMany(mappedBy = "chatroom", cascade = CascadeType.ALL)
    private List<ChatRoomMember> chatroomMemberList = new ArrayList<>();

    @Builder
    private ChatRoom(String name) {
        this.chatRoomName = name;
    }

    public static ChatRoom createChatRoom(String name) {
        return ChatRoom.builder()
                .name(name)
                .build();
    }

    public static ChatRoom updateChatRoom(String name) {
        return ChatRoom.builder()
                .name(name)
                .build();
    }
}