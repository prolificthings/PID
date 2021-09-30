package com.example.geofencebarcodescanner;

public class Competitor {
private String rank,name,scan;

    public Competitor(String rank, String name, String scan) {
        this.rank = rank;
        this.name = name;
        this.scan = scan;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getScan() {
        return scan;
    }

    public void setScan(String scan) {
        this.scan = scan;
    }
}
