package com.example.arom1.controller;


import com.example.arom1.common.response.BaseResponse;
import com.example.arom1.dto.SeoulEateryDto;
import com.example.arom1.service.EateryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class EateryController {
    private final EateryService eateryService;

    @GetMapping("/seoul/{index}")
    public BaseResponse<?> eateryHome(@PathVariable int index) throws IOException {
        int start = 1000 * index + 1;
        int end = 1000 * (index + 1);
        StringBuilder urlBuilder = new StringBuilder("http://openapi.seoul.go.kr:8088"); /*URL*/
        urlBuilder.append("/" +  URLEncoder.encode("${API_KEY}","UTF-8") ); /*인증키 (sample사용시에는 호출시 제한됩니다.)*/
        urlBuilder.append("/" +  URLEncoder.encode("json","UTF-8") ); /*요청파일타입 (xml,xmlf,xls,json) */
        urlBuilder.append("/" + URLEncoder.encode("LOCALDATA_072404","UTF-8")); /*서비스명 (대소문자 구분 필수입니다.)*/
        urlBuilder.append("/" + URLEncoder.encode(Integer.toString(start),"UTF-8")); /*요청시작위치 (sample인증키 사용시 5이내 숫자)*/
        urlBuilder.append("/" + URLEncoder.encode(Integer.toString(end),"UTF-8")); /*요청종료위치(sample인증키 사용시 5이상 숫자 선택 안 됨)*/
        List<SeoulEateryDto> list = eateryService.getApi(urlBuilder);
        return new BaseResponse<>(list);
    }
}