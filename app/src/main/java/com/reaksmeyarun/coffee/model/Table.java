package com.reaksmeyarun.coffee.model;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Table {

    private String tableID;
    private String status ;
    private List<Item> itemList;

    public Table() {
    }

    public Table(String tableID, String status) {
        this.tableID = tableID;
        this.status = status;
    }

    public Table(String tableID, String status, List<Item> itemList) {
        this.tableID = tableID;
        this.status = status;
        this.itemList = itemList;
    }
    @Exclude
    public Map<String, Object> toMap(){
        Map<String, Object> map = new HashMap<>();
        map.put("tableID",tableID);
        map.put("status",status);
        map.put("itemList",itemList);
        return map;
    }
    public String getTableID() {
        return tableID;
    }

    public void setTableID(String tableID) {
        this.tableID = tableID;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Item> getItemList() {
        return itemList;
    }

    public void setItemList(List<Item> itemList) {
        this.itemList = itemList;
    }
}
