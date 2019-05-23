package com.cnpm.happylunch;

import java.io.Serializable;

public class Food implements Serializable{
    private String description;
    private String foodId;
    private String img;
    private String menuId;
    private String name;
    private int numSold;
    private String price;
    private int rating;
    private int timeFinish;

    public Food() {

    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setFoodId(String foodId) {
        this.foodId = foodId;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNumSold(int numSold) {
        this.numSold = numSold;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public void setTimeFinish(int timeFinish) {
        this.timeFinish = timeFinish;
    }

    public String getDescription() {
        return description;
    }

    public String getFoodId() {
        return foodId;
    }

    public String getImg() {
        return img;
    }

    public String getMenuId() {
        return menuId;
    }

    public String getName() {
        return name;
    }

    public int getNumSold() {
        return numSold;
    }

    public String getPrice() {
        return price;
    }

    public int getRating() {
        return rating;
    }

    public int getTimeFinish() {
        return timeFinish;
    }

    @Override
    public String toString() {
        return getName();
    }

}
