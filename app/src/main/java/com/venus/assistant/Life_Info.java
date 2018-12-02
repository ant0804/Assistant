package com.venus.assistant;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Life_Info {
    @SerializedName("kongtiao")
    @Expose
    private List<String> kongtiao = null;
    @SerializedName("daisan")
    @Expose
    private List<String> daisan = null;
    @SerializedName("ziwaixian")
    @Expose
    private List<String> ziwaixian = null;
    @SerializedName("yundong")
    @Expose
    private List<String> yundong = null;
    @SerializedName("ganmao")
    @Expose
    private List<String> ganmao = null;
    @SerializedName("xiche")
    @Expose
    private List<String> xiche = null;
    @SerializedName("diaoyu")
    @Expose
    private List<String> diaoyu = null;
    @SerializedName("guomin")
    @Expose
    private List<String> guomin = null;
    @SerializedName("wuran")
    @Expose
    private List<String> wuran = null;
    @SerializedName("chuanyi")
    @Expose
    private List<String> chuanyi = null;

    public List<String> getKongtiao() {
        return kongtiao;
    }

    public void setKongtiao(List<String> kongtiao) {
        this.kongtiao = kongtiao;
    }

    public List<String> getDaisan() {
        return daisan;
    }

    public void setDaisan(List<String> daisan) {
        this.daisan = daisan;
    }

    public List<String> getZiwaixian() {
        return ziwaixian;
    }

    public void setZiwaixian(List<String> ziwaixian) {
        this.ziwaixian = ziwaixian;
    }

    public List<String> getYundong() {
        return yundong;
    }

    public void setYundong(List<String> yundong) {
        this.yundong = yundong;
    }

    public List<String> getGanmao() {
        return ganmao;
    }

    public void setGanmao(List<String> ganmao) {
        this.ganmao = ganmao;
    }

    public List<String> getXiche() {
        return xiche;
    }

    public void setXiche(List<String> xiche) {
        this.xiche = xiche;
    }

    public List<String> getDiaoyu() {
        return diaoyu;
    }

    public void setDiaoyu(List<String> diaoyu) {
        this.diaoyu = diaoyu;
    }

    public List<String> getGuomin() {
        return guomin;
    }

    public void setGuomin(List<String> guomin) {
        this.guomin = guomin;
    }

    public List<String> getWuran() {
        return wuran;
    }

    public void setWuran(List<String> wuran) {
        this.wuran = wuran;
    }

    public List<String> getChuanyi() {
        return chuanyi;
    }

    public void setChuanyi(List<String> chuanyi) {
        this.chuanyi = chuanyi;
    }
}
