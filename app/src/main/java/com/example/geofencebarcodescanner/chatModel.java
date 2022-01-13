package com.example.geofencebarcodescanner;

public class chatModel {
    private String userName, chatBody;
    private String chatDate, chatTime;

    public chatModel() {
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getChatBody() {
        return chatBody;
    }

    public void setChatBody(String chatBody) {
        this.chatBody = chatBody;
    }

    public String getChatDate() {
        return chatDate;
    }

    public void setChatDate(String chatDate) {
        this.chatDate = chatDate;
    }

    public String getChatTime() {
        return chatTime;
    }

    public void setChatTime(String chatTime) {
        this.chatTime = chatTime;
    }
}
