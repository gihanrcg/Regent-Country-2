package com.example.gisilk.onlineorder2.com.functions.Booking;

public class Book {

    private String roomType;
    private int noOfRooms, noOfNights;
    private String checkinDate;

    public Book() {
    }

    public Book(String roomType, int noOfRooms, int noOfNights, String checkinDate) {
        this.roomType = roomType;
        this.noOfRooms = noOfRooms;
        this.noOfNights = noOfNights;
        this.checkinDate = checkinDate;
    }
    public Book(String roomType, int noOfNights, int noOfRooms){
        this.roomType = roomType;
        this.noOfNights = noOfNights;
        this.noOfRooms = noOfRooms;
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

}
