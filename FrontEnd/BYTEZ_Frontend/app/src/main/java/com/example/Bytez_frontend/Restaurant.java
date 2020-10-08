package com.example.Bytez_frontend;

/**
 * This class represents a restaurant user from the database
 */
public class Restaurant {
    private String name;
    private String address;
    //private Long id;
    //private Double avgScore;
    //private User owner;
    //private logo;

    public Restaurant(String name, String address) {
        this.name = name;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
