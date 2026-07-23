package com.restaurant.orderservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MenuItemDto {

    private Long id;
    private String name;
    private double price;
    private String category;

    @JsonProperty("isAvailable")
    private boolean isAvailable;

    public MenuItemDto(){}


    public Long  getId() {
        return id;
    }


    public void setId(Long id) {
        this.id = id;
    }


    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }


    public double getPrice() {
        return price;
    }


    public void setPrice(double price) {
        this.price = price;
    }


    public String getCategory() {
        return category;
    }


    public void setCategory(String category) {
        this.category = category;
    }



    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }


}
