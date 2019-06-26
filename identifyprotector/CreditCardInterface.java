package com.identifyprotector.identifyprotector;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class CreditCardInterface extends Activity {

    private static final String TAG ="CreditCardInterFace" ;
    private EditText firstName ;
    private EditText cardNumber ;
    private EditText secureCode ;
    private EditText bankName ;
    private EditText accountNumber ;
    private EditText cardOwner ;
    private EditText expirDate ;
    private EditText issueingDate ;
    private EditText phoneNumber ;
    private EditText country;
    private EditText city ;
    private EditText street ;
    private EditText ZipCode ;
    private CheckBox mState ;
    private FloatingActionButton mSave ;
AppDataBase appDataBase ;
    CreditCard creditCard ;
@Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.creditcard_iterface);
    firstName = (EditText) findViewById(R.id.creditCardFirstName);
        cardNumber = (EditText) findViewById(R.id.creditCardNumber);
        secureCode = (EditText) findViewById(R.id.creditCardSecureCode);
        bankName = (EditText) findViewById(R.id.creditCardBankName);
        accountNumber = (EditText) findViewById(R.id.creditCardAccountNumber);
        cardOwner = (EditText) findViewById(R.id.creditCardCardOwner);
        expirDate = (EditText) findViewById(R.id.creditCardExpirationDate);
        issueingDate = (EditText) findViewById(R.id.creditCardIssuingDate );
        phoneNumber = (EditText) findViewById(R.id.creditCardPhoneNumber);
        country = (EditText) findViewById(R.id.creditCardCountry);
        city = (EditText) findViewById(R.id.creditCardCity);
        street = (EditText) findViewById(R.id.creditCardStreet);
        ZipCode = (EditText) findViewById(R.id.creditCardZipCode);
        mState = (CheckBox) findViewById(R.id.creditCardCheck);
        mSave = (FloatingActionButton) findViewById(R.id.creditCardSave);
    mState.setChecked(true);
appDataBase = new AppDataBase( this) ;
}

    @Override
    protected void onStart() {

        super.onStart();
    mSave.setOnClickListener(new View.OnClickListener() {
        int i ;

        @Override
        public void onClick(View v) {
        if (!firstName.getText().toString().isEmpty())
        {
            if (mState.isChecked()) {
                i = 1;
                new PerformSearchTask().execute(cardNumber.getText().toString().trim()) ;
            }else i = 0 ;

            creditCard = new CreditCard(firstName.getText().toString(),cardNumber.getText().toString(),
                secureCode.getText().toString(),bankName.getText().toString(),accountNumber.getText().toString(),cardOwner.getText().toString()
        ,expirDate.getText().toString() , issueingDate.getText().toString(),phoneNumber.getText().toString(), country.getText().toString(),
                city.getText().toString(),street.getText().toString(), ZipCode.getText().toString(),i);
        appDataBase.addCreditCard(creditCard);
        }
     else Toast.makeText(getBaseContext(),"Write the first Name",Toast.LENGTH_SHORT).show();

        }

    });

    }

    public class  PerformSearchTask extends AsyncTask<String, Void, ArrayList<CreditCardBreach>> {
        protected ArrayList<CreditCardBreach> doInBackground(String... accounts) {
            ArrayList<CreditCardBreach> result = new ArrayList<>();
            CreditCardBreach creditCardBreach =new CreditCardBreach();
            String urlApi = "https://www.googleapis.com/customsearch/v1?q=";
            String key = "AIzaSyDkaSeJ8cNr8YqTkgK6neaybOTqwIBlduM" ;
            String customSearch = "004878726315063446531:jq4smo4-n3a";
            URL url = null;
            try {
                url = new URL(urlApi+accounts[0]+"&key="+key+"&cx="+customSearch+"&alt=json&fields=items");
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            Log.d(TAG, "query: 11"+url);
            // URI uri =new URI(url.getProtocol(),url.getHost(),url.getPath(),url.getRef()) ;
            //url = uri.toURL() ;
            Log.d(TAG, "query: 22"+url);
            final HttpURLConnection connection;
            try {
                assert url != null;
                connection = (HttpURLConnection) url.openConnection();

                connection.setRequestMethod("GET");
                connection.connect();


                if (connection.getResponseCode() == HttpURLConnection.HTTP_OK ) {
                    Log.d(TAG, "query: " + connection.getResponseCode());
                    InputStream inputStream = new BufferedInputStream(connection.getInputStream());
                    BufferedReader r = new BufferedReader(new InputStreamReader(inputStream));
                    StringBuilder stringBuilder = new StringBuilder();
                    String line ;
                    int j ,i ;
                    i = 0 ;
                    j = 0 ;
                    while ((line = r.readLine())!= null){
                        if (!line.contains("{"))
                        {if (line.contains("title")){
                            stringBuilder.append(line).append("\n");
                            Log.d(TAG, "doInBackground: "+stringBuilder.toString());
                            j++;
String t = line.replaceAll("\"title\":","");
                            creditCardBreach.setTitle(t);
                        } if (line.contains("link")){

                            creditCardBreach.setLink(line);
                            j++ ;
                        }
                            if ( j == 2 )
                            {
                                creditCardBreach.setCreditCard(accounts[0]);
                                creditCardBreach.setProfileName(firstName.getText().toString());
                                result.add(i,creditCardBreach);
                                appDataBase.addCardBreaches(result.get(i));
                                Log.d(TAG, "doInBackground: Array "+"  i "+i+result.get(i).getTitle());
                                i++ ;
                                j = 0 ;
                            }
                        }
                         } }}
            catch (IOException e) {
                e.printStackTrace();
            }

                     return result;
        }


        protected void onPostExecute(ArrayList<CreditCardBreach> result) {

            // Create the history card if not already created by FetchHistoryTask

            if (result == null) {
                Toast.makeText(getBaseContext(), getString(R.string.error_result_null), Toast.LENGTH_SHORT).show();
            } else {
                Log.d(TAG, "onPostExecute: " + result.size() );
                for (int i = 0 ; i< result.size()-1;i++ ) {
                    Log.d(TAG, "onPostExecute: " +i+ result.size() + result.get(i).getTitle()) ;
                    //               appDataBase.addCardBreaches(result.get(i));
                }



                }
            }
        }


    }


