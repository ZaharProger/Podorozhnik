package com.podorozhnik.entities;

public class Request {
    private String userLogin;
    private Location departurePoint;
    private Location destinationPoint;
    private String date;
    private String time;
    private boolean isDriver;
    private String userDeviceToken;

    public Request()
    {}

    public Request(String userLogin, Location departurePoint, Location destinationPoint,
                   String date, String time, boolean isDriver, String userDeviceToken){
        this.userLogin = userLogin;
        this.departurePoint = departurePoint;
        this.destinationPoint = destinationPoint;
        this.date = date;
        this.time = time;
        this.isDriver = isDriver;
        this.userDeviceToken = userDeviceToken;
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

    public void setDeparturePoint(Location departurePoint) {
        this.departurePoint = departurePoint;
    }

    public Location getDeparturePoint() {
        return departurePoint;
    }

    public void setDestinationPoint(Location destinationPoint) {
        this.destinationPoint = destinationPoint;
    }

    public Location getDestinationPoint() {
        return destinationPoint;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTime() {
        return time;
    }

    public void setUserDeviceToken(String userDeviceToken) {
        this.userDeviceToken = userDeviceToken;
    }

    public String getUserDeviceToken() {
        return userDeviceToken;
    }
}
