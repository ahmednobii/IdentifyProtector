package com.identifyprotector.identifyprotector;

import java.util.ArrayList;
import java.util.List;

public class ReportList {
    private String profileName ;
    private String profileCategory ;

    private  String email ;
    public ReportList(String profileName, String profileCategory, String email) {
        this.profileName = profileName;
        this.profileCategory = profileCategory;
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getProfileName() {
        return profileName;
    }

    public void setProfileName(String profileName) {
        this.profileName = profileName;
    }

    public String getProfileCategory() {
        return profileCategory;
    }

    public void setProfileCategory(String profileCategory) {
        this.profileCategory = profileCategory;
    }

    }


