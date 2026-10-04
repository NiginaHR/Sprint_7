package edu.praktikum.sprint7.models;

import java.util.List;

public class Order {
    private String firstName;
    private String phone;
    private String address;


    private List<String> color;

    private String comment;

    public Order(String firstName, String phone, String address, List<String> color, String comment) {
        this.firstName = firstName;
        this.phone = phone;
        this.address = address;
        this.color = color;
        this.comment = comment;
    }
    public Order() {
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<String> getColor() {
        return color;
    }

    public void setColor(List<String> color) {
        this.color = color;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}