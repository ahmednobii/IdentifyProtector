package com.identifyprotector.identifyprotector;

public class Information {
private int id ;
private String profileName ;
private String firstName ;
private String middleName ;
private String lastName ;


    private String DOB ;
private  String nationalID ;
private String PassportNumber ;
private String eMail1 ;
private  String eMAil2 ;
private String phoneNumber1 ;
private String phoneNumber2 ;
private String country;
private String city ;
private String street ;
private String ZipCode ;
private int mState ;


public Information() { }

    public Information(String profileName, String firstName, String lastName, String nationalID,
                       String eMail1, String phoneNumber1, String country, String city, String street,
                       String zipCode) {
        this.profileName = profileName;
        this.firstName = firstName;
        this.lastName = lastName;
       this.nationalID = nationalID ;
        this.eMail1 = eMail1;
        this.phoneNumber1 = phoneNumber1;
        this.country = country;
        this.city = city;
        this.street = street;
        ZipCode = zipCode;
    }

    public String getProfileName() {
        return profileName;
    }

    public void setProfileName(String profileName) {
        this.profileName = profileName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getDOB() {
        return DOB;
    }

    public void setDOB(String DOB) {
        this.DOB = DOB;
    }

    public String getNationalID() {
        return nationalID;
    }

    public void setNationalID(String nationalID) {
        this.nationalID = nationalID;
    }

    public String getPassportNumber() {
        return PassportNumber;
    }

    public void setPassportNumber(String passportNumber) {
        PassportNumber = passportNumber;
    }

    public String geteMail1() {
        return eMail1;
    }

    public void seteMail1(String eMail1) {
        this.eMail1 = eMail1;
    }

    public String geteMAil2() {
        return eMAil2;
    }

    public void seteMAil2(String eMAil2) {
        this.eMAil2 = eMAil2;
    }

    public String getPhoneNumber1() {
        return phoneNumber1;
    }

    public void setPhoneNumber1(String phoneNumber1) {
        this.phoneNumber1 = phoneNumber1;
    }

    public String getPhoneNumber2() {
        return phoneNumber2;
    }

    public void setPhoneNumber2(String phoneNumber2) {
        this.phoneNumber2 = phoneNumber2;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getZipCode() {
        return ZipCode;
    }

    public void setZipCode(String zipCode) {
        ZipCode = zipCode;
    }

    public Information(int id, String profileName, String firstName, String middleName, String lastName, String DOB, String nationalID, String passportNumber, String eMail1, String eMAil2, String phoneNumber1, String phoneNumber2, String country, String city, String street, String zipCode) {
        this.id = id;
        this.profileName = profileName;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.DOB = DOB;
        this.nationalID = nationalID;
        PassportNumber = passportNumber;
        this.eMail1 = eMail1;
        this.eMAil2 = eMAil2;
        this.phoneNumber1 = phoneNumber1;
        this.phoneNumber2 = phoneNumber2;
        this.country = country;
        this.city = city;
        this.street = street;
        ZipCode = zipCode;
    }

    public Information(String profileName, String firstName, String middleName, String lastName, String DOB, String nationalID, String passportNumber, String eMail1, String eMAil2, String phoneNumber1, String phoneNumber2, String country, String city, String street, String zipCode) {
        this.profileName = profileName;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.DOB = DOB;
        this.nationalID = nationalID;
        PassportNumber = passportNumber;
        this.eMail1 = eMail1;
        this.eMAil2 = eMAil2;
        this.phoneNumber1 = phoneNumber1;
        this.phoneNumber2 = phoneNumber2;
        this.country = country;
        this.city = city;
        this.street = street;
        ZipCode = zipCode;
    }
}
