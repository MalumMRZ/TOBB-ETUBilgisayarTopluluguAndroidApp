package com.yusufmirza.etubilgisayartopluluk.ClubInterface.helperClasses;

import com.yusufmirza.etubilgisayartopluluk.Helper;

import java.io.Serializable;

public class VideosClass implements Serializable, Helper {

    String name;
    String imageUri;

    String accountType;

    String link;


    public VideosClass(String name, String imageUri, String accountType, String link) {
        this.name = name;
        this.imageUri = imageUri;
        this.accountType = accountType;
        this.link = link;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }
}
