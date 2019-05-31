package com.cnpm.happylunch;

import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

class Order{

    private String id;
    private String MSSV;
    private String bill;
    private String time;

    public Order(){

    }

    public Order(String id, String MSSV, String bill, String time) {
        this.id = id;
        this.MSSV = MSSV;
        this.bill = bill;
        this.time = time;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBill() {
        return bill;
    }

    public void setBill(String bill) {
        this.bill = bill;
    }

    public String getMSSV() {
        return MSSV;
    }

    public void setMSSV(String MSSV) {
        this.MSSV = MSSV;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}

class BillItem {
    private int num;
    private int price;
    private String id;
    private String status = "Đang xử lí";

    public BillItem(){

    }

    BillItem(BagRow food){
        this.id = food.getIdFood();
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
        if (String.valueOf(time.charAt(1)).equals("d"))
            time = "0" + time;
        if (String.valueOf(time.charAt(4)).equals("h"))
            time = time.substring(0,3) + "0" + time.substring(3);
        if (String.valueOf(time.charAt(7)).equals("p"))
            time = time.substring(0,6) + "0" + time.substring(6);
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

class BillResellItem{
    private String idFood, idBill, idResell;
    private int numResell;

    public BillResellItem(){}

    public BillResellItem(String idFood, String idBill, String idResell, int numResell) {
        this.idFood = idFood;
        this.idBill = idBill;
        this.idResell = idResell;
        this.numResell = numResell;
    }

    public int getNumResell() {
        return numResell;
    }

    public void setNumResell(int numResell) {
        this.numResell = numResell;
    }

    public String getIdFood() {
        return idFood;
    }

    public void setIdFood(String idFood) {
        this.idFood = idFood;
    }

    public String getIdBill() {
        return idBill;
    }

    public void setIdBill(String idBill) {
        this.idBill = idBill;
    }

    public String getIdResell() {
        return idResell;
    }

    public void setIdResell(String idResell) {
        this.idResell = idResell;
    }
}

class BillResell{
    private String id,time;
    private int cost;
    public List<BillResellItem> item;

    public BillResell(){}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public List<BillResellItem> getItem() {
        return item;
    }

    public void setItem(List<BillResellItem> item) {
        this.item = item;
    }
}

