package com.yusufmirza.etubilgisayartopluluk.HelperClasses;

import java.io.Serializable;

public class Member implements Serializable {

    String name;
    String info;
    String imageUri;
    String link;

    public Member(String name, String info, String imageUri, String link) {
        this.name = name;
        this.info = info;
        this.imageUri = imageUri;
        this.link = link;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
