package com.identifyprotector.identifyprotector;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ReportDetails extends Activity {
    AppDataBase appDataBase;
    List<Breach> breachList;
    TextView reportDetails;
    List<CreditCardBreach> creditCardBreaches;
    private static final String TAG = "ReportDetails";
    Intent intent ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reports_interface_second);
        appDataBase = new AppDataBase(this);
        reportDetails = (TextView) findViewById(R.id.reportdatail);
        intent = getIntent();
        Log.d(TAG, "onCreate:profileCatg " + intent.getStringExtra("PCat"));
    }

    @Override
    protected void onStart() {
        super.onStart();
String try1 = null; 
        switch (intent.getStringExtra("PCat")) {
            case "Information":
               try1  = getBreachesEmail(intent.getStringExtra(ReportsAdapter.emailTag));
           //try1  =  intent.getStringExtra(ReportsAdapter.emailTag) +  intent.getStringExtra("PCat") ;
                Log.d(TAG, "onCreate: intent" + intent.getStringExtra(ReportsAdapter.emailTag));
                break;
            case "CreditCard":

             //   try1  =  intent.getStringExtra(ReportsAdapter.emailTag) +  intent.getStringExtra("PCat") ;
                 try1  =  getCreditCardBreaches(intent.getStringExtra(ReportsAdapter.emailTag));
                break;
            case "IDInformation":

               // try1  =  intent.getStringExtra(ReportsAdapter.emailTag) +  intent.getStringExtra("PCat") ;
              try1  =    getIDBreaches(intent.getStringExtra(ReportsAdapter.emailTag));
                break;
        }
        if (intent.getStringExtra("PCat").equals("InformationPhone"))
        {
//            try1  =  intent.getStringExtra(ReportsAdapter.emailTag) +  intent.getStringExtra("PCat") ;
            try1  =   getPhoneBreaches(intent.getStringExtra(ReportsAdapter.emailTag));
        }
        Log.d(TAG, "onCreate: rData "+ intent.getStringExtra(ReportsAdapter.emailTag)+intent.getStringExtra("PCat"));
reportDetails.setText(try1);
    }

    public String getBreachesEmail(String email) {
        int k = 0;
        Cursor cursorBreaches = appDataBase.fetchBreachbyMail(email);
        breachList = new ArrayList<>();
        String str = "";
        int j = 0;
        while (cursorBreaches.moveToNext()) {
            Breach breach = new Breach(cursorBreaches.getString(1), cursorBreaches.getString(2)
                    , cursorBreaches.getString(3), cursorBreaches.getString(4),
                    cursorBreaches.getString(5), cursorBreaches.getInt(0));

            breachList.add(j, breach);

            j++;
        }
        j = 0;
        for (j = 0; j < breachList.size(); j++) {
            k = j + 1;
            str = str + k + " web " + breachList.get(j).getName() + " \n" + " Title : " + breachList.get(j).getTitle() +
                    " \n" + " Description : " + breachList.get(j).getDescription() + "\n" + "\n";

        }
        return  str ;  }


    public String getCreditCardBreaches(String number) {
        Cursor cursor = appDataBase.fetchCreditCardBreachbynumber(number);
        creditCardBreaches = new ArrayList<>();
        int u = 0;
        int j = 0;
int k = 0 ;
        String str = "" ;
        if (cursor != null) {
            while (cursor.moveToNext()) {
                CreditCardBreach creditCardBreach = new CreditCardBreach(cursor.getInt(0), cursor.getString(1),
                        cursor.getString(2), cursor.getString(3), cursor.getString(4));
                creditCardBreaches.add(u, creditCardBreach);
                u++;

            }
        for ( j = 0 ; j < creditCardBreaches.size() ; j++){
                k = j +1 ;
                str = str + k + "  " + creditCardBreaches.get(j).getTitle()+ "\n"+ creditCardBreaches.get(j).getLink() + "\n"+ "\n" ;

            }
        }
        return str;
    }
    public String getIDBreaches(String number) {
        Cursor cursor = appDataBase.fetchIDBreachbynumber(number);
        creditCardBreaches = new ArrayList<>();
        creditCardBreaches.clear();
        int u = 0;
        int j = 0;
        int k = 0 ;
        String str = "" ;
        if (cursor != null) {
            while (cursor.moveToNext()) {
                CreditCardBreach creditCardBreach = new CreditCardBreach(cursor.getInt(0), cursor.getString(1),
                        cursor.getString(2), cursor.getString(3), cursor.getString(4));
                creditCardBreaches.add(u, creditCardBreach);
                u++;

            }
            for ( j = 0 ; j < creditCardBreaches.size() ; j++){
                k = j +1 ;
                str = str + k + "  " + creditCardBreaches.get(j).getTitle()+ "\n"+ creditCardBreaches.get(j).getLink() + "\n"+ "\n" ;

            }

            Log.d(TAG, "ReportDetaits ID "+creditCardBreaches.size()+str);

            }
        return str;
    }
    public String getPhoneBreaches(String number) {
        Cursor cursor = appDataBase.fetchPhoneBreachbynumber(number);
        creditCardBreaches = new ArrayList<>();
        int u = 0;
        int j = 0;
        int k = 0 ;
        String str = "" ;
        if (cursor != null) {
            while (cursor.moveToNext()) {
                CreditCardBreach creditCardBreach = new CreditCardBreach(cursor.getInt(0), cursor.getString(1),
                        cursor.getString(2), cursor.getString(3), cursor.getString(4));
                creditCardBreaches.add(u, creditCardBreach);
                u++;

            }
            for ( j = 0 ; j < creditCardBreaches.size() ; j++){
                k = j +1 ;
                str = str + k + "  " + creditCardBreaches.get(j).getTitle()+ "\n"+ creditCardBreaches.get(j).getLink() + "\n"+ "\n" ;

            }
            Log.d(TAG, "ReportDetaits "+creditCardBreaches.size()+str);

        }

    return  str ;}
}