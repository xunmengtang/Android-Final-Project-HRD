package com.reaksmeyarun.coffee.model;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Receipt {

    private String id;
    private String createDate;
    private long sortDate;
    private String createBy;
    private List<Item> item;
    private String total;

    @Exclude
    public Map<String, Object> toMap(){
        Map<String, Object> map = new HashMap<>();
        map.put("id",id);
        map.put("createDate",createDate);
        map.put("createBy",createBy);
        map.put("item",item);
        map.put("total",total);
        return map;
    }

    public Receipt() {
    }

    public Receipt(String id, String createDate, long sortDate, String createBy, List<Item> itemID, String total) {
        this.id = id;
        this.createDate = createDate;
        this.sortDate = sortDate;
        this.createBy = createBy;
        this.item = itemID;
        this.total = total;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public List<Item> getItemID() {
        return item;
    }

    public void setItemID(List<Item> itemID) {
        this.item = itemID;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public long getSortDate() {
        return sortDate;
    }

    public void setSortDate(long sortDate) {
        this.sortDate = sortDate;
    }

    @Override
    public String toString() {
        return "Receipt{" +
                "id='" + id + '\'' +
                ", createDate='" + createDate + '\'' +
                ", sortDate=" + sortDate +
                ", createBy='" + createBy + '\'' +
                ", item=" + item +
                ", total='" + total + '\'' +
                '}';
    }
}
