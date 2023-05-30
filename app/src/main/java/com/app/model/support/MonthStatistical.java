package com.app.model.support;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class MonthStatistical implements Serializable {

    @SerializedName("month")
    @Expose
    int month;

    @SerializedName("likes")
    @Expose
    int likes;

    public MonthStatistical(int month, int likes) {
        this.month = month;
        this.likes = likes;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }
}
