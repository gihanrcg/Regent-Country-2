package com.example.gisilk.onlineorder2.com.functions.OrderLiquor;

public class Liquor {

    private String name, size, orderDateTime;
    private int price, quantity;
    private boolean availability;
    //private int thumbnail;

//    public void setThumbnail(int thumbnail) {
//        this.thumbnail = thumbnail;
//    }

    //public int getThumbnail() {
     //   return thumbnail;
  //  }

    public Liquor() {
    }

    public String getDateTime() {

        return orderDateTime;
    }

    public void setDateTime(String dateTime) {
        this.orderDateTime = dateTime;
    }

    public Liquor(String name, String size, int price, boolean availability) {
        this.name = name;
        this.size = size;
        this.price = price;
        this.availability = availability;
    }

    public String getName() {

        return name;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getQuantity() {

        return quantity;
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
