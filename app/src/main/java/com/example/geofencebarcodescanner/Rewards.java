package com.example.geofencebarcodescanner;

public class Rewards {
    private String company,offer,expiry;
    private int img;

    public Rewards(String company, String offer, String expiry, int img) {
        this.company = company;
        this.offer = offer;
        this.expiry = expiry;
        this.img = img;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getOffer() {
        return offer;
    }

    public void setOffer(String offer) {
        this.offer = offer;
    }

    public String getExpiry() {
        return expiry;
    }

    public void setExpiry(String expiry) {
        this.expiry = expiry;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }
}
