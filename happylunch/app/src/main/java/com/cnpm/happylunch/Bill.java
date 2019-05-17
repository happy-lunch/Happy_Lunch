package com.cnpm.happylunch;

import java.util.ArrayList;
import java.util.List;

class BillItem{

    private int num;
    private int price;
    private String id;
    private String status = "Đang xử lí";

    public BillItem(){

    }

    BillItem(BagRow food){
        this.id = food.getId();
        this.num = food.getCount();
        this.price = food.getPrice();
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public BillItem(int num, int price, String id, String status) {
        this.num = num;
        this.price = price;
        this.id = id;
        this.status = status;
    }
}

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

