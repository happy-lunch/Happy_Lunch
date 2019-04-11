package com.cnpm.happylunch;

public class Food {
    private int foodImg;
    private String foodName;
    private String foodPrice;
    private double numStar;
    private int numSold;
    private KindOfFood kind;

    public Food(int foodImg, String foodName, String foodPrice) {
        this.foodImg = foodImg;
        this.foodName = foodName;
        this.foodPrice = foodPrice;
    }

    public Food(int foodImg, String foodName, String foodPrice, int numSold, KindOfFood kind) {
        this.foodImg = foodImg;
        this.foodName = foodName;
        this.foodPrice = foodPrice;
        this.numSold = numSold;
        this.kind = kind;
    }

    public void setNumStar(double numStar) {
        this.numStar = numStar;
    }

    public void setNumSold(int numSold) {
        this.numSold = numSold;
    }

    public void setKind(KindOfFood kind) {
        this.kind = kind;
    }

    public void setFoodImg(int foodImg) {
        this.foodImg = foodImg;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public void setFoodPrice(String foodPrice) {
        this.foodPrice = foodPrice;
    }

    public int getFoodImg() {
        return foodImg;
    }

    public String getFoodName() {
        return foodName;
    }

    public String getFoodPrice() {
        return foodPrice;
    }

    public double getNumStar() {
        return numStar;
    }

    public int getNumSold() {
        return numSold;
    }

    public KindOfFood getKind() {
        return kind;
    }
}
