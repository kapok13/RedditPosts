package com.vb.redditposts.data.models;

import android.graphics.Bitmap;

import java.io.Serializable;

public class Posts implements Serializable {

    private String author;
    private String time;
    private Bitmap picture;
    private int commentsNumber;

    public Posts(String author, String time, Bitmap picture, int commentsNumber) {
        this.author = author;
        this.time = time;
        this.picture = picture;
        this.commentsNumber = commentsNumber;
    }

    public String getAuthor() {
        return author;
    }

    public String getTime() {
        return time;
    }

    public Bitmap getPicture() {
        return picture;
    }

    public int getCommentsNumber() {
        return commentsNumber;
    }
}
