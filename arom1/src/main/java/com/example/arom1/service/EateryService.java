package com.example.arom1.service;

import com.example.arom1.common.exception.BaseException;
import com.example.arom1.common.response.BaseResponseStatus;
import com.example.arom1.dto.request.SeoulEateryDto;
import com.example.arom1.dto.response.EateryResponse;
import com.example.arom1.entity.Eatery;
import com.example.arom1.repository.EateryRepository;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class EateryService {
    private final EateryRepository eateryRepository;

    public List<SeoulEateryDto> getApi(StringBuilder apiUrl) throws IOException {
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
        String jsonString = sb.toString();
        Gson gson = new Gson();
        JsonObject jsonObject = gson.fromJson(jsonString, JsonObject.class);

        JsonArray seoulEateryArray = jsonObject.getAsJsonObject("LOCALDATA_072404").getAsJsonArray("row");

        List<SeoulEateryDto> seoulEateryDtoList = new ArrayList<>();
        for (JsonElement element : seoulEateryArray) {
            JsonObject rowObject = element.getAsJsonObject();
            SeoulEateryDto dto = gson.fromJson(rowObject, SeoulEateryDto.class);
            if(dto.getTrdStateNm().equals("폐업")) continue;
            eateryRepository.save(Eatery.dtoToEntity(dto));
            seoulEateryDtoList.add(dto);
            //X, Y : 중부원점TM(EPSG:2097) 좌표계
        }
        reader.close();
        conn.disconnect();
        return seoulEateryDtoList;
    }

    public List<EateryResponse> searchEatery(String keyword){
        List<EateryResponse> seoulEateryByKeyword = eateryRepository.findByNameContaining(keyword).stream().map(EateryResponse::entityToDto).toList();
        if(seoulEateryByKeyword.isEmpty())
            throw new BaseException(BaseResponseStatus.NO_EATERY_BY_KEYWORD);
        return seoulEateryByKeyword;
    }

    public List<EateryResponse> eateryHomeWithAddress(String address){
        List<EateryResponse> seoulEateryByKeyword = eateryRepository.findByNameContaining(address).stream().map(EateryResponse::entityToDto).toList();
        if(seoulEateryByKeyword.isEmpty())
            throw new BaseException(BaseResponseStatus.NO_EATERY_BY_KEYWORD);
        return seoulEateryByKeyword;
    }

}