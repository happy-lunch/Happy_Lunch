package com.cnpm.happylunch;

import com.google.firebase.database.Exclude;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class User {
    private String uid;
    private String avaName;
    private String firstName;
    private String lastName;
    private String mssv;
    private String email;
    private int HPCoin;
    private List<Food> listFood = new ArrayList<Food>();
    private ArrayList<RatingFood> ratingFoods = new ArrayList<RatingFood>();

    public User(){

    }

    public User(String uid,String avaName, String firstName, String lastName, String mssv, String email, int HPCoin) {
        this.uid = uid;
        this.firstName = firstName;
        this.lastName = lastName;
        this.mssv = mssv;
        this.email = email;
        this.HPCoin = HPCoin;
        this.avaName = avaName;
    }

    public User(String mssv, String firstName, String lastName){
        this.lastName = lastName;
        this.mssv = mssv;
    }

    public List<Food> getListFood() {
        return listFood;
    }

    public ArrayList<RatingFood> getRatingFoods() {
        return ratingFoods;
    }

    public String getUid() {
        return uid;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getAvaName() {
        return avaName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getMssv() {
        return mssv;
    }

    public String getEmail() {
        return email;
    }

    public int getHPCoin() {
        return HPCoin;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setAvaName(String avaName) {
        this.avaName = avaName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setMssv(String mssv) {
        this.mssv = mssv;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setHPCoin(int HPCoin) {
        this.HPCoin = HPCoin;
    }

    public void setListFood(List<Food> listFood) {
        this.listFood = listFood;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("coin", HPCoin);

        return result;
    }
}
