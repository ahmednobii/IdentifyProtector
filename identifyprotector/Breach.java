package com.identifyprotector.identifyprotector;

import android.os.Parcel;
import android.os.Parcelable;

public class Breach implements Parcelable {
    private String name;
    private String title;
    private String description;
    private String account;
private  String profileName ;

    public String getProfileName() {
        return profileName;
    }

    public void setProfileName(String profileName) {
        this.profileName = profileName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private int id ;
    public Breach(String name, String title, String description, String account  ) {
        this.name = name;
        this.title = title;
        this.description = description;
        this.account = account;
    }

    public Breach(String name, String title, String description, String account ,String profileName ) {
        this.name = name;
        this.title = title;
        this.description = description;
        this.account = account;
    this.profileName = profileName ;
    }

    public Breach( String profileName,String account,String name, String title, String description,  int id) {
        this.name = name;
        this.title = title;
        this.description = description;
        this.account = account;
        this.profileName = profileName;
        this.id = id;
    }

    public static final Parcelable.Creator<Breach> CREATOR = new Creator<Breach>() {
        @Override
        public Breach createFromParcel(Parcel in) {
            return new Breach(in);
        }

        @Override
        public Breach[] newArray(int size) {
            return new Breach[size];
        }
    };

    public String getName() {
        return name;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getAccount() {
        return account;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeString(name);
        out.writeString(title);
        out.writeString(description);
        out.writeString(account);
        out.writeString(profileName);
        out.writeInt(id);
    }
    private Breach(Parcel in) {
        this.name = in.readString();
        this.title = in.readString();
        this.description = in.readString();
        this.account = in.readString();
    this.profileName = in.readString() ;
   this.id = in.readInt() ;
    }
}
