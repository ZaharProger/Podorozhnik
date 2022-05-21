package com.podorozhnik.entities;

public class Request {
    private String userLogin;
    private String departurePoint;
    private String destinationPoint;
    private String date;
    private String time;
    private boolean isDriver;
    public Request()
    {}

    public Request(String userLogin, String departurePoint, String destinationPoint,
                   String date, String time, boolean isDriver){
        this.userLogin = userLogin;
        this.departurePoint = departurePoint;
        this.destinationPoint = destinationPoint;
        this.date = date;
        this.time = time;
       this.isDriver = isDriver;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }

    public String getUserLogin() {
        return userLogin;
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
