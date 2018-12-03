package com.venus.assistant.Weather.Entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Weather {
    @SerializedName("aqi")
    @Expose
    private String aqi;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("info")
    @Expose
    private Weather_Info info;

    public String getAqi() {
        return aqi;
    }

    public void setAqi(String aqi) {
        this.aqi = aqi;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Weather_Info getInfo() {
        return info;
    }

    public void setInfo(Weather_Info info) {
        this.info = info;
    }
}
