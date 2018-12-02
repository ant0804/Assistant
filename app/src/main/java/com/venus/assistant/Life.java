package com.venus.assistant;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Life {
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("info")
    @Expose
    private Life_Info info;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Life_Info getInfo() {
        return info;
    }

    public void setInfo(Life_Info info) {
        this.info = info;
    }
}
