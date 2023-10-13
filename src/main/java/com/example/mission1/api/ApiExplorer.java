package com.example.mission1.api;

import com.example.mission1.model.WifiBean;
import com.example.mission1.model.WifiDao;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ApiExplorer {
    public static void apiData() {
        OkHttpClient client = new OkHttpClient();
        Gson gson = new Gson();
        WifiDao wifiDao = new WifiDao();

        String url = "http://openapi.seoul.go.kr:8088/KEY/json/TbPublicWifiInfo/";
        int startIdx;
        int endIdx;

        for (int i = 0; i < 24; i++) {
            startIdx = i * 1000 + 1;
            endIdx = i * 1000 + 1000;
            Request request = new Request.Builder()
                    .url(url + startIdx + "/" + endIdx)
                    .build();
            try {
                Response response = client.newCall(request).execute();
                JsonObject jsonObject = gson.fromJson(response.body().string(), JsonObject.class);
                JsonArray jsonElements = jsonObject.getAsJsonObject("TbPublicWifiInfo")
                        .getAsJsonArray("row");
                for (JsonElement jsonElement : jsonElements) {
                    String wifiData = gson.toJson(jsonElement.getAsJsonObject());
                    WifiBean wifiBean = gson.fromJson(wifiData, WifiBean.class);
                    wifiDao.insertWifi(wifiBean);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
