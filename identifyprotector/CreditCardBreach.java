package com.identifyprotector.identifyprotector;

public class CreditCardBreach  {
private String creditCard;
    private String title;
    private String link;
private int id ;
private String profileName ;

    public String getProfileName() {
        return profileName;
    }

    public void setProfileName(String profileName) {
        this.profileName = profileName;
    }

    public CreditCardBreach(String creditCard, String title, String link, String profileName) {
        this.creditCard = creditCard;
        this.title = title;
        this.link = link;
        this.profileName = profileName;
    }

    public CreditCardBreach(int id, String creditCard, String title, String link, String profileName) {
        this.creditCard = creditCard;
        this.title = title;
        this.link = link;
        this.id = id;
        this.profileName = profileName;
    }

    public String getCreditCard() {
        return creditCard;
    }

    public CreditCardBreach() {
    }

    public void setCreditCard(String creditCard) {
        this.creditCard = creditCard;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
