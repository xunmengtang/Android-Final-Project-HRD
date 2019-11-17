package com.reaksmeyarun.coffee.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class User implements Parcelable {

    private String authID;
    private String user;
    private String email;
    private String password;
    private String rule;
    private String status;

    public User() {
    }

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public User(String authID, String user, String email, String rule, String status) {
        this.authID = authID;
        this.user = user;
        this.email = email;
        this.rule = rule;
        this.status = status;
    }

    public User(String authID, String user, String email, String password, String rule, String status) {
        this.user = user;
        this.email = email;
        this.password = password;
        this.rule = rule;
        this.authID = authID;
        this.status = status;
    }

    protected User(Parcel in) {
        authID = in.readString();
        user = in.readString();
        email = in.readString();
        password = in.readString();
        rule = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getRule() {
        return rule;
    }

    public void setRule(String rule) {
        this.rule = rule;
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

    public String getAuthID() {
        return authID;
    }

    public void setAuthID(String authID) {
        this.authID = authID;
    }

    @Exclude
    public Map<String, Object> toMap(){
        Map<String, Object> map = new HashMap<>();
        map.put("authID",authID);
        map.put("user",user);
        map.put("email",email);
        map.put("rule",rule);
        map.put("status",status);
        return map;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(authID);
        dest.writeString(user);
        dest.writeString(email);
        dest.writeString(password);
        dest.writeString(rule);
    }

    @Override
    public String toString() {
        return "User{" +
                "authID='" + authID + '\'' +
                ", user='" + user + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", rule='" + rule + '\'' +
                '}';
    }
}
