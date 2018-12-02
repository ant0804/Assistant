package com.venus.assistant;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class HourlyForecast {
    @SerializedName("img")
    @Expose
    private String img;
    @SerializedName("wind_speed")
    @Expose
    private String windSpeed;
    @SerializedName("hour")
    @Expose
    private String hour;
    @SerializedName("wind_direct")
    @Expose
    private String windDirect;
    @SerializedName("temperature")
    @Expose
    private String temperature;
    @SerializedName("info")
    @Expose
    private String info;

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(String windSpeed) {
        this.windSpeed = windSpeed;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public String getWindDirect() {
        return windDirect;
    }

    public void setWindDirect(String windDirect) {
        this.windDirect = windDirect;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
