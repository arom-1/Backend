package com.example.arom1.controller;


import com.example.arom1.common.exception.BaseException;
import com.example.arom1.common.response.BaseResponse;
import com.example.arom1.dto.request.SeoulEateryDto;
import com.example.arom1.dto.response.EateryResponse;
import com.example.arom1.service.EateryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class EateryController {
    private final EateryService eateryService;

    @GetMapping("/eatery/{index}")
    public BaseResponse<?> eateryHome(@PathVariable int index) throws IOException {
        int start = 1000 * index + 1;
        int end = 1000 * (index + 1);
        StringBuilder urlBuilder = new StringBuilder("http://openapi.seoul.go.kr:8088"); /*URL*/
        urlBuilder.append("/" +  URLEncoder.encode("4f655a6e4e696d68373174524a6944","UTF-8") ); /*인증키 (sample사용시에는 호출시 제한됩니다.)*/
        urlBuilder.append("/" +  URLEncoder.encode("json","UTF-8") ); /*요청파일타입 (xml,xmlf,xls,json) */
        urlBuilder.append("/" + URLEncoder.encode("LOCALDATA_072404","UTF-8")); /*서비스명 (대소문자 구분 필수입니다.)*/
        urlBuilder.append("/" + URLEncoder.encode(Integer.toString(start),"UTF-8")); /*요청시작위치 (sample인증키 사용시 5이내 숫자)*/
        urlBuilder.append("/" + URLEncoder.encode(Integer.toString(end),"UTF-8")); /*요청종료위치(sample인증키 사용시 5이상 숫자 선택 안 됨)*/
        List<SeoulEateryDto> list = eateryService.getApi(urlBuilder);
        return new BaseResponse<>(list);
    }

    //홈
    //유저가 설정한 주소 (__동) 기반으로 음식점 반환
    @GetMapping("/eatery")
    public BaseResponse<List<EateryResponse>> eateryHome(@RequestParam String address){
        try{
            List<EateryResponse> eateryResponseList = eateryService.searchEateryWithAddress(address);
            return new BaseResponse<>(eateryResponseList);
        }catch (BaseException e){
            return new BaseResponse<>(e.getStatus());
        }
    }

    //키워드로 음식점 검색
    @GetMapping("/eatery/search")
    public BaseResponse<List<EateryResponse>> searchEateryByKeyword(@RequestParam String keyword){
        try {
            List<EateryResponse> eateryResponseList = eateryService.searchEateryWithKeyword(keyword);
            return new BaseResponse<>(eateryResponseList);
        }catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }

    @GetMapping("/eatery/search/category/{category}")
    public BaseResponse<List<EateryResponse>> searchEateryByCategory(@PathVariable String category){
        try{
            List<EateryResponse> eateryResponseList = eateryService.searchEateryWithCategory(category);
            return new BaseResponse<>(eateryResponseList);
        }catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }

    //    유저의 현재위치를 기반으로 주변 식당 제공
    @GetMapping("/eatery/search/address")
    public BaseResponse<List<EateryResponse>> searchEateryByAddress(@RequestParam Double longitude,
                                                                    @RequestParam Double latitude){
        try{
            List<EateryResponse> eateryResponseList = eateryService.searchEateryWithLocation(longitude, latitude);
            return new BaseResponse<>(eateryResponseList);
        }catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }
}