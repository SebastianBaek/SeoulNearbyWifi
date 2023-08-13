package com.example.mission1.model;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class WifiBean {
    @SerializedName("X_SWIFI_MGR_NO")
    private String number;
    @SerializedName("X_SWIFI_WRDOFC")
    private String gu;
    @SerializedName("X_SWIFI_MAIN_NM")
    private String name;
    @SerializedName("X_SWIFI_ADRES1")
    private String doroAddress;
    @SerializedName("X_SWIFI_ADRES2")
    private String detailAddress;
    @SerializedName("X_SWIFI_INSTL_FLOOR")
    private String floor;
    @SerializedName("X_SWIFI_INSTL_TY")
    private String type;
    @SerializedName("X_SWIFI_INSTL_MBY")
    private String agency;
    @SerializedName("X_SWIFI_SVC_SE")
    private String service;
    @SerializedName("X_SWIFI_CMCWR")
    private String net;
    @SerializedName("X_SWIFI_CNSTC_YEAR")
    private String installYear;
    @SerializedName("X_SWIFI_INOUT_DOOR")
    private String inOut;
    @SerializedName("X_SWIFI_REMARS3")
    private String access;
    @SerializedName("LAT")
    private double x;
    @SerializedName("LNT")
    private double y;
    @SerializedName("WORK_DTTM")
    private String recentWork;
}
