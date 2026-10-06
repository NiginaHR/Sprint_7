package edu.praktikum.sprint7.models;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class Order {
    private String firstName;
    private String lastName;
    private String address;
    private String metroStation;
    private String phone;
    private int rentTime;
    private String deliveryDate;
    private String comment;
    private List<String> color;
    private String password;

    public Order(String firstName, String phone, String address, List<String> color, String comment) {
        this.firstName = firstName;
        this.phone = phone;
        this.address = address;
        this.color = color;
        this.comment = comment;
    }

            public Order() {
        }
    }
