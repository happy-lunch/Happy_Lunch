package com.cnpm.happylunch;

public class RatingFood {
    private String foodId;
    private int level;

    public RatingFood() {

    }

    public RatingFood(String foodId, int level) {
        this.foodId = foodId;
        this.level = level;
    }

    public void setFoodId(String foodId) {
        this.foodId = foodId;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getFoodId() {
        return foodId;
    }

    public int getLevel() {
        return level;
    }
}
