package com.venus.assistant;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class HistoryWeather {
    @SerializedName("history")
    @Expose
    private History history;

    public History getHistory() {
        return history;
    }

    public void setHistory(History history) {
        this.history = history;
    }
}
