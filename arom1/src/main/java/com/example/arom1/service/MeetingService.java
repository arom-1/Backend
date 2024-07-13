package com.example.arom1.service;

import com.example.arom1.common.exception.BaseException;
import com.example.arom1.common.response.BaseResponse;
import com.example.arom1.common.response.BaseResponseStatus;
import com.example.arom1.dto.MeetingDto;
import com.example.arom1.dto.ReviewDto;
import com.example.arom1.dto.response.MeetingResponse;
import com.example.arom1.dto.response.ReviewResponse;
import com.example.arom1.entity.Eatery;
import com.example.arom1.entity.Meeting;
import com.example.arom1.entity.Member;
import com.example.arom1.entity.Review;
import com.example.arom1.repository.EateryRepository;
import com.example.arom1.repository.MeetingRepository;
import com.example.arom1.repository.MemberRepository;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class MeetingService {

    private final MeetingRepository meetingRepository;
    private final EateryRepository eateryRepository;
    private final MemberRepository memberRepository;

    public List<Meeting> getApi(StringBuilder apiUrl) throws IOException {
        URL url = new URL(apiUrl.toString());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestProperty("Content-type", "application/json");

        int responseCode = conn.getResponseCode();
        BufferedReader reader = new BufferedReader(new InputStreamReader(responseCode >= 200 && responseCode <= 300 ? conn.getInputStream() : conn.getErrorStream()));

        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }

        Gson gson = new Gson();
        List<Meeting> meetings = gson.fromJson(sb.toString(), new TypeToken<List<Meeting>>(){}.getType());

        return meetings;
    }

    //게시글 전체 조회
    public List<MeetingResponse> findAllMeetings(){
        List<Meeting> meetings = meetingRepository.findAll();
        return meetings.stream()
                .map(MeetingResponse::entityToDto)
                .collect(Collectors.toList());
    }

    //게시글 삭제
    public void deleteMeeting(Long meetingId, Long memberId) {
        Meeting existingMeeting = meetingRepository.findById(meetingId)
                        .orElseThrow(()->new BaseException(BaseResponseStatus.INVALID_MEETING));

        //권한 확인
        if (!existingMeeting.getMember().getId().equals(memberId)) {
            throw new BaseException(BaseResponseStatus.INVALID_MEMBER);
        }

        meetingRepository.delete(existingMeeting);
    }

    //게시글 수정
    public Meeting updateMeeting(Long meetingId, Long memberId, MeetingDto meetingDto) {
        Meeting existingMeeting = meetingRepository.findById(meetingId)
                .orElseThrow(()->new BaseException(BaseResponseStatus.INVALID_MEETING));

        //권한 확인
        if (!existingMeeting.getMember().getId().equals(memberId)) {
            throw new BaseException(BaseResponseStatus.INVALID_MEMBER);
        }

        existingMeeting.setTitle(meetingDto.getTitle());
        existingMeeting.setmeetingTime(meetingDto.getMeetingTime());
        existingMeeting.setMeetingMaxMember(meetingDto.getMeetingMaxMember());
        existingMeeting.setMeetingParticipatedMember(meetingDto.getMeetingParticipatedMember());

        Eatery eatery = eateryRepository.findById(meetingDto.getEateryId())
                .orElseThrow(()->new BaseException(BaseResponseStatus.INVALID_EATERY));
        existingMeeting.setEateryId(meetingDto.getEateryId());

        Member member = memberRepository.findById(meetingDto.getMemberId())
                .orElseThrow(()->new BaseException(BaseResponseStatus.INVALID_MEMBER));
        existingMeeting.setMemberId(meetingDto.getMemberId());

        return meetingRepository.save(existingMeeting);
    }

    // 글 상세보기 기능
    public MeetingResponse getMeetingById(Long meetingId) {
        Meeting meeting = meetingRepository.findById(meetingId)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.INVALID_MEETING));

        return MeetingResponse.entityToDto(meeting);
    }


    //글 작성 기능
    public List<MeetingResponse> saveMeeting(Long eateryId, Long OwnerId, MeetingDto meetingdto) {
        Eatery eatery = eateryRepository.findById(eateryId)
                .orElseThrow(()->new BaseException(BaseResponseStatus.INVALID_EATERY));

        Member member = memberRepository.findById(OwnerId)
                .orElseThrow(()->new BaseException(BaseResponseStatus.INVALID_MEMBER));

        Meeting meeting = Meeting.newMeeting(meetingdto);
        meetingRepository.save(meeting);

        List<Meeting> meetings = meetingRepository.findByEateryId(eateryId);
        return meetings.stream()
                .map(MeetingResponse::entityToDto)
                .collect(Collectors.toList());

    }


}