package com.app.model;

import android.content.SharedPreferences;

import com.google.gson.Gson;

import java.io.Serializable;

public class User implements Serializable {
    private int id;
    private String name;
    private String email;
    private String password;
    private String date;
    private int status;

    public User(int id, String name, String email, String password, String date, int status) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.date = date;
        this.status = status;
    }

    public User() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void putToSharedPreferences(SharedPreferences sharedPreferences) {
        Gson gson = new Gson();
        String userJson = gson.toJson(this);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("user", userJson);
        editor.apply();
    }

    public static User getUserFromSharedPreferences(SharedPreferences sharedPreferences) {
        User user = null;
        String userJson = sharedPreferences.getString("user", null);
        if (userJson != null) {
            Gson gson = new Gson();
            user = gson.fromJson(userJson, User.class);
        }
        return user;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", date='" + date + '\'' +
                ", status=" + status +
                '}';
    }
}
