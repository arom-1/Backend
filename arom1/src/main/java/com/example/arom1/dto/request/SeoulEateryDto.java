package com.example.arom1.dto.request;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SeoulEateryDto {

    @SerializedName("TRDSTATEGBN")
    private String trdStateGbn;

    @SerializedName("TRDSTATENM")
    private String trdStateNm;

    @SerializedName("SITETEL")
    private String telephone;

    @SerializedName("SITEWHLADDR")
    private String siteWhlAddr;

    @SerializedName("RDNWHLADDR")
    private String rdnWhlAddr;

    @SerializedName("RDNPOSTNO")
    private String rdnPostNo;

    @SerializedName("BPLCNM")
    private String name;

    @SerializedName("LASTMODTS")
    private String lastModTs;

    @SerializedName("UPDATEGBN")
    private String updateGbn;

    @SerializedName("UPDATEDT")
    private String updateDt;

    @SerializedName("UPTAENM")
    private String uptaeNm;

    @SerializedName("X")
    private String x;

    @SerializedName("Y")
    private String y;
}