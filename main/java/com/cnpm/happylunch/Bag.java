package com.cnpm.happylunch;

class Bag {
    private int img;
    private String name;
    private String time;
    private int count;
    private int status;


    Bag(int img, String name, String time, int count, int status) {
        this.img = img;
        this.name = name;
        this.time = time;
        this.count = count;
        this.status = status;
    }

    int getImg() {
        return img;
    }


    String getName() {
        return name;
    }


    String getTime() {
        return time;
    }


    int getCount() {
        return count;
    }


    int getStatus() {
        return status;
    }

}
