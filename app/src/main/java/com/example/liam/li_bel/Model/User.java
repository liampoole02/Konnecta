package com.example.liam.li_bel.Model;

public class User {
    private String username;
    private String id;
    private String imageURL;
    private String status;

    public User(String username, String id, String imageURL, String status) {
        this.username = username;
        this.id = id;
        this.imageURL = imageURL;
        this.status = status;

    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public User() {
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
