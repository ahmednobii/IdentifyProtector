package com.identifyprotector.identifyprotector;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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
import java.net.URISyntaxException;
import java.net.URL;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyStoreException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SignatureException;
import java.security.UnrecoverableEntryException;
import java.util.ArrayList;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

public class AddPersonal extends AppCompatActivity {

    private EditText Nickname, FirstName, MiddleName, LastName,DOB,NationalID,PassportNumber,mail,PhoneNum,Country,	City,Street,ZipCode;
    private String Nkname, FirstN, MiddleN, LastN, DOBp, NationalIDp, PassportNum, email, Phone, Countryp, Cityp, Streetp, ZipCodep;
    private String encyNationalID, encyPassportNum,encyemail,encyPhone,encyStreet,encyZipCode;
    private CheckBox allowbox;
    private String uname ="";
    private String ActivateMonitoring="1";
    String TAG = "AddPersonal";
    String AES="AES";
AppDataBase dataBase ;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personal_information_interface);
dataBase = new AppDataBase( this) ;
        Nickname = (EditText)findViewById(R.id.AddPerson);
        FirstName = (EditText)findViewById(R.id.FirstN);
        MiddleName = (EditText)findViewById(R.id.MiddleN);
        LastName = (EditText)findViewById(R.id.LastN);
        DOB = (EditText)findViewById(R.id.DOB);
        NationalID = (EditText)findViewById(R.id.NationalID);
        mail = (EditText)findViewById(R.id.email);
        PhoneNum = (EditText)findViewById(R.id.Phonepnum);
        PassportNumber = (EditText)findViewById(R.id.PassportNum);
        Country = (EditText)findViewById(R.id.Countryp);
        City = (EditText)findViewById(R.id.Cityp);
        Street = (EditText)findViewById(R.id.Streetp);
        ZipCode = (EditText)findViewById(R.id.ZipCodep);

        allowbox = (CheckBox) findViewById(R.id.allowpesonal);
        allowbox.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (((CheckBox) v).isChecked()) {
                            Toast.makeText(getBaseContext(), "is checked", Toast.LENGTH_SHORT).show();
                            ActivateMonitoring = "1";

                        } else {
                            ActivateMonitoring = "0";
                        }
                    }
                }
        );


        Button imageButton = (Button) findViewById(R.id.addPersonal);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Nkname=Nickname.getText().toString();
                FirstN=FirstName.getText().toString();
                MiddleN=MiddleName.getText().toString();
                LastN=LastName.getText().toString();
                DOBp=DOB.getText().toString();
                NationalIDp=NationalID.getText().toString();
                email=mail.getText().toString();
                PassportNum=PassportNumber.getText().toString();
                Phone=PhoneNum.getText().toString();
                Countryp=Country.getText().toString();
                Cityp=City.getText().toString();
                Streetp=Street.getText().toString();
                ZipCodep=ZipCode.getText().toString();

                try {
                    encyNationalID=encrypt(NationalIDp,TAG);
                    encyPassportNum=encrypt(PassportNum,TAG);
                    encyemail=encrypt(email,TAG);
                    encyPhone=encrypt(Phone,TAG);
                    encyStreet=encrypt(Streetp,TAG);
                    encyZipCode=encrypt(ZipCodep,TAG);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                CheckValid();
                finish();
            }
        });

        Button logout = (Button) findViewById(R.id.out);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    builder = new AlertDialog.Builder(AddPersonal.this, android.R.style.Theme_Material_Dialog_Alert);
                } else {
                    builder = new AlertDialog.Builder(AddPersonal.this);
                }
                builder.setTitle("LOGOUT")
                        .setMessage("Are you sure you want to logout?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // continue with delete
                                SaveSharedPreference.logout(AddPersonal.this);
                                Intent intent = new Intent(AddPersonal.this, LoginFragment.class);
                                startActivity(intent);
                                finish();
                            }
                        })
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // do nothing
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();

            }
        });

    }

    public void CheckValid() {




        if (Nkname.matches("")) {
            Toast.makeText(this, "You did not enter a Profile name", Toast.LENGTH_SHORT).show();
        } else if (FirstN.matches("")) {
            Toast.makeText(this, "You did not enter a First Name", Toast.LENGTH_SHORT).show();
        } else if (LastN.matches("")) {
            Toast.makeText(this, "You did not enter a Last Name", Toast.LENGTH_SHORT).show();
        }else if (email.matches("")) {
            Toast.makeText(this, "You did not enter an Email", Toast.LENGTH_SHORT).show();
        } else if (Phone.matches("")) {
            Toast.makeText(this, "You did not enter a Phone Number", Toast.LENGTH_SHORT).show();
        }   else if (Countryp.matches("")) {
            Toast.makeText(this, "You did not enter a Country", Toast.LENGTH_SHORT).show();
        } else if (Cityp.matches("")) {
            Toast.makeText(this, "You did not enter a City", Toast.LENGTH_SHORT).show();
        } else if (Streetp.matches("")) {
            Toast.makeText(this, "You did not enter a Street", Toast.LENGTH_SHORT).show();
        } else if (ZipCodep.matches("")) {
            Toast.makeText(this, "You did not enter a Zip Code", Toast.LENGTH_SHORT).show();
        }
        else {
            Information information = new Information(Nkname,FirstN,MiddleName.getText().toString(),
                    LastName.getText().toString(),DOB.getText().toString(),NationalID.getText().toString(),PassportNumber.getText().toString(),
                    mail.getText().toString(),"",PhoneNum.getText().toString(),"",Country.getText().toString(),
                    City.getText().toString(),Street.getText().toString(),ZipCode.getText().toString());

            dataBase.addInformation(information);
            uname = SaveSharedPreference.getUserName(AddPersonal.this).toString();
            String type = "Personal";
            BackgroundWorker backgroundWorker = new BackgroundWorker(this);
            backgroundWorker.execute(type, Nkname, ActivateMonitoring, FirstN, MiddleN,
                    LastN, DOBp, encyNationalID,encyPassportNum,encyemail,encyPhone, Countryp,Cityp, encyStreet, encyZipCode, uname);

            new PerformSearchTask().execute(mail.getText().toString());
            new PerformSearchTaskID().execute(NationalID.getText().toString());
            new PerformSearchTaskPhone().execute(PhoneNum.getText().toString());

        }
    }


    private String encrypt(String Data, String password)throws Exception {
        SecretKeySpec key = generateKey(password);
        Cipher c=Cipher.getInstance(AES);
        c.init(Cipher.ENCRYPT_MODE,key);
        byte[] encVal=c.doFinal(Data.getBytes());
        String encryptedValue= Base64.encodeToString(encVal, Base64.DEFAULT);
        return encryptedValue;

    }

    private SecretKeySpec generateKey(String password)throws Exception {
        final MessageDigest digest= MessageDigest.getInstance("SHA-256");
        byte bytes[]=password.getBytes("UTF-8");
        digest.update(bytes, 0,bytes.length);
        byte key[]=digest.digest();
        SecretKeySpec secretKeySpec=new SecretKeySpec(key,"AES");
        return secretKeySpec;

    }
    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Warning")
                .setMessage("Are you sure you want to leave this page ?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        SharedPreferences preferences =getSharedPreferences("loginPrefs", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.clear();
                        editor.commit();
                        finish();
                        //finish();
                    }

                })
                .setNegativeButton("No", null)
                .show();
    }

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
                    Log.d(TAG, "onPostExecute: " + result.size() + result.get(i).getAccount()) ;
                    result.get(i).setProfileName(Nickname.getText().toString());

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
            String key = "AIzaSyBuScEECloIQ_xnbix83AyHEFVoE9I6vdI" ;
            String customSearch = "004878726315063446531:mqofxqhbfmm";
            URL url = null;
            try {
                url = new URL(urlApi+accounts[0]+"&key="+key+"&cx="+customSearch+"&alt=json&fields=items");
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            Log.d(TAG, "query:ID 11"+url);
            // URI uri =new URI(url.getProtocol(),url.getHost(),url.getPath(),url.getRef()) ;
            //url = uri.toURL() ;
            Log.d(TAG, "query: ID 22"+url);
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
                                creditCardBreach.setProfileName(Nickname.getText().toString());
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
            String key = "AIzaSyBuScEECloIQ_xnbix83AyHEFVoE9I6vdI" ;
            String customSearch = "004878726315063446531:mp-97z08qoi";
            URL url = null;
            try {
                url = new URL(urlApi+accounts[0]+"&key="+key+"&cx="+customSearch+"&alt=json&fields=items");
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            Log.d(TAG, "query:pho 11"+url);
            // URI uri =new URI(url.getProtocol(),url.getHost(),url.getPath(),url.getRef()) ;
            //url = uri.toURL() ;
            Log.d(TAG, "query:Pho 22"+url);
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
                                creditCardBreach.setProfileName(Nickname.getText().toString());
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
