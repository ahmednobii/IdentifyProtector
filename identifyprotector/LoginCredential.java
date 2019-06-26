package com.identifyprotector.identifyprotector;

import android.widget.EditText;

public class LoginCredential {

    private int ID ;
    private String profileName;
private String appName ;
private String userName ;
private String password ;
private int State  ;

    public LoginCredential(String profileName, String appName, String userName, String password, int state) {
        this.profileName = profileName;
        this.appName = appName;
        this.userName = userName;
        this.password = password;
        State = state;
    }

    public LoginCredential(int ID, String profileName, String appName, String userName, String password, int state) {
        this.ID = ID;
        this.profileName = profileName;
        this.appName = appName;
        this.userName = userName;
        this.password = password;
        State = state;
    }

    public LoginCredential(String filename, EditText appname, String encyUser, String encyPass) {
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getProfileName() {
        return profileName;
    }

    public void setProfileName(String profileName) {
        this.profileName = profileName;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getState() {
        return State;
    }

    public void setState(int state) {
        State = state;
    }
}
