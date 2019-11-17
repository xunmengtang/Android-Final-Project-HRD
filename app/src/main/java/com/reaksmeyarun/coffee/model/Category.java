package com.reaksmeyarun.coffee.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class Category implements Parcelable{
    private String createBy;
    private String id;
    private String categoryName;

    protected Category(Parcel in) {
        createBy = in.readString();
        id = in.readString();
        categoryName = in.readString();
    }

    public static final Creator<Category> CREATOR = new Creator<Category>() {
        @Override
        public Category createFromParcel(Parcel in) {
            return new Category(in);
        }

        @Override
        public Category[] newArray(int size) {
            return new Category[size];
        }
    };

    @Override
    public String toString() {
        return "Category{" +
                "createBy='" + createBy + '\'' +
                ", id='" + id + '\'' +
                ", categoryName='" + categoryName + '\'' +
                '}';
    }

    public Category() {
    }

    public Category(String id) {
        this.id = id;
    }

    public Category(String createBy, String categoryName) {
        this.id = createBy;
        this.categoryName = categoryName;
    }

    public Category(String createBy, String id, String categoryName) {
        this.createBy = createBy;
        this.id = id;
        this.categoryName = categoryName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    @Exclude
    public Map<String, Object> toMap(){
        Map<String, Object> map = new HashMap<>();
        map.put("id",id);
        map.put("createBy",createBy);
        map.put("categoryName",categoryName);
        return map;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(createBy);
        parcel.writeString(id);
        parcel.writeString(categoryName);
    }
}