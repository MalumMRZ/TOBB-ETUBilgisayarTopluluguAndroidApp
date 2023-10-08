package com.yusufmirza.etubilgisayartopluluk.ClubInterface.helperClasses;



import java.io.Serializable;
import java.util.ArrayList;

public class ActivityClass implements Serializable {

  String imageUri;

  ArrayList<String> imageList;

  String activityLink;

  String activityName;
  String activityDate;
  String activityTime;

  String activityPlace;
  String activitySpeaker;


    public ActivityClass(String imageUri, ArrayList<String> imageList, String activityLink, String activityName, String activityDate, String activityTime, String activityPlace, String activitySpeaker) {
        this.imageUri = imageUri;
        this.imageList = imageList;
        this.activityLink = activityLink;
        this.activityName = activityName;
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

    public ArrayList<String> getImageList() {
        return imageList;
    }

    public void setImageList(ArrayList<String> imageList) {
        this.imageList = imageList;
    }

    public String getActivityLink() {
        return activityLink;
    }

    public void setActivityLink(String activityLink) {
        this.activityLink = activityLink;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
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
