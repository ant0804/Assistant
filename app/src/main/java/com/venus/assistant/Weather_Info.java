package com.venus.assistant;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Weather_Info {
    @SerializedName("night")
    @Expose
    private List<String> night = null;
    @SerializedName("day")
    @Expose
    private List<String> day = null;

    public List<String> getNight() {
        return night;
    }

    public void setNight(List<String> night) {
        this.night = night;
    }

    public List<String> getDay() {
        return day;
    }

    public void setDay(List<String> day) {
        this.day = day;
    }
}
