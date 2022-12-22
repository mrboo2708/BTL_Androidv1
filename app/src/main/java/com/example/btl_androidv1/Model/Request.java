package com.example.btl_androidv1.Model;

import java.util.List;

public class Request {
    private String phone;
    private String name;
    private String address;
    private String total;
    private String Status;
    private List<Order> stuff;

    public Request() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Request(String phone, String name, String address, String total, List<Order> stuff) {
        this.phone = phone;
        this.address = address;
        this.total = total;
        this.stuff = stuff;
        this.name = name;
        this.Status = "0";

    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<Order> getStuff() {
        return stuff;
    }

    public void setStuff(List<Order> stuff) {
        this.stuff = stuff;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }
}
