package com.example.arom1.service;

import com.example.arom1.common.exception.BaseException;
import com.example.arom1.common.response.BaseResponse;
import com.example.arom1.common.response.BaseResponseStatus;
import com.example.arom1.dto.ReviewDto;
import com.example.arom1.dto.response.ReviewResponse;
import com.example.arom1.entity.Eatery;
import com.example.arom1.entity.Member;
import com.example.arom1.entity.Review;
import com.example.arom1.repository.EateryRepository;
import com.example.arom1.repository.MemberRepository;
import com.example.arom1.repository.ReviewRepository;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final EateryRepository eateryRepository;
    private final MemberRepository memberRepository;

    public List<Review> getApi(StringBuilder apiUrl) throws IOException {
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
        List<Review> reviews = gson.fromJson(sb.toString(), new TypeToken<List<Review>>(){}.getType());

        return reviews;
    }

    public List<Review> findByEateryId(Long eateryId) {
        return reviewRepository.findByEateryId(eateryId);
    }


    //리뷰 수정 기능
    public Review updateReview(Long reviewId, Long memberId, ReviewDto reviewDto) {
        Review existingReview = reviewRepository.findById(reviewId)
                .orElseThrow(()-> new BaseException(BaseResponseStatus.NO_REVIEW_EXIST));

        //권한 확인
        if (!existingReview.getMember().getId().equals(memberId)) {
            throw new BaseException(BaseResponseStatus.INVALID_MEMBER);
        }

        existingReview.setContent(reviewDto.getContent());
        existingReview.setRating(reviewDto.getRating());
        existingReview.setViews(reviewDto.getViews());
        existingReview.setLikes(reviewDto.getLikes());
        existingReview.setDislikes(reviewDto.getDislikes());

        return reviewRepository.save(existingReview);
    }
    
    //리뷰 삭제
    public void deleteReview(Long reviewId, Long memberId) {
        Review existingReview = reviewRepository.findById(reviewId)
                .orElseThrow(()-> new BaseException(BaseResponseStatus.NO_REVIEW_EXIST));

        //권한 확인
        if (!existingReview.getMember().getId().equals(memberId)) {
            throw new BaseException(BaseResponseStatus.INVALID_MEMBER);
        }
        reviewRepository.delete(existingReview);
    }

    //리뷰 조회
    public List<ReviewResponse> findAllReviews(){
        // 모든 리뷰를 조회하여 ReviewResponse DTO로 변환
        List<Review> reviews = reviewRepository.findAll();
        return reviews.stream()
                .map(ReviewResponse::entityToDto)
                .collect(Collectors.toList());
    }

    //리뷰 작성
    public List<ReviewResponse> saveReview(Long eateryId, Long memberId, ReviewDto reviewdto) {
        Eatery eatery = eateryRepository.findById(eateryId)
                .orElseThrow(()->new BaseException(BaseResponseStatus.INVALID_EATERY));

        Member member = memberRepository.findById(memberId)
                .orElseThrow(()->new BaseException(BaseResponseStatus.INVALID_MEMBER));

        Review review = Review.newReview(reviewdto);
        reviewRepository.save(review);

        List<Review> reviews = reviewRepository.findByEateryId(eateryId);
        return reviews.stream()
                .map(ReviewResponse::entityToDto)
                .collect(Collectors.toList());
    }
}
