package com.reaksmeyarun.coffee.model;

import java.util.ArrayList;
import java.util.List;

public class Global {

    public static List<Item> itemList = new ArrayList<>();
    public static String currentTable;
    public static String view ;
    public static String order;

    public static final String ONEDIT_CARTORDER = "0";
    public static final String ONDELETE_CARTORDER = "1";
    public static final String ONADDQUALITY_CARTORDER = "2";

    public static final String ONADD_CATEGORY = "100";
    public static final String ONEDIT_CATEGORY = "200";

    public static final String ONADD_ITEM = "100";
    public static final String ONEDIT_ITEM = "200";

    public static final String ONEDITITEM_TITLE = "Edit Item";
    public static final String ONADDITEM_TITLE = "Add Item";

    public static final String ONEDITCATEGORY_TITLE = "Edit Category";
    public static final String ONADDCATEGORY_TITLE = "Add Category";

    public static final String ONLINE = "1";
    public static final String OFFLINE = "2";

    public static final String PENDING = "Pending";
    public static final String PAID = "Paid";

    public static final String AVAILABLE = "Available";
    public static final String BOOKED = "Booked";


    public static final String ADMIN_NODE = "Admin";
    public static final String CASHIER_NODE = "Cashier";
    public static final String WAITER_NODE = "Waiter";

    public static User user;

    public static User getUser() {
        return user;
    }

    public static void setUser(User user) {
        Global.user = user;
    }

    public static List<Item> getItemList() {
        return itemList;
    }

    public static void setItemList(List<Item> itemList) {
        Global.itemList = itemList;
    }

    public static String getCurrentTable() {
        return currentTable;
    }

    public static void setCurrentTable(String currentTable) {
        Global.currentTable = currentTable;
    }

    public static boolean onCheck(){
        if(user.getRule().equals(ADMIN_NODE)){
            return true;
        }else {
            return false;
        }
    }
}
