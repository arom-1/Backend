package com.example.arom1.service;

import com.example.arom1.common.exception.BaseException;
import com.example.arom1.common.geo.CoordinateConverter;
import com.example.arom1.common.response.BaseResponseStatus;
import com.example.arom1.dto.request.SeoulEateryDto;
import com.example.arom1.dto.response.EateryResponse;
import com.example.arom1.entity.Eatery;
import com.example.arom1.entity.EateryLocation;
import com.example.arom1.repository.EateryLocationRepository;
import com.example.arom1.repository.EateryRepository;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.locationtech.jts.geom.Point;
import org.locationtech.proj4j.ProjCoordinate;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class EateryService {
    private final EateryRepository eateryRepository;
    private final CoordinateConverter coordinateConverter;
    private final EateryLocationRepository eateryLocationRepository;

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
            if(dto.getX().isBlank() && dto.getY().isBlank()) continue;
            if(dto.getTrdStateNm().equals("폐업")) continue;
            if(dto.getUptaeNm().equals("경양식"))
                dto.setUptaeNm("양식");
            else if(dto.getUptaeNm().equals("중국식"))
                dto.setUptaeNm("중식");
            else if(!(dto.getUptaeNm().equals("한식")
                    || dto.getUptaeNm().equals("분식")
                    || dto.getUptaeNm().equals("일식"))){
                dto.setUptaeNm("기타");
            }
            ProjCoordinate tmp = coordinateConverter.transform((Double.parseDouble(dto.getX())), Double.parseDouble(dto.getY()));
            dto.setX(String.valueOf(tmp.x));
            dto.setY(String.valueOf(tmp.y));
            eateryRepository.save(Eatery.dtoToEntity(dto));
            seoulEateryDtoList.add(dto);
            //X, Y : 중부원점TM(EPSG:2097) 좌표계
        }
        reader.close();
        conn.disconnect();
        return seoulEateryDtoList;
    }

    public List<EateryResponse> searchEateryWithAddress(String address) {
        List<EateryResponse> seoulEateryByKeyword = eateryRepository.findBySiteWhlAddrContaining(address)
                .stream()
                .map(eatery -> EateryResponse.entityToDto(eatery, null))
                .toList();
        if (seoulEateryByKeyword.isEmpty())
            throw new BaseException(BaseResponseStatus.NO_EATERY_BY_KEYWORD);
        return seoulEateryByKeyword;
    }

    public List<EateryResponse> searchEateryWithKeyword(String keyword) {
        List<EateryResponse> seoulEateryByKeyword = eateryRepository.findByNameContaining(keyword)
                .stream()
                .map(eatery -> EateryResponse.entityToDto(eatery, null))
                .toList();
        if (seoulEateryByKeyword.isEmpty())
            throw new BaseException(BaseResponseStatus.NO_EATERY_BY_KEYWORD);
        return seoulEateryByKeyword;
    }


    public List<EateryResponse> searchEateryWithLocation(Double longitude,
                                                         Double latitude) {

        Point point = EateryLocation.newEateryLocation(longitude, latitude);
        List<EateryResponse> eateryLocations = eateryLocationRepository.findLocationsWithinDistance(point,1000)
                .stream()
                .map(location -> EateryResponse.entityToDto(location.getEatery(), point))
                .toList();
        if(eateryLocations.isEmpty())
            throw new BaseException(BaseResponseStatus.NO_EATERY_BY_ADDRESS);
        return eateryLocations;
    }

    public List<EateryResponse> searchEateryWithCategory(String category) {
        List<EateryResponse> seoulEateryByCategory = eateryRepository.findByUptaeNm(category)
                .stream()
                .map(eatery -> EateryResponse.entityToDto(eatery, null))
                .toList();
        if (seoulEateryByCategory.isEmpty())
            throw new BaseException(BaseResponseStatus.NO_EATERY_BY_KEYWORD);
        return seoulEateryByCategory;
    }
}