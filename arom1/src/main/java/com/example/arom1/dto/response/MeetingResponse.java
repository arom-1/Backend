package com.example.arom1.dto.response;

import com.example.arom1.entity.Meeting;
import com.example.arom1.entity.Review;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class MeetingResponse {
    private long id;
    private String title;
    private LocalDateTime meetingTime;
    private int meetingMaxMember;
    private int meetingParticipatedMember;
    private long memberId;
    private long eateryId;


    @Builder
    public MeetingResponse(Long id, String title, LocalDateTime meetingTime, int meetingMaxMember, int meetingParticipatedMember, long memberId, long eateryId) {
        this.id = id;
        this.title = title;
        this.meetingTime = meetingTime;
        this.meetingMaxMember = meetingMaxMember;
        this.meetingParticipatedMember = meetingParticipatedMember;
        this.memberId = memberId;
        this.eateryId = eateryId;
    }


    public static MeetingResponse entityToDto(Meeting meeting) {
        return MeetingResponse.builder()
                .id(meeting.getId())
                .title(meeting.getTitle())
                .meetingTime(meeting.getMeetingTime())
                .meetingMaxMember(meeting.getMeetingMaxMember())
                .meetingParticipatedMember(meeting.getMeetingParticipatedMember())
                .memberId(meeting.getMemberId())
                .eateryId(meeting.getEateryId())
                .build();
    }

}
