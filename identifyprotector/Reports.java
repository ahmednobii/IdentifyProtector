package com.identifyprotector.identifyprotector;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class Reports extends AppCompatActivity {
    private static final String TAG
            = "Reports";
    RecyclerView list;
    AppDataBase appDataBase;
    List<Information> informationList = new ArrayList<>();
    List<ReportList> reportLists = new ArrayList<>();
    List<Breach> breachList = new ArrayList<>();
    List<CreditCardBreach> creditCardBreaches = new ArrayList<>();
    List<creditcard> creditCardList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.reports_iterface);

        Button logout = (Button) findViewById(R.id.out);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SaveSharedPreference.logout(Reports.this);
                Intent intent = new Intent(Reports.this, LoginFragment.class);
                startActivity(intent);
                finish();

            }
        });

        appDataBase = new AppDataBase(this);
        list = (RecyclerView) findViewById(R.id.recyclerViewReports);
        informationList = new ArrayList<Information>();

    }

    @Override
    protected void onStart() {
        super.onStart();
        ReportsAdapter reportsAdapter;
        reportLists.clear();
        breachList.clear();
        informationList.clear();

        reportsAdapter = new ReportsAdapter(reportLists);
        reportsAdapter.clearData();
        fitchData();
        getCreditCards();
/*if (!informationList.isEmpty()){
for (int i = 0; i<=informationList.size()-1;i++) {
    Log.d(TAG, "onStart: " + informationList.get(i).geteMAil2()+informationList.get(i).geteMail1()+informationList.get(i).getFirstName()+informationList.size());
}   }
else Toast.makeText(this,"null",Toast.LENGTH_SHORT).show();*/
        if (!reportLists.isEmpty()) {
            reportsAdapter = new ReportsAdapter(reportLists);
            list.setAdapter(reportsAdapter);
            list.setLayoutManager(new LinearLayoutManager(this));
            Log.d(TAG, "onStart: ");

        for (ReportList reportList : reportLists){
            Log.d(TAG, "onStart: report "+reportList.getEmail()+reportList.getProfileCategory());
        }
        }
    }

    private void fitchData() {
        Cursor cursor = appDataBase.getDataInformation();
        informationList.clear();
        reportLists.clear();
        int i = 0;
        if (cursor != null) {
            while (cursor.moveToNext()) {
                Information information = new Information(cursor.getInt(0), cursor.getString(1),
                        cursor.getString(2), cursor.getString(3), cursor.getString(4)
                        , cursor.getString(5), cursor.getString(6), cursor.getString(7)
                        , cursor.getString(8), cursor.getString(9), cursor.getString(10)
                        , cursor.getString(11), cursor.getString(12), cursor.getString(13),
                        cursor.getString(14), cursor.getString(15));
                informationList.add(i, information);
                if (!information.geteMail1().isEmpty()) {
                    Log.d(TAG, "fitchData: " + information.geteMail1() + "    i : " + i);

                    String res = getBreachesEmail(information.geteMail1(), i);
                    getIDBreaches(information.getNationalID(),i);
                    getPhoneBreaches(information.getPhoneNumber1(),i);
                    Log.d(TAG, "Loop " + information.geteMail1() + "Res" + res);
                }
                i++;

            }
            Toast.makeText(getBaseContext(), "Size", Toast.LENGTH_SHORT).show();

        } else Toast.makeText(getBaseContext(), "null", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "fitchData: " + informationList.size());
    }

    public String getBreachesEmail(String email, int i) {

        Cursor cursorBreaches = appDataBase.fetchBreachbyMail(email);
        ReportList reportList;
        int j = 0;

        int k = 0;
        while (cursorBreaches.moveToNext()) {
            Breach breach = new Breach(cursorBreaches.getString(1), cursorBreaches.getString(2)
                    , cursorBreaches.getString(3), cursorBreaches.getString(4),
                    cursorBreaches.getString(5), cursorBreaches.getInt(0));
            breachList.add(j, breach);
            Log.d(TAG, "getBreachesEmail: ");
            j++;
        }
        Log.d(TAG, "fitchBreach: " + breachList.size());
        if (!breachList.isEmpty()) {
            reportList = new ReportList(informationList.get(i).getProfileName(), "Information", breachList.get(0).getAccount());
            reportLists.add(reportList);

            Log.d(TAG, "fitchData: reportlist " + k + reportLists.get(k).getEmail());

            k = k + 1;

            breachList.clear();
            Log.d(TAG, "fitchData:breachlis " + breachList.size());
            return "True";
        }

        email = null;
        Log.d(TAG, "fitchBreachesEmail: re size" + reportLists.size());
        return "false";
    }


    public void getCreditCards() {
        Cursor cursor = appDataBase.getDataCreditCard();
        creditCardList.clear();
        int i = 0;
        if (cursor != null) {
            while (cursor.moveToNext()) {
                creditcard creditCard = new creditcard(cursor.getInt(0), cursor.getString(1),
                        cursor.getString(2), cursor.getString(3), cursor.getString(4)
                        , cursor.getString(5), cursor.getString(6), cursor.getString(7)
                        , cursor.getString(8), cursor.getString(9), cursor.getString(10)
                        , cursor.getString(11), cursor.getString(12), cursor.getString(13),
                        cursor.getInt(14));
                if (!creditCard.getCreditNum().isEmpty())

                Log.d(TAG, "getCreditCards: "+creditCard.getAcc()+i);
                creditCardList.add(i, creditCard);
                getCreditCardBreaches(creditCard.getAcc(),i);
i++ ;
            }
            Log.d(TAG, "getCreditCards:size "+creditCardList.size());
        }
    }

    public void getCreditCardBreaches(String number,int k ) {
        Cursor cursor = appDataBase.fetchCreditCardBreachbynumber(number);
        creditCardBreaches.clear();
        int u = 0;
        int j = 0 ;
        if (cursor != null) {
            while (cursor.moveToNext()) {
                CreditCardBreach creditCardBreach = new CreditCardBreach(cursor.getInt(0), cursor.getString(1),
                        cursor.getString(2), cursor.getString(3), cursor.getString(4));
                creditCardBreaches.add(u, creditCardBreach);
                u++;

            }
            Log.d(TAG, "getCreditCardBreaches:size "+creditCardBreaches.size()+k);
        if(!creditCardBreaches.isEmpty()){
                if(! reportLists.isEmpty()){
                    int t = reportLists.size() ;
                   ReportList reportList = new ReportList(creditCardList.get(k).getNickname(),"CreditCard",creditCardList.get(k).getCreditNum());
                   reportLists.add(t,reportList);
               // creditCardBreaches.clear();
                    Log.d(TAG, "getCreditCardBreaches: reportList if "+reportLists.size());
                }else {
                    ReportList reportList = new ReportList(creditCardList.get(0).getNickname(),"CreditCard",creditCardList.get(0).getCreditNum());

                    Log.d(TAG, "getCreditCardBreaches: reportList"+reportLists.size());
                   reportLists.add(j,reportList);
                    j++ ;
                }

        }

        }


    }

    public void getIDBreaches(String number,int k ) {
        Cursor cursor = appDataBase.fetchIDBreachbynumber(number);
        creditCardBreaches.clear();
        int u = 0;
        int j = 0 ;
        if (cursor != null) {
            while (cursor.moveToNext()) {
                CreditCardBreach creditCardBreach = new CreditCardBreach(cursor.getInt(0), cursor.getString(1),
                        cursor.getString(2), cursor.getString(3), cursor.getString(4));
                creditCardBreaches.add(u, creditCardBreach);
                u++;

            }
            Log.d(TAG, "getCreditCardBreaches:size "+creditCardBreaches.size()+k);
            if(!creditCardBreaches.isEmpty()){
                if(! reportLists.isEmpty()){
                    int t = reportLists.size() ;
                    ReportList reportList = new ReportList(informationList.get(k).getProfileName(),"IDInformation",informationList.get(k).getNationalID());
                    reportLists.add(t,reportList);
                    creditCardBreaches.clear();
                    Log.d(TAG, "getCreditCardBreaches: reportList if "+reportLists.size());
                }else {
                    ReportList reportList = new ReportList(informationList.get(k).getProfileName(),"IDInformation",informationList.get(k).getNationalID());

                    Log.d(TAG, "getCreditCardBreaches: reportList"+reportLists.size());
                    reportLists.add(j,reportList);
                    j++ ;
                }

            }

        }


    }

    public void getPhoneBreaches(String number,int k ) {
        Cursor cursor = appDataBase.fetchPhoneBreachbynumber(number);
        creditCardBreaches.clear();
        int u = 0;
        int j = 0 ;
        if (cursor != null) {
            while (cursor.moveToNext()) {
                CreditCardBreach creditCardBreach = new CreditCardBreach(cursor.getInt(0), cursor.getString(1),
                        cursor.getString(2), cursor.getString(3), cursor.getString(4));
                creditCardBreaches.add(u, creditCardBreach);
                Log.d(TAG, "getPhoneBreaches: "+creditCardBreach.getLink());
                u++;

            }
            Log.d(TAG, "getCreditCardBreaches:size "+creditCardBreaches.size()+k);
            if(!creditCardBreaches.isEmpty()){
                if(! reportLists.isEmpty()){
                    int t = reportLists.size() ;
                    ReportList reportList = new ReportList(informationList.get(k).getProfileName(),"InformationPhone",informationList.get(k).getPhoneNumber1());
                    reportLists.add(reportList);
                    // creditCardBreaches.clear();
                    Log.d(TAG, "getCreditCardBreaches: reportList if "+reportLists.size());
                }else {
                    ReportList reportList = new ReportList(informationList.get(k).getProfileName(),"InformationPhone",informationList.get(k).getPhoneNumber1());

                    Log.d(TAG, "getCreditCardBreaches: reportList"+reportLists.size());
                    reportLists.add(j,reportList);
                    j++ ;
                }

            }

        }


    }

}