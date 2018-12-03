package com.venus.assistant.Weather.Entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Pm25 {
    @SerializedName("so2")
    @Expose
    private Integer so2;
    @SerializedName("o3")
    @Expose
    private Integer o3;
    @SerializedName("co")
    @Expose
    private String co;
    @SerializedName("level")
    @Expose
    private Integer level;
    @SerializedName("color")
    @Expose
    private String color;
    @SerializedName("no2")
    @Expose
    private Integer no2;
    @SerializedName("aqi")
    @Expose
    private Integer aqi;
    @SerializedName("quality")
    @Expose
    private String quality;
    @SerializedName("pm10")
    @Expose
    private Integer pm10;
    @SerializedName("pm25")
    @Expose
    private Integer pm25;
    @SerializedName("advice")
    @Expose
    private String advice;
    @SerializedName("chief")
    @Expose
    private String chief;
    @SerializedName("upDateTime")
    @Expose
    private long upDateTime;

    public Integer getSo2() {
        return so2;
    }

    public void setSo2(Integer so2) {
        this.so2 = so2;
    }

    public Integer getO3() {
        return o3;
    }

    public void setO3(Integer o3) {
        this.o3 = o3;
    }

    public String getCo() {
        return co;
    }

    public void setCo(String co) {
        this.co = co;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Integer getNo2() {
        return no2;
    }

    public void setNo2(Integer no2) {
        this.no2 = no2;
    }

    public Integer getAqi() {
        return aqi;
    }

    public void setAqi(Integer aqi) {
        this.aqi = aqi;
    }

    public String getQuality() {
        return quality;
    }

    public void setQuality(String quality) {
        this.quality = quality;
    }

    public Integer getPm10() {
        return pm10;
    }

    public void setPm10(Integer pm10) {
        this.pm10 = pm10;
    }

    public Integer getPm25() {
        return pm25;
    }

    public void setPm25(Integer pm25) {
        this.pm25 = pm25;
    }

    public String getAdvice() {
        return advice;
    }

    public void setAdvice(String advice) {
        this.advice = advice;
    }

    public String getChief() {
        return chief;
    }

    public void setChief(String chief) {
        this.chief = chief;
    }

    public long getUpDateTime() {
        return upDateTime;
    }

    public void setUpDateTime(Integer upDateTime) {
        this.upDateTime = upDateTime;
    }
}
