package com.example.arom1.repository;

import com.example.arom1.entity.ChatRoom;
import com.example.arom1.entity.Meeting;
import com.example.arom1.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
    //Id로 채팅방 찾기
    @NonNull
    Optional<ChatRoom> findById(@NonNull Long id);

    List<ChatRoom> findByMeetingId(Long meetingId);
}
