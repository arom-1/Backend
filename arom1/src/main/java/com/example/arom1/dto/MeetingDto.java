package com.example.arom1.dto;

import com.example.arom1.entity.Eatery;
import com.example.arom1.entity.Meeting;
import com.example.arom1.entity.Member;
import com.example.arom1.entity.Review;
import lombok.Builder;
import lombok.Getter;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;

@Getter
public class MeetingDto {
    private long id;
    private String title;
    private LocalDateTime meetingTime;
    private int meetingMaxMember;
    private int meetingParticipatedMember;
    private long memberId;
    private long eateryId;


    @Builder
    public MeetingDto(String title, LocalDateTime meetingTime, int meetingMaxMember, int meetingParticipatedMember, long memberId, long eateryId){
        this.title=title;
        this.meetingTime=meetingTime;
        this.meetingMaxMember=meetingMaxMember;
        this.meetingParticipatedMember=meetingParticipatedMember;
        this.memberId=memberId;
        this.eateryId=eateryId;
    }

    public static MeetingDto CreatemeetingDto(Meeting meeting) {
        return MeetingDto.builder()
                .title(meeting.getTitle())
                .meetingTime(meeting.getMeetingTime())
                .meetingMaxMember(meeting.getMeetingMaxMember())
                .meetingParticipatedMember(meeting.getMeetingParticipatedMember())
                .memberId(meeting.getMemberId())
                .eateryId(meeting.getEateryId())
                .build();
    }


}
