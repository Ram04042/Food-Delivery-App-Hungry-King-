package com.aishwarya.food_delivery.eatit.Model;


public class User {
    private String name;
    private String password;
    private String Phone;
    private String IsStaff;

    public User() {

    }

    public User(String name, String password) {
        this.name = name;
        this.password = password;
        IsStaff = "false";
    }

    public String getIsStaff() {
        return IsStaff;
    }

    public void setIsStaff(String isStaff) {
        IsStaff = isStaff;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
