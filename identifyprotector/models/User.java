package com.identifyprotector.identifyprotector.models;


public class User {

    private String username;
    private String email;
    private String password;
    private String code;
    private String duration;
    private String ActivateAlert;


    public String getActivateAlert() {
        return ActivateAlert;
    }

    public void setActivateAlert(String activateAlert) {
        ActivateAlert = activateAlert;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getName() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public void setName(String name) {
        this.username = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setCode(String code) {
        this.code = code;
    }

}
