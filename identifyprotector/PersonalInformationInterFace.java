package com.identifyprotector.identifyprotector;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;

public class PersonalInformationInterFace extends Activity {
    private static final String TAG = "InformationInterFace" ;
CheckBox checkBox ;
    ImageButton addMail,addPhoneeNumber ;
EditText profileName,firstName,middleName,LastnName , DOB,nationalID,passportNumber,eMail1 ,
        eMail2 ,phoneNumber1, phoneNumber2,country,city,street,zipCode ;
AppDataBase dataBase  ;
FloatingActionButton mSave ;
@Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    setContentView(R.layout.personal_information_interface);
addMail = (ImageButton) findViewById(R.id.information_addMail) ;
addPhoneeNumber = (ImageButton) findViewById(R.id.information_addPhoneNumber) ;
profileName = (EditText) findViewById(R.id.information_profileName); //1
firstName = (EditText) findViewById(R.id.information_firstName);     //2
middleName = (EditText) findViewById(R.id.information_middleeName); //3
LastnName = (EditText) findViewById(R.id.information_lastName);//4
DOB = (EditText) findViewById(R.id.information_DOB);//4
nationalID = (EditText) findViewById(R.id.information_NationalID);//5
passportNumber = (EditText) findViewById(R.id.information_passportNumber);//6
phoneNumber1 = (EditText) findViewById(R.id.information_phoneNumber1);//7
phoneNumber2 = (EditText) findViewById(R.id.information_phoneNumber2);//8
eMail1 = (EditText) findViewById(R.id.information_Email1);//9
eMail2 = (EditText) findViewById(R.id.information_Email2);//10
country = (EditText) findViewById(R.id.information_country);//11
city= (EditText) findViewById(R.id.information_city);//12
street = (EditText) findViewById(R.id.information_street);//13
zipCode = (EditText) findViewById(R.id.infromation_zipCode);//14
       dataBase  = new AppDataBase(getBaseContext()) ;
    mSave = (FloatingActionButton) findViewById(R.id.information_Save);
checkBox = (CheckBox)findViewById(R.id.information_checkBox) ;
checkBox.setChecked(true);
}

    @Override
    protected void onStart() {
        super.onStart();
addMail.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        if (eMail2.getVisibility()== View.GONE ) {
            eMail2.setVisibility(View.VISIBLE); }
        else eMail2.setVisibility(View.GONE); }});
addPhoneeNumber.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        if (phoneNumber2.getVisibility()== View.GONE ) {
            phoneNumber2.setVisibility(View.VISIBLE); }
        else phoneNumber2.setVisibility(View.GONE); }
});
mSave.setOnClickListener(new View.OnClickListener() { @Override
            public void onClick(View v) {
                Information information = new Information(profileName.getText().toString(), firstName.getText().toString(),middleName.getText().toString(),
                        LastnName.getText().toString(),DOB.getText().toString(), nationalID.getText().toString(),passportNumber.getText().toString(),
                        eMail1.getText().toString(),eMail2.getText().toString(), phoneNumber1.getText().toString(),phoneNumber2.getText().toString(),
                        country.getText().toString(), city.getText().toString(), street.getText().toString(), zipCode.getText().toString());

    dataBase.addInformation(information) ;
                Log.d(TAG, "onClick: " + information.getProfileName()) ;
    if (! information.geteMail1().isEmpty() ) {
if (checkBox.isChecked() ){
    ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
    NetworkInfo netInfo= null;
    if (cm != null) {
        netInfo = cm.getActiveNetworkInfo();
        }
//"satish.talim@gmail.com"
    if (netInfo != null && netInfo.isConnectedOrConnecting()) {
        Log.d(TAG, "onCreate: "+"True"+netInfo.getTypeName());
        Log.d(TAG, "onClick:emailchecked "+ eMail1.getText().toString());

        new PerformSearchTask().execute(eMail1.getText().toString().trim());
new PerformSearchTaskID().execute(nationalID.getText().toString());
new PerformSearchTaskPhone().execute(phoneNumber1.getText().toString());
    } } else {
        Log.d(TAG, "onCreate nullEmail : "+"False");

    }
}else Log.d(TAG, "onClick: null emial" +eMail1.getText());
}});

}

    @SuppressLint("StaticFieldLeak")
    protected class PerformSearchTask extends AsyncTask<String, Void, ArrayList<Breach>> {
        protected ArrayList<Breach> doInBackground(String... accounts) {
            HaveIbeenPwnedAPI api = new HaveIbeenPwnedAPI();
            ArrayList<Breach> result = new ArrayList<Breach>();
            try {
                result = api.query(accounts[0]);
            } catch (URISyntaxException e) {
                Toast.makeText(getBaseContext(), getString(R.string.error_invalid_uri_syntax), Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            } catch (IOException e) {
                Toast.makeText(getBaseContext(), getString(R.string.error_invalid_response), Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }

            return result;
        }


        protected void onPostExecute(ArrayList<Breach> result) {

            // Create the history card if not already created by FetchHistoryTask

            if (result == null) {
                Toast.makeText(getBaseContext(), getString(R.string.error_result_null), Toast.LENGTH_SHORT).show();
            } else {
                Log.d(TAG, "onPostExecute: " + result.size() + result.get(0).getName());

for (int i = 0 ; i<= result.size()-1;i++ ) {
    Log.d(TAG, "onPostExecute: " + result.size() + result.get(i).getName()) ;
    result.get(i).setProfileName(profileName.getText().toString());

    dataBase.addBreaches(result.get(i));
}
            }
        }


    }



    public class  PerformSearchTaskID extends AsyncTask<String, Void, ArrayList<CreditCardBreach>> {
        protected ArrayList<CreditCardBreach> doInBackground(String... accounts) {
            ArrayList<CreditCardBreach> result = new ArrayList<>();
            CreditCardBreach creditCardBreach =new CreditCardBreach();
            String urlApi = "https://www.googleapis.com/customsearch/v1?q=";
            String key = "AIzaSyDkaSeJ8cNr8YqTkgK6neaybOTqwIBlduM" ;
            String customSearch = "004878726315063446531:mqofxqhbfmm";
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

                            line = line.replaceAll("\"link\"","Link");
                            creditCardBreach.setLink(line);
                            j++ ;
                        }
                            if ( j == 2 )
                            {
                                creditCardBreach.setCreditCard(accounts[0]);
                                creditCardBreach.setProfileName(firstName.getText().toString());
                                result.add(i,creditCardBreach);
                                dataBase.addIDBreaches(result.get(i));
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


    public class  PerformSearchTaskPhone extends AsyncTask<String, Void, ArrayList<CreditCardBreach>> {
        protected ArrayList<CreditCardBreach> doInBackground(String... accounts) {
            ArrayList<CreditCardBreach> result = new ArrayList<>();
            CreditCardBreach creditCardBreach =new CreditCardBreach();
            String urlApi = "https://www.googleapis.com/customsearch/v1?q=";
            String key = "AIzaSyDkaSeJ8cNr8YqTkgK6neaybOTqwIBlduM" ;
            String customSearch = "004878726315063446531:mp-97z08qoi";
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
line = line.replace("\"link\":","");
                            creditCardBreach.setLink(line);
                            j++ ;
                        }
                            if ( j == 2 )
                            {
                                creditCardBreach.setCreditCard(accounts[0]);
                                creditCardBreach.setProfileName(firstName.getText().toString());
                                result.add(i,creditCardBreach);
                                dataBase.addPhoneBreaches(result.get(i));
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
