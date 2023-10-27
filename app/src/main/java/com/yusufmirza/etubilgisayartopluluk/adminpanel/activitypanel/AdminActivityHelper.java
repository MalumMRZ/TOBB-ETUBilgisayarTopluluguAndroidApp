package com.yusufmirza.etubilgisayartopluluk.adminpanel.activitypanel;

import com.yusufmirza.etubilgisayartopluluk.Helper;

import java.io.Serializable;

public class AdminActivityHelper implements Serializable, Helper {

     private String name;
    private  String activityLink;
    private String place;
    private String date;
    private String time;
    private String speaker;
    private String subMap;


    public AdminActivityHelper(String name, String activityLink, String place, String date, String time, String speaker, String subMap) {
        this.name = name;
        this.activityLink = activityLink;
        this.place = place;
        this.date = date;
        this.time = time;
        this.speaker = speaker;
        this.subMap = subMap;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getActivityLink() {
        return activityLink;
    }

    public void setActivityLink(String activityLink) {
        this.activityLink = activityLink;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getSpeaker() {
        return speaker;
    }

    public void setSpeaker(String speaker) {
        this.speaker = speaker;
    }

    public String getSubMap() {
        return subMap;
    }

    public void setSubMap(String subMap) {
        this.subMap = subMap;
    }
}
