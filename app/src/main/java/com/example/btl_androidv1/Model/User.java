package com.example.btl_androidv1.Model;

public class User {
    private String Name;
    private String Password;
    private String Phone;
    private String IsWork;

    public User() {
    }

    public String getIsWork() {
        return IsWork;
    }

    public void setIsWork(String isWork) {
        IsWork = isWork;
    }

    public User(String name, String password) {
        this.Name = name;
        this.Password = password;
        IsWork = "false";

    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        this.Name = name;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        this.Password = password;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }
}
