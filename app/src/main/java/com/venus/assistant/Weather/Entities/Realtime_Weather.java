package com.venus.assistant.Weather.Entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Realtime_Weather {
    @SerializedName("humidity")
    @Expose
    private String humidity;
    @SerializedName("img")
    @Expose
    private String img;
    @SerializedName("info")
    @Expose
    private String info;
    @SerializedName("temperature")
    @Expose
    private String temperature;

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }
}
