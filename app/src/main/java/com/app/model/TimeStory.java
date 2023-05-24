package com.app.model;

import android.util.Log;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;


public class TimeStory {
    private String date;
    private String time;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String showDateTime() {
        LocalDate now = LocalDate.now();
        LocalDate lastUpdate = LocalDate.parse(date.substring(0, date.indexOf("T")));
        long daysBetween = ChronoUnit.DAYS.between(lastUpdate, now) ;
        Log.e("Time", daysBetween + "");
        if (daysBetween == 0) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
            LocalTime timeNow = LocalTime.now();
            LocalTime timeLastUpdate = LocalTime.parse(this.time, formatter);
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

}
