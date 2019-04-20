package com.cnpm.happylunch;

import java.util.List;

public class User {
    private String uid;
    private String firstName;
    private String lastName;
    private String mssv;
    private String email;
    private int HPCoin;
    private List<Food> listFood;

    public User(){

    }

    public User(String uid, String firstName, String lastName, String mssv, String email, int HPCoin) {
        this.uid = uid;
        this.firstName = firstName;
        this.lastName = lastName;
        this.mssv = mssv;
        this.email = email;
        this.HPCoin = HPCoin;
    }

    public String getUid() {
        return uid;
    }

    public String getFirstName() {
        return firstName;
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
}
