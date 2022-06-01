package com.podorozhnik.entities;

public class User {
    private String login;
    private String password;
    private String deviceToken;

    public User()
    {}

    public User(String login, String password, String deviceToken){
        this.login = login;
        this.password = password;
        this.deviceToken = deviceToken;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setDeviceToken(String deviceToken) {
        this.deviceToken = deviceToken;
    }

    public String getDeviceToken() {
        return deviceToken;
    }
}
