package com.reaksmeyarun.coffee.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class Item implements Parcelable {
    private String id;
    private String createBy;
    private String itemName;
    private String itemCode;
    private String categoryID;
    private double price;
    private double cost;
    private String quaility;

    protected Item(Parcel in) {
        id = in.readString();
        createBy = in.readString();
        itemName = in.readString();
        itemCode = in.readString();
        categoryID = in.readString();
        price = in.readDouble();
        cost = in.readDouble();
    }

    public static final Creator<Item> CREATOR = new Creator<Item>() {
        @Override
        public Item createFromParcel(Parcel in) {
            return new Item(in);
        }

        @Override
        public Item[] newArray(int size) {
            return new Item[size];
        }
    };

    @Exclude
    public Map<String, Object> toMap(){
        Map<String, Object> map = new HashMap<>();
        map.put("id",id);
        map.put("itemName",itemName);
        map.put("createBy",createBy);
        map.put("itemCode",itemCode);
        map.put("categoryID",categoryID);
        map.put("price",price);
        map.put("cost",cost);
        return map;
    }

    public Item() {
    }

    public Item(String id, String createBy, String itemName, String itemCode, String categoryID, double price, double cost) {
        this.id = id;
        this.createBy = createBy;
        this.itemName = itemName;
        this.itemCode = itemCode;
        this.categoryID = categoryID;
        this.price = price;
        this.cost = cost;
    }

    public Item(String id, String createBy, String itemName, String itemCode, String categoryID, double price, double cost, String quaility) {
        this.id = id;
        this.createBy = createBy;
        this.itemName = itemName;
        this.itemCode = itemCode;
        this.categoryID = categoryID;
        this.price = price;
        this.cost = cost;
        this.quaility = quaility;
    }

    public String getQuaility() {
        return quaility;
    }

    public void setQuaility(String quaility) {
        this.quaility = quaility;
    }

    @Override
    public String toString() {
        return "Item{" +
                "id='" + id + '\'' +
                ", itemName='" + itemName + '\'' +
                ", itemCode='" + itemCode + '\'' +
                ", price=" + price +
                ", cost=" + cost +
                '}';
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

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(String categoryID) {
        this.categoryID = categoryID;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public static Creator<Item> getCREATOR() {
        return CREATOR;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(createBy);
        dest.writeString(itemName);
        dest.writeString(itemCode);
        dest.writeString(categoryID);
        dest.writeDouble(price);
        dest.writeDouble(cost);
    }
}
