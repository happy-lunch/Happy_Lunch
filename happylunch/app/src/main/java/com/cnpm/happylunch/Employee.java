package com.cnpm.happylunch;

public class Employee {
    private String id;
    private boolean isOwner;
    private String name;
    private String username;
    private String password;
    private String std;

    public Employee(String id, boolean isOwner, String name, String username, String password, String std) {
        this.id = id;
        this.isOwner = isOwner;
        this.name = name;
        this.username = username;
        this.password = password;
        this.std = std;
    }

    public Employee() {

    }

    public void setId(String id) {
        this.id = id;
    }

    public void setOwner(boolean owner) {
        isOwner = owner;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setStd(String std) {
        this.std = std;
    }

    public String getId() {
        return id;
    }

    public boolean isOwner() {
        return isOwner;
    }

    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getStd() {
        return std;
    }

    @Override
    public boolean equals(Object obj) {
        Employee e = (Employee) obj;
        return e.getId().equals(getId());
    }
}
