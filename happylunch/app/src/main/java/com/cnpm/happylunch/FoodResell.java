package com.cnpm.happylunch;

public class FoodResell {
    private String id, idFood, idUser, idBill, time;
    private int price, numSell;

    public FoodResell(){

    }

    FoodResell(String key, BagRow bagRow, String idUser){
        this.id = key;
        this.idFood = bagRow.getId();
        this.idUser = idUser;
        this.idBill = bagRow.getIdBIll();
        this.time = bagRow.getTime();
        this.price = bagRow.getPrice();
        this.numSell = bagRow.getCount();
        this.idBill = bagRow.getIdBIll();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdFood() {
        return idFood;
    }

    public void setIdFood(String idFood) {
        this.idFood = idFood;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
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

    public int getNumSell() {
        return numSell;
    }

    public void setNumSell(int numSell) {
        this.numSell = numSell;
    }

    public String getIdBill() {
        return idBill;
    }

    public void setIdBill(String idBill) {
        this.idBill = idBill;
    }
}
