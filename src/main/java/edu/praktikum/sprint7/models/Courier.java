package edu.praktikum.sprint7.models;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class Courier {
    private int id;
    private String login;
    private String password;
    private String firstName;
}