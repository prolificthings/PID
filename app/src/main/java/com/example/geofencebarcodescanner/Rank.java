package com.example.geofencebarcodescanner;

public class Rank {

    public int rankNo;
    public String name, scans, img;

    public Rank(int rankNo, String name, String scans, String img) {
        this.rankNo = rankNo;
        this.name = name;
        this.scans = scans;
        this.img = img;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public int getRankNo() {
        return rankNo;
    }

    public void setRankNo(int rankNo) {
        this.rankNo = rankNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getScans() {
        return scans;
    }

    public void setScans(String scans) {
        this.scans = scans;
    }
}
