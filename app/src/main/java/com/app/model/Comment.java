package com.app.model;

import android.util.Log;

import com.app.service.ApiClient;

import java.io.Serializable;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

import retrofit2.Call;

public class Comment implements Serializable {
    private int idUser;
    private int idStory;
    private String content;
    private int star;

    private String date;

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public int getIdStory() {
        return idStory;
    }

    public void setIdStory(int idStory) {
        this.idStory = idStory;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getStar() {
        return star;
    }

    public void setStar(int star) {
        this.star = star;
    }


    public String getDate() {
        LocalDate now = LocalDate.now();
        LocalDate lastUpdate = LocalDate.parse(date.substring(0, date.indexOf("T")));
        long daysBetween = ChronoUnit.DAYS.between(lastUpdate, now);
        Log.e("Time", daysBetween + "");
        if (daysBetween == 0) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
            LocalTime timeNow = LocalTime.now();
            Log.e("Lỗi time", date);
            String time = date.substring(date.indexOf("T") + 1, date.indexOf("."));
            Log.e("Lỗi time", time);
            LocalTime timeLastUpdate = LocalTime.parse(time, formatter);
            Duration duration = Duration.between(timeLastUpdate, timeNow);
            long hours = duration.toHours();
            long minutes = duration.toMinutes() % 60;
            if (hours < 1 && minutes > 1) {
                return minutes + " phút trước";
            } else if (hours >= 1) {
                return hours + " giờ trước";
            } else {
                return "1 phút trước";
            }
        } else if (daysBetween == 1) {
            return "Hôm qua";
        } else if (daysBetween > 1 && daysBetween < 7) {
            return daysBetween + " ngày trước";
        } else {
            return lastUpdate.getDayOfMonth() + " " + lastUpdate.getMonth().name() + " " + lastUpdate.getYear();
        }
    }


    public void setDate(String date) {
        this.date = date;
    }
}
