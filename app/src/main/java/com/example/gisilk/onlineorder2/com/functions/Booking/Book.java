package com.example.gisilk.onlineorder2.com.functions.Booking;

public class Book {

    private String roomType;
    private int noOfRooms;
    private int noOfNights;
    private String checkinDate;
    private String checkoutDate;

    public Book(String roomType, int noOfRooms, int noOfNights, String checkinDate, String checkoutDate) {
        this.roomType = roomType;
        this.noOfRooms = noOfRooms;
        this.noOfNights = noOfNights;
        this.checkinDate = checkinDate;
        this.checkoutDate = checkoutDate;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public int getNoOfRooms() {
        return noOfRooms;
    }

    public void setNoOfRooms(int noOfRooms) {
        this.noOfRooms = noOfRooms;
    }

    public int getNoOfNights() {
        return noOfNights;
    }

    public void setNoOfNights(int noOfNights) {
        this.noOfNights = noOfNights;
    }

    public String getCheckinDate() {
        return checkinDate;
    }

    public void setCheckinDate(String checkinDate) {
        this.checkinDate = checkinDate;
    }

    public String getCheckoutDate() {
        return checkoutDate;
    }

    public void setCheckoutDate(String checkoutDate) {
        this.checkoutDate = checkoutDate;
    }
}
