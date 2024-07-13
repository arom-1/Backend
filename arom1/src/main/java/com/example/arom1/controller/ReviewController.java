package com.example.arom1.controller;

import com.example.arom1.common.exception.BaseException;
import com.example.arom1.common.response.BaseResponse;
import com.example.arom1.dto.ReviewDto;
import com.example.arom1.dto.request.SeoulEateryDto;
import com.example.arom1.dto.response.EateryResponse;
import com.example.arom1.dto.response.ReviewResponse;
import com.example.arom1.entity.Eatery;
import com.example.arom1.entity.Review;
import com.example.arom1.repository.ReviewRepository;
import com.example.arom1.service.ReviewService;
import com.fasterxml.jackson.databind.ser.Serializers;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/eatery/{index}/review")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;


    @GetMapping
    public BaseResponse<?> getReviews(@PathVariable int index) throws IOException {
        int start = 1000 * index + 1;
        int end = 1000 * (index + 1);
        StringBuilder urlBuilder = new StringBuilder("http://openapi.seoul.go.kr:8088"); /*URL*/
        urlBuilder.append("/" +  URLEncoder.encode("4f655a6e4e696d68373174524a6944","UTF-8") ); /*인증키 (sample사용시에는 호출시 제한됩니다.)*/
        urlBuilder.append("/" +  URLEncoder.encode("json","UTF-8") ); /*요청파일타입 (xml,xmlf,xls,json) */
        urlBuilder.append("/" + URLEncoder.encode("LOCALDATA_072404","UTF-8")); /*서비스명 (대소문자 구분 필수입니다.)*/
        urlBuilder.append("/" + URLEncoder.encode(Integer.toString(start),"UTF-8")); /*요청시작위치 (sample인증키 사용시 5이내 숫자)*/
        urlBuilder.append("/" + URLEncoder.encode(Integer.toString(end),"UTF-8")); /*요청종료위치(sample인증키 사용시 5이상 숫자 선택 안 됨)*/
        List<Review> list = reviewService.getApi(urlBuilder);

        List<ReviewResponse> reviewResponseList = list.stream() //리뷰 목록 조회
                .map(ReviewResponse::entityToDto)
                .collect(Collectors.toList());

        return new BaseResponse<>(reviewResponseList);
    }

    //작성
    @PostMapping
    public BaseResponse<?> saveReview(@RequestParam Long eateryId, @RequestParam Long memberId, @RequestBody ReviewDto reviewdto){
        try{
            List<ReviewResponse> reviewResponseList = reviewService.saveReview(eateryId, memberId, reviewdto);
            return new BaseResponse<>(reviewResponseList);
        }catch (BaseException e){
            return new BaseResponse<>(e.getStatus());
        }
    }

    //수정
    @PutMapping("{reviewId}")
    public BaseResponse<?> updateReview(@PathVariable Long reviewId, @RequestParam Long memberId, @RequestBody ReviewDto reviewdto ){
        try{
            Review updatedReview = reviewService.updateReview(reviewId, memberId, reviewdto);
            return new BaseResponse<>(updatedReview);
        }
        catch (BaseException e){
            return new BaseResponse<>(e.getStatus());
        }
    }

    //삭제
    @DeleteMapping("{reviewId}")
    public BaseResponse<?> deleteReview(@PathVariable Long reviewId,@RequestParam Long memberId){
        try{
            reviewService.deleteReview(reviewId, memberId);
            return new BaseResponse<>(HttpStatus.NO_CONTENT.value());
        }
        catch (BaseException e){
            return new BaseResponse<>(e.getStatus());
        }
    }

}
