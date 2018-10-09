package com.example.gisilk.onlineorder2.com.functions.OrderFood;

public class Food {

    private String name, size, filePath;
    private int price;
    private boolean availability;

    public Food(){
    }

    public Food(String name, String size, int price, boolean availability, String filePath) {
        this.name = name;
        this.size = size;
        this.price = price;
        this.availability = availability;
        this.filePath = filePath;
    }

    public Food(String name, String size, int price, boolean availability) {
        this.name = name;
        this.size = size;
        this.price = price;
        this.availability = availability;

    }


    public String getFilePath() {
        return filePath;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public boolean isAvailability() {
        return availability;
    }

    public void setAvailability(boolean availability) {
        this.availability = availability;
    }
}
