package com.podorozhnik.entities;

public class Request {
    private int id;
    private int userId;
    private String departurePoint;
    private String destinationPoint;
    private String date;
    private String time;
    private boolean isDriver;

    public Request(int id, int userId, String departurePoint, String destinationPoint,
                   String date, String time, boolean isDriver){
        this.id = id;
        this.userId = userId;
        this.departurePoint = departurePoint;
        this.destinationPoint = destinationPoint;
        this.date = date;
        this.time = time;
        this.isDriver = isDriver;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getUserId() {
        return userId;
    }

    public boolean isDriver() {
        return isDriver;
    }

    public void setDriver(boolean driver) {
        isDriver = driver;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    public void setDeparturePoint(String departurePoint) {
        this.departurePoint = departurePoint;
    }

    public String getDeparturePoint() {
        return departurePoint;
    }

    public void setDestinationPoint(String destinationPoint) {
        this.destinationPoint = destinationPoint;
    }

    public String getDestinationPoint() {
        return destinationPoint;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTime() {
        return time;
    }
}
