package com.identifyprotector.identifyprotector;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class AppDataBase extends SQLiteOpenHelper {

    private static final String TAG = "AppDataBase";
    private static final String dataBaseName = "IdentityProtector.db";
    // table for breaches for mails

    private static final String breachesMailsTable = "BreachMails";
    private static final String mails_Id = "ID";
    private static final String mail_webName = "Name";
    private static final String mail_webTitle = "Title";
    private static final String mail_webDescription = "description";
    private static final String mail_profileName = "profileName";
    private static final String mail_webAccount = "Account";
    // information DB
    private static final String informationTable = "Information";
    private static final String information_id = "ID";
    private static final String profileName = "ProfileName";
    private static final String firstName = "firstName";
    private static final String middleName = "middleName";
    private static final String lastName = "lastName";
    private static final String DOB = "DOB";
    private static final String nationalID = "nationalID";
    private static final String PassportNumber = "passportNumber";
    private static final String eMail1 = "Mail";
    private static final String eMAil2 = "additionalMail";
    private static final String phoneNumber1 = "phoneNumber";
    private static final String phoneNumber2 = "additionalPhoneNumber";
    private static final String country = "country";
    private static final String city = "city";
    private static final String street = "street";
    private static final String ZipCode = "zipCode";
    //creditcard DB

    private static final String creditCardTable = "CreditCard";
    private static final String creditCard_id = "ID";
    private static final String creditCard_firstName = "firstName";
    private static final String creditCard_cardNumber = "CardNumber";
    private static final String creditCard_secureCode = "SecureCode";
    private static final String creditCard_bankName = "BankName";
    private static final String creditCard_AccountNumber = "AccountNumber";
    private static final String creditCard_CardOwner = "CardOwner" ;
    private static final String creditCard_expirationDate = "ExpirationDate";
    private static final String creditCard_IssuingDate = "IssuingDate";
    private static final String creditCard_phoneNumber = "phoneNumber";
    private static final String creditCard_country = "country";
    private static final String creditCard_city = "city";
    private static final String creditCard_street = "street";
    private static final String creditCard_ZipCode = "zipCode";
    private static final String creditCard_State = "State";

    //Create db of creditcard Breaches
    private static final String creditCardBreach = "CreditCardBreaches";
    private static final String creditCardBreach_id = "ID";
    private static final String creditCardBreach_creditCardNumber = "CreditCardNumber";
    private static final String creditCardBreach_title = "Title";
    private static final String creditCardBreach_link = "Link";
    private static final String creditCardBreach_profileName = "ProfileName";

    //Create db of creditcard Breaches
    private static final String IDBreach = "IDsBreaches";
    private static final String IDBreach_id = "ID";
    private static final String IDBreach_IDNumber = "IDNumber";
    private static final String IDBreach_title = "Title";
    private static final String IDBreach_link = "Link";
    private static final String IDBreach_profileName = "ProfileName";

    //Create db of creditcard Breaches
    private static final String PhonerNumberBreach = "PhonerNumberBreaches";
    private static final String PhonerNumberBreach_id = "ID";
    private static final String PhonerNumberBreach_PhoneNumber = "PHONENumber";
    private static final String PhonerNumberBreach_title = "Title";
    private static final String PhonerNumberBreach_link = "Link";
    private static final String PhonerNumberBreach_profileName = "ProfileName";
    // Create DB Login credentials
    // Create DB Login credentials
private static final String loginCredentialsTable = "loginCredentials";
    private static final String loginCredentials_id = "ID";
    private static final String loginCredentials_profileName = "profileName";
    private static final String loginCredentials_appName = "appName";
    private static final String loginCredentials_userName = "UserName";
    private static final String loginCredentials_password = "password";
    private static final String loginCredentials_state = "state";

    AppDataBase(Context context) {
        super(context, dataBaseName, null, 1);
        Log.d(TAG, "AppDataBase: ");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String create_breachesMailsTable = "CREATE TABLE " + breachesMailsTable + "( " +
                mails_Id + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE , " +
                mail_profileName + " TEXT ," + mail_webAccount + " TEXT ," + mail_webName + " TEXT," + mail_webTitle + " TEXT ," +
                mail_webDescription + " TEXT )";
        String create_InfromationTable = "CREATE TABLE " + informationTable + " ( "
                + information_id + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE, "
                + profileName + " TEXT, " + firstName + " TEXT, " + middleName + " TEXT ,"
                + lastName + " TEXT," + DOB + " TEXT, " + nationalID + " TEXT ,"
                + PassportNumber + " TEXT, " + eMail1 + " TEXT," + eMAil2 + " TEXT ,"
                + phoneNumber1 + " TEXT, " + phoneNumber2 + " TEXT, " + country + " TEXT ,"
                + city + " TEXT, " + street + " TEXT, " + ZipCode + " TEXT)";
        String create_creditCard = "CREATE TABLE " + creditCardTable + " ( "
                + creditCard_id + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE, "
                + creditCard_firstName + " TEXT, " + creditCard_cardNumber + " TEXT, " + creditCard_secureCode + " TEXT ,"
                + creditCard_bankName + " TEXT," + creditCard_AccountNumber + " TEXT, " + creditCard_CardOwner + " TEXT , "+ creditCard_expirationDate + " TEXT ,"
                + creditCard_IssuingDate + " TEXT, " + creditCard_phoneNumber + " TEXT,"
                + creditCard_country + " TEXT ," + creditCard_city + " TEXT, " + creditCard_street + " TEXT, " +
                creditCard_ZipCode + " TEXT," + creditCard_State + " INTEGR )";

        String create_CreditBreaches = "CREATE TABLE " + creditCardBreach + " ( "
                + creditCardBreach_id + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE, " + creditCardBreach_creditCardNumber + " TEXT ," +
                creditCardBreach_title + " TEXT , " + creditCardBreach_link + " TEXT , " + creditCardBreach_profileName + " TEXT ) ";

        String create_LoginCredentials = "CREATE TABLE " + loginCredentialsTable + " ( "
                + loginCredentials_id + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE, " + loginCredentials_profileName + " TEXT ," +
                loginCredentials_appName + " TEXT , " + loginCredentials_userName + " TEXT , " + loginCredentials_password + " TEXT , "+
                loginCredentials_state + " INTEGER )";

        String create_IDBreaches = "CREATE TABLE " + IDBreach + " ( "
                + IDBreach_id + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE, " + IDBreach_IDNumber + " TEXT ," +
                IDBreach_title + " TEXT , " + IDBreach_link + " TEXT , " + IDBreach_profileName + " TEXT ) ";

        String create_PhoneNumbersBreaches = "CREATE TABLE " + PhonerNumberBreach + " ( "
                + PhonerNumberBreach_id + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE, " + PhonerNumberBreach_PhoneNumber + " TEXT ," +
                PhonerNumberBreach_title + " TEXT , " + PhonerNumberBreach_link + " TEXT , " + PhonerNumberBreach_profileName + " TEXT ) ";

        db.execSQL(create_breachesMailsTable);
        db.execSQL(create_InfromationTable);
        db.execSQL(create_creditCard);
        db.execSQL(create_CreditBreaches);
    db.execSQL(create_LoginCredentials);
        db.execSQL(create_PhoneNumbersBreaches);
        db.execSQL(create_IDBreaches);
        Log.d(TAG, "onCreate: done");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF  EXISTS " + breachesMailsTable);
        db.execSQL("DROP TABLE IF  EXISTS " + informationTable);
        db.execSQL("DROP TABLE IF  EXISTS " + creditCardTable);
        db.execSQL("DROP TABLE IF  EXISTS " + creditCardBreach);
        db.execSQL("DROP TABLE IF  EXISTS " + loginCredentialsTable);
        db.execSQL("DROP TABLE IF  EXISTS " + IDBreach);
        db.execSQL("DROP TABLE IF  EXISTS " + PhonerNumberBreach);
        onCreate(db);
        Log.d(TAG, "onUpgrade: droped");
    }

    public Boolean addBreaches(Breach breach) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(mail_profileName, breach.getProfileName());
        contentValues.put(mail_webAccount, breach.getAccount());
        contentValues.put(mail_webName, breach.getName());
        contentValues.put(mail_webTitle, breach.getTitle());
        contentValues.put(mail_webDescription, breach.getDescription());
//contentValues.put(mail_profileName,breach.getProfileName());
        long result = db.insert(breachesMailsTable, null, contentValues);
        Log.d(TAG, "addItem: " + result);
        if (result != -1) {
            Log.d(TAG, "addBreaches: " + result);
            return true;
        } else {
            Log.d(TAG, "addBreaches: " + result);
            return false;
        }

    }

    public boolean addInformation(Information information) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(profileName, information.getProfileName());
        contentValues.put(firstName, information.getFirstName());
        contentValues.put(middleName, information.getMiddleName());
        contentValues.put(lastName, information.getLastName());
        contentValues.put(DOB, information.getDOB());
        contentValues.put(nationalID, information.getNationalID());
        contentValues.put(PassportNumber, information.getPassportNumber());
        contentValues.put(eMail1, information.geteMail1());
        contentValues.put(eMAil2, information.geteMAil2());
        contentValues.put(phoneNumber1, information.getPhoneNumber1());
        contentValues.put(phoneNumber2, information.getPhoneNumber2());
        contentValues.put(country, information.getCountry());
        contentValues.put(city, information.getCity());
        contentValues.put(street, information.getStreet());
        contentValues.put(ZipCode, information.getZipCode());


        long result = db.insert(informationTable, null, contentValues);

        Log.d(TAG, "addInformation " + result);
        if (result != -1) {
            Log.d(TAG, "addInformation: " + result);
            return true;
        } else {
            Log.d(TAG, "addInformation: " + result);
            return false;
        }

    }

    public Cursor getDataBreaches() {
        SQLiteDatabase db = this.getWritableDatabase();
        String[] queryData = {mails_Id, mail_webName, mail_webTitle, mail_webDescription, mail_webAccount};
        Cursor cursor = db.rawQuery("SELECT * FROM " + breachesMailsTable, null);
        Log.d(TAG, "getData:d ");
        return cursor;
    }

    public Cursor fetchBreachbyMail(String mail) {
        Cursor row = null;
        Log.d(TAG, "fetchdatabyId: done");
        SQLiteDatabase db = this.getWritableDatabase();
        String query;

        query = "SELECT * FROM " + breachesMailsTable + " WHERE " + mail_webAccount + " like '%" + mail + "%'";
        row = db.rawQuery(query, null);


        row.moveToFirst();

        return row;
    }


    public Cursor getDataInformation() {
        SQLiteDatabase db = this.getWritableDatabase();
        String[] queryData = {information_id, profileName, firstName, middleName, lastName, DOB, nationalID, PassportNumber
                , eMail1, eMAil2, phoneNumber1, phoneNumber2, country, city, street, ZipCode};
        Cursor cursor = db.rawQuery("SELECT * FROM " + informationTable, null);
        Log.d(TAG, "getData:d ");
        return cursor;
    }

    public boolean addCreditCard(creditcard creditCard) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(creditCard_firstName, creditCard.getNickname());
        contentValues.put(creditCard_cardNumber, creditCard.getCreditNum());
        contentValues.put(creditCard_secureCode, creditCard.getSecureCode());
        contentValues.put(creditCard_bankName, creditCard.getBankName());
        contentValues.put(creditCard_AccountNumber, creditCard.getAcc());
        contentValues.put(creditCard_CardOwner, creditCard.getCardOwner());
        contentValues.put(creditCard_expirationDate, creditCard.getExpDate());
        contentValues.put(creditCard_IssuingDate, creditCard.getIssDate());
        contentValues.put(creditCard_phoneNumber, creditCard.getPhoneNum());
        contentValues.put(creditCard_country, creditCard.getCountry());
        contentValues.put(creditCard_city, creditCard.getCity());
        contentValues.put(creditCard_street, creditCard.getStreet());
        contentValues.put(creditCard_ZipCode, creditCard.getZipCode());
        contentValues.put(creditCard_State,Integer.valueOf(creditCard.getActivateMonitoring()));


        long result = db.insert(creditCardTable, null, contentValues);

        Log.d(TAG, "addCreditCard " + result);
        if (result != -1) {
            Log.d(TAG, "addCreditCard: " + result);
            return true;
        } else {
            Log.d(TAG, "addCreditCard: " + result);
            return false;
        }


    }

    public Boolean addCardBreaches(CreditCardBreach breach) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(creditCardBreach_creditCardNumber, breach.getCreditCard());
        contentValues.put(creditCardBreach_title, breach.getTitle());
        contentValues.put(creditCardBreach_link, breach.getLink());
        contentValues.put(creditCardBreach_profileName, breach.getProfileName());

        long result = db.insert(creditCardBreach, null, contentValues);
        Log.d(TAG, "addItem: " + result);
        if (result != -1) {
            Log.d(TAG, "addBreaches: " + result);
            return true;
        } else {
            Log.d(TAG, "addBreaches: " + result);
            return false;
        }


    }
    public Cursor fetchCreditCardBreachbynumber(String number) {
        Cursor row = null;
        Log.d(TAG, "fetchdatabyId: done");
        SQLiteDatabase db = this.getWritableDatabase();
        String query;

        query = "SELECT * FROM " + creditCardBreach + " WHERE " + creditCardBreach_creditCardNumber + " like '%" + number +
                "%'";
        row = db.rawQuery(query, null);


        row.moveToFirst();

        return row;
    }

    public Cursor getDataCreditCard() {
        SQLiteDatabase db = this.getWritableDatabase();
        String[] queryData = {creditCard_id, creditCard_firstName, creditCard_cardNumber, creditCard_secureCode,
                creditCard_bankName, creditCard_AccountNumber, creditCard_expirationDate, creditCard_IssuingDate
                , creditCard_phoneNumber, creditCard_country, creditCard_city, creditCard_street, creditCard_ZipCode};
        Cursor cursor = db.rawQuery("SELECT * FROM " + creditCardTable , null);
        Log.d(TAG, "getData:d ");
        return cursor;
    }

    public Boolean addLoginCredential(LoginCredential loginCredential) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(loginCredentials_profileName, loginCredential.getProfileName());
        contentValues.put(loginCredentials_appName, loginCredential.getAppName());
        contentValues.put(loginCredentials_userName, loginCredential.getUserName());
        contentValues.put(loginCredentials_password, loginCredential.getPassword());
        contentValues.put(loginCredentials_state, loginCredential.getState());
        long result = db.insert(loginCredentialsTable, null, contentValues);
        Log.d(TAG, "addItem: " + result);
        if (result != -1) {
            Log.d(TAG, "addLoginCredenials: " + result);
            return true;
        } else {
            Log.d(TAG, "not Added loginCredentials " + result);
            return false;
        }


    }
    public void removeAllCreditCardBreaches()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM "+ creditCardBreach); //delete all rows in a table
        db.close();
        Log.d(TAG, "removeAllCreditCardBreaches: ");
    }


    public void removeAllInformationBreaches()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM "+ breachesMailsTable); //delete all rows in a table
        db.close();
        Log.d(TAG, "removeAllInformationBreaches: ");
    }
    public Boolean addIDBreaches(CreditCardBreach breach) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(IDBreach_IDNumber, breach.getCreditCard());
        contentValues.put(IDBreach_title, breach.getTitle());
        contentValues.put(IDBreach_link, breach.getLink());
        contentValues.put(IDBreach_profileName, breach.getProfileName());

        long result = db.insert(IDBreach, null, contentValues);
        Log.d(TAG, "addItem: " + result);
        if (result != -1) {
            Log.d(TAG, "addBreaches: " + result);
            return true;
        } else {
            Log.d(TAG, "addBreaches: " + result);
            return false;
        }


    }
    public Boolean addPhoneBreaches(CreditCardBreach breach) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(PhonerNumberBreach_PhoneNumber, breach.getCreditCard());
        contentValues.put(PhonerNumberBreach_title, breach.getTitle());
        contentValues.put(PhonerNumberBreach_link, breach.getLink());
        contentValues.put(PhonerNumberBreach_profileName, breach.getProfileName());

        long result = db.insert(PhonerNumberBreach, null, contentValues);
        Log.d(TAG, "addItem: " + result);
        if (result != -1) {
            Log.d(TAG, "addBreaches: " + result);
            return true;
        } else {
            Log.d(TAG, "addBreaches: " + result);
            return false;
        }


    }

    public Cursor fetchIDBreachbynumber(String number) {
        Cursor row = null;
        Log.d(TAG, "fetchdatabyId: done");
        SQLiteDatabase db = this.getWritableDatabase();
        String query;

        query = "SELECT * FROM " + IDBreach + " WHERE " + IDBreach_IDNumber + " like '%" + number +
                "%'";
        row = db.rawQuery(query, null);


        row.moveToFirst();

        return row;
    }

    public Cursor fetchPhoneBreachbynumber(String number) {
        Cursor row = null;
        Log.d(TAG, "fetchdatabyId: done");
        SQLiteDatabase db = this.getWritableDatabase();
        String query;

        query = "SELECT * FROM " + PhonerNumberBreach + " WHERE " + PhonerNumberBreach_PhoneNumber + " like '%" + number +
                "%'";
        row = db.rawQuery(query, null);


        row.moveToFirst();

        return row;
    }

}


