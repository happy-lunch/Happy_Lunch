package com.cnpm.happylunch;

import java.util.ArrayList;
import java.util.List;


public class Bill {

    private String id;
    private int price;
    private String time;
    private String status = "Đang xử lí";
    public List<BillItem> item;

    public Bill(){

    }

    public Bill(String id, int price, String time){
        this.item = new ArrayList<>();
        this.id = id;
        this.price = price;
        this.time = time;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

