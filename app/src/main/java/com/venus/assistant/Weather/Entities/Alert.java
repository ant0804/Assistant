package com.venus.assistant.Weather.Entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Alert {
    @SerializedName("content")
    @Expose
    private String content;
    @SerializedName("pubTime")
    @Expose
    private String pubTime;
    @SerializedName("originUrl")
    @Expose
    private String originUrl;
    @SerializedName("alarmTp2")
    @Expose
    private String alarmTp2;
    @SerializedName("alarmTp1")
    @Expose
    private String alarmTp1;
    @SerializedName("type")
    @Expose
    private Integer type;
    @SerializedName("alarmPic2")
    @Expose
    private String alarmPic2;
    @SerializedName("alarmPic1")
    @Expose
    private String alarmPic1;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPubTime() {
        return pubTime;
    }

    public void setPubTime(String pubTime) {
        this.pubTime = pubTime;
    }

    public String getOriginUrl() {
        return originUrl;
    }

    public void setOriginUrl(String originUrl) {
        this.originUrl = originUrl;
    }

    public String getAlarmTp2() {
        return alarmTp2;
    }

    public void setAlarmTp2(String alarmTp2) {
        this.alarmTp2 = alarmTp2;
    }

    public String getAlarmTp1() {
        return alarmTp1;
    }

    public void setAlarmTp1(String alarmTp1) {
        this.alarmTp1 = alarmTp1;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getAlarmPic2() {
        return alarmPic2;
    }

    public void setAlarmPic2(String alarmPic2) {
        this.alarmPic2 = alarmPic2;
    }

    public String getAlarmPic1() {
        return alarmPic1;
    }

    public void setAlarmPic1(String alarmPic1) {
        this.alarmPic1 = alarmPic1;
    }
}
