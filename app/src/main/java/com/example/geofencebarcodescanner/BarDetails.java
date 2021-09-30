package com.example.geofencebarcodescanner;

public class BarDetails {
    String month;
    int scans;

    public BarDetails(String month, int scans) {
        this.month = month;
        this.scans = scans;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public int getScans() {
        return scans;
    }

    public void setScans(int scans) {
        this.scans = scans;
    }
}
