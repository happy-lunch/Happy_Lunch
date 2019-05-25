package com.cnpm.happylunch;

import java.io.Serializable;

public class Food implements Serializable{
    private String description;
    private String foodId;
    private String img;
    private String menuId;
    private String name;
    private int numPeopleRating;
    private int numSold;
    private String price;
    private float rating;
    private int timeFinish;

    public Food() {

    }

    public void setNumPeopleRating(int numPeopleRating) {
        this.numPeopleRating = numPeopleRating;
    }

    public int getNumPeopleRating() {
        return numPeopleRating;
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

    public void setRating(float rating) {
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

    public float getRating() {
        return rating;
    }

    public int getTimeFinish() {
        return timeFinish;
    }

    @Override
    public String toString() {
        return getName();
    }

    @Override
    public boolean equals( Object obj) {
        Food food = (Food) obj;
        return food.getFoodId().equals(getFoodId());
    }

}
