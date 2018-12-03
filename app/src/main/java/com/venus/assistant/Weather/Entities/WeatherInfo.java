package com.venus.assistant.Weather.Entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class WeatherInfo {
    @SerializedName("historyWeather")
    @Expose
    private HistoryWeather historyWeather;
    @SerializedName("area")
    @Expose
    private List<List<String>> area = null;
    @SerializedName("life")
    @Expose
    private Life life;
    @SerializedName("realtime")
    @Expose
    private Realtime realtime;
    @SerializedName("alert")
    @Expose
    private List<Alert> alert = null;
    @SerializedName("trafficalert")
    @Expose
    private List<Object> trafficalert = null;
    @SerializedName("weather")
    @Expose
    private List<Weather> weather = null;
    @SerializedName("pm25")
    @Expose
    private Pm25 pm25;
    @SerializedName("hourly_forecast")
    @Expose
    private List<HourlyForecast> hourlyForecast = null;

    public HistoryWeather getHistoryWeather() {
        return historyWeather;
    }

    public void setHistoryWeather(HistoryWeather historyWeather) {
        this.historyWeather = historyWeather;
    }

    public List<List<String>> getArea() {
        return area;
    }

    public void setArea(List<List<String>> area) {
        this.area = area;
    }

    public Life getLife() {
        return life;
    }

    public void setLife(Life life) {
        this.life = life;
    }

    public Realtime getRealtime() {
        return realtime;
    }

    public void setRealtime(Realtime realtime) {
        this.realtime = realtime;
    }

    public List<Alert> getAlert() {
        return alert;
    }

    public void setAlert(List<Alert> alert) {
        this.alert = alert;
    }

    public List<Object> getTrafficalert() {
        return trafficalert;
    }

    public void setTrafficalert(List<Object> trafficalert) {
        this.trafficalert = trafficalert;
    }

    public List<Weather> getWeather() {
        return weather;
    }

    public void setWeather(List<Weather> weather) {
        this.weather = weather;
    }

    public Pm25 getPm25() {
        return pm25;
    }

    public void setPm25(Pm25 pm25) {
        this.pm25 = pm25;
    }

    public List<HourlyForecast> getHourlyForecast() {
        return hourlyForecast;
    }

    public void setHourlyForecast(List<HourlyForecast> hourlyForecast) {
        this.hourlyForecast = hourlyForecast;
    }
}

