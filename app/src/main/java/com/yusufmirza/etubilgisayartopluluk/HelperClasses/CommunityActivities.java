package com.yusufmirza.etubilgisayartopluluk.HelperClasses;

import com.yusufmirza.etubilgisayartopluluk.Helper;

import java.io.Serializable;

public class CommunityActivities implements Serializable, Helper {
    String imageUri;

    String expired;

    String activityLink;

    String name;
    String activityDate;
    String activityTime;

    String activityPlace;
    String activitySpeaker;


    public CommunityActivities(String imageUri, String activityLink, String name, String activityDate, String activityTime, String activityPlace, String activitySpeaker, String expired) {
        this.imageUri = imageUri;
        this.expired = expired;
        this.activityLink = activityLink;
        this.name = name;
        this.activityDate = activityDate;
        this.activityTime = activityTime;
        this.activityPlace = activityPlace;
        this.activitySpeaker = activitySpeaker;
    }



    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }

    public String getExpired() {
        return expired;
    }

    public void setExpired(String expired) {
        this.expired = expired;
    }

    public String getActivityLink() {
        return activityLink;
    }

    public void setActivityLink(String activityLink) {
        this.activityLink = activityLink;
    }

    public String getName() {
        return name;
    }

    public void setName(String activityName) {
        this.name = activityName;
    }

    public String getActivityDate() {
        return activityDate;
    }

    public void setActivityDate(String activityDate) {
        this.activityDate = activityDate;
    }

    public String getActivityTime() {
        return activityTime;
    }

    public void setActivityTime(String activityTime) {
        this.activityTime = activityTime;
    }

    public String getActivityPlace() {
        return activityPlace;
    }

    public void setActivityPlace(String activityPlace) {
        this.activityPlace = activityPlace;
    }

    public String getActivitySpeaker() {
        return activitySpeaker;
    }

    public void setActivitySpeaker(String activitySpeaker) {
        this.activitySpeaker = activitySpeaker;
    }


}
