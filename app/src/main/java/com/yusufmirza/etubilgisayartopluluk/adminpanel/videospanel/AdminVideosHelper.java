package com.yusufmirza.etubilgisayartopluluk.adminpanel.videospanel;

import com.yusufmirza.etubilgisayartopluluk.Helper;

import java.io.Serializable;

public class AdminVideosHelper implements Serializable, Helper {

     private String name;
    private  String link;
    private String imageUri;
    private String subMap;

    private String accountType;

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }

    public String getSubMap() {
        return subMap;
    }

    public void setSubMap(String subMap) {
        this.subMap = subMap;
    }

    public AdminVideosHelper(String name, String link, String imageUri, String subMap, String accountType) {
        this.name = name;
        this.link = link;
        this.accountType = accountType;
        this.imageUri = imageUri;
        this.subMap = subMap;
    }
}
