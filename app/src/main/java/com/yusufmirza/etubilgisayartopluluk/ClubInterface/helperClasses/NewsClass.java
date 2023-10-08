package com.yusufmirza.etubilgisayartopluluk.ClubInterface.helperClasses;

public class NewsClass {

    String link;
    String name;

    String imageUri;


    public NewsClass(String link, String name, String imageUri) {
        this.link = link;
        this.name = name;
        this.imageUri = imageUri;
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
}
