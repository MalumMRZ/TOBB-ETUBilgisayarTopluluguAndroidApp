package com.yusufmirza.etubilgisayartopluluk.HelperClasses;

import com.yusufmirza.etubilgisayartopluluk.Helper;

import java.io.Serializable;

public class CommunityProjects implements Serializable, Helper {
    String imageUri;

    String expired;

    String projectLink;

    String name;
    String projectDate;
    String projectTime;

    String projectPlace;
    String projectSlogan;
    String projectApplication;

    public CommunityProjects(String imageUri, String projectLink, String name, String projectDate, String projectTime, String projectPlace, String projectSlogan, String projectApplication, String expired) {
        this.imageUri = imageUri;
        this.expired = expired;
        this.projectLink = projectLink;
        this.name = name;
        this.projectDate = projectDate;
        this.projectTime = projectTime;
        this.projectPlace = projectPlace;
        this.projectSlogan = projectSlogan;
        this.projectApplication = projectApplication;
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

    public String getProjectLink() {
        return projectLink;
    }

    public void setProjectLink(String projectLink) {
        this.projectLink = projectLink;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProjectDate() {
        return projectDate;
    }

    public void setProjectDate(String projectDate) {
        this.projectDate = projectDate;
    }

    public String getProjectTime() {
        return projectTime;
    }

    public void setProjectTime(String projectTime) {
        this.projectTime = projectTime;
    }

    public String getProjectPlace() {
        return projectPlace;
    }

    public void setProjectPlace(String projectPlace) {
        this.projectPlace = projectPlace;
    }

    public String getProjectSlogan() {
        return projectSlogan;
    }

    public void setProjectSlogan(String projectSlogan) {
        this.projectSlogan = projectSlogan;
    }

    public String getProjectApplication() {
        return projectApplication;
    }

    public void setProjectApplication(String projectApplication) {
        this.projectApplication = projectApplication;
    }
}
