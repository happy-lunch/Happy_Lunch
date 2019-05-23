package com.cnpm.happylunch;

import java.io.Serializable;

public class Category implements Serializable {
    private String id;
    private String img;
    private String name;

    public Category() {

    }

    public void setId(String id) {
        this.id = id;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getImg() {
        return img;
    }

    public String getName() {
        return name;
    }
}
