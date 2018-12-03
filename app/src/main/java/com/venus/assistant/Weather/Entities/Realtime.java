package com.venus.assistant.Weather.Entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Realtime {
    @SerializedName("mslp")
    @Expose
    private String mslp;
    @SerializedName("wind")
    @Expose
    private Wind wind;
    @SerializedName("time")
    @Expose
    private String time;
    @SerializedName("pressure")
    @Expose
    private String pressure;
    @SerializedName("weather")
    @Expose
    private Realtime_Weather weather;
    @SerializedName("feelslike_c")
    @Expose
    private String feelslikeC;
    @SerializedName("dataUptime")
    @Expose
    private String dataUptime;
    @SerializedName("date")
    @Expose
    private String date;

    public String getMslp() {
        return mslp;
    }

    public void setMslp(String mslp) {
        this.mslp = mslp;
    }

    public Wind getWind() {
        return wind;
    }

    public void setWind(Wind wind) {
        this.wind = wind;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getPressure() {
        return pressure;
    }

    public void setPressure(String pressure) {
        this.pressure = pressure;
    }

    public Realtime_Weather getWeather() {
        return weather;
    }

    public void setWeather(Realtime_Weather weather) {
        this.weather = weather;
    }

    public String getFeelslikeC() {
        return feelslikeC;
    }

    public void setFeelslikeC(String feelslikeC) {
        this.feelslikeC = feelslikeC;
    }

    public String getDataUptime() {
        return dataUptime;
    }

    public void setDataUptime(String dataUptime) {
        this.dataUptime = dataUptime;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
