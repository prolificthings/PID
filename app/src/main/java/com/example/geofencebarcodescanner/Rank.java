package com.example.geofencebarcodescanner;

public class Rank {
    public int rankNo;
    public String name, scans;

    public Rank(int rankNo, String name, String scans) {
        this.rankNo = rankNo;
        this.name = name;
        this.scans = scans;
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
