package com.example.projectapp.Model;

public class User {

    private String id;
    private String username;
    private String imageURL;
    private String search;
    private String block;
    private String flat;
    private String course;


    public User(String id, String username, String imageURL, String search, String block, String flat, String course) {
        this.id = id;
        this.username = username;
        this.imageURL = imageURL;
        this.search = search;
        this.block = block;
        this.flat = flat;
        this.course = course;

    }

    public User() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public String getBlock() {
        return block;
    }

    public void setBlock(String block) {
        this.block = block;
    }

    public String getFlat() {
        return flat;
    }

    public void setFlat(String Flat) {
        this.flat = flat;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }


}





