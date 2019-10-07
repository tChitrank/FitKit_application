package com.example.hp.fitkit;

public class FriendlyMessages {
    private String text;
    private String name;
    private String photoUrl;
    private String senderid;
    private String uid;
    private String time;
    private String device_token;

    public FriendlyMessages() {
    }

    public FriendlyMessages(String senderid,String text, String name, String photoUrl,String uid,String time,String device_token) {
        this.senderid = senderid;
        this.text = text;
        this.name = name;
        this.photoUrl = photoUrl;
        this.time = time;
        this.uid = uid;
        this.device_token = device_token;
    }

    public String getDevice_token() {
        return device_token;
    }

    public void setDevice_token(String device_token) {
        this.device_token = device_token;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getSenderid() {
        return senderid;
    }

    public void setSenderid(String senderid) {
        this.senderid = senderid;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }
}
