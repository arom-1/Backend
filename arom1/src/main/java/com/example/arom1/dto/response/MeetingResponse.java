package com.example.arom1.dto.response;

import com.example.arom1.entity.Meeting;
import com.example.arom1.entity.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class MeetingResponse {
    private String title;
    private LocalDateTime meeting_time;
    private int meeting_max_member;
    private int meeting_participated_member;
    private long member_id;
    private long eatery_id;
    private long chatroom_id;

    private MemberResponse meetingOwner;


    public static MeetingResponse entityToDto(Meeting meeting, MemberResponse memberResponse, Long chatRoomId) {
        return MeetingResponse.builder()
                .title(meeting.getTitle())
                .meeting_time(meeting.getMeeting_time())
                .meeting_max_member(meeting.getMeeting_max_member())
                .meeting_participated_member(meeting.getMeeting_participated_member())
                .member_id(meeting.getMember().getId())
                .eatery_id(meeting.getEatery().getId())
                .chatroom_id(chatRoomId)
                .meetingOwner(memberResponse)
                .build();

    }

    public static MeetingResponse entityToDto(Meeting meeting, MemberResponse memberResponse) {
        return MeetingResponse.builder()
                .title(meeting.getTitle())
                .meeting_time(meeting.getMeeting_time())
                .meeting_max_member(meeting.getMeeting_max_member())
                .meeting_participated_member(meeting.getMeeting_participated_member())
                .member_id(meeting.getMember().getId())
                .eatery_id(meeting.getEatery().getId())
                .meetingOwner(memberResponse)
                .build();

    }



}
