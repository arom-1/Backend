package com.example.arom1.entity;

import com.example.arom1.dto.MeetingDto;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "Meeting")
public class Meeting extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private LocalDateTime meetingTime;

    private int meetingMaxMember;

    private int meetingParticipatedMember;

    private Long memberId;
    private Long eateryId;

    @ManyToOne(fetch = FetchType.LAZY) //식당 추가
    @JoinColumn(name = "eatery_id")
    private Eatery eatery;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "Meeting", cascade = CascadeType.REMOVE)
    private List<MeetingReply> replies = new ArrayList<>();

    @Builder
    private Meeting(Long id, String title, LocalDateTime meetingTime, int meetingMaxMember, int meetingParticipatedMember,long memberId, long eateryId) { //수정
        this.id = id;
        this.title = title;
        this.meetingTime = meetingTime;
        this.meetingMaxMember = meetingMaxMember;
        this.meetingParticipatedMember = meetingParticipatedMember;
        this.memberId= memberId;
        this.eateryId=eateryId;
    }

    public static Meeting newMeeting(MeetingDto meetingDto){
        return Meeting.builder()
                .title(meetingDto.getTitle())
                .meetingTime(meetingDto.getMeetingTime())
                .meetingMaxMember(meetingDto.getMeetingMaxMember())
                .meetingParticipatedMember(meetingDto.getMeetingParticipatedMember())
                .memberId(meetingDto.getMemberId())
                .eateryId(meetingDto.getEateryId())
                .build();
    }

    public void setTitle(String title){
        this.title = title;
    }

    public void setmeetingTime(LocalDateTime meetingTime){
        this.meetingTime = meetingTime;
    }

    public void setMeetingMaxMember(int meetingMaxMember){
        this.meetingMaxMember=meetingMaxMember;
    }

    public void setMeetingParticipatedMember(int meetingParticipatedMember){
        this.meetingParticipatedMember=meetingParticipatedMember;
    }

    public void setMemberId (long memberId){
        this.memberId = memberId;
    }

    public void setEateryId (long eateryId) {
        this.eateryId = eateryId;
    }


}