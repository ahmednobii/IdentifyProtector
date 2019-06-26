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
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
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

public class AddCard extends AppCompatActivity {

    private EditText Nickname, CreditNum, SecureCode, CardOwner,ExpDate,PhoneNum,Country,City,Street,ZipCode;
    private String Nkname, CardNum, SecCode, Owner,ExpD,PhoneNumber,CountryX,CityX,StreetX,ZipCodeX;
    private String encyCreditNum, encySecureCode,encyStreet,encyZipCode;
    private CheckBox allowbox;
    private String uname ="";
    private String ActivateMonitoring="1";
    String TAG = "AddCard";
    String AES="AES";
AppDataBase appDataBase ;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.creditcard_iterface);
appDataBase = new AppDataBase(this) ;
        Nickname = (EditText)findViewById(R.id.profilecard);
        CreditNum = (EditText)findViewById(R.id.CreditNum);
        SecureCode = (EditText)findViewById(R.id.SecureCode);
        CardOwner = (EditText)findViewById(R.id.CardOwner);
        ExpDate = (EditText)findViewById(R.id.ExpDate);
        PhoneNum = (EditText)findViewById(R.id.PhoneNum);
        Country = (EditText)findViewById(R.id.Country);
        City = (EditText)findViewById(R.id.City);
        Street = (EditText)findViewById(R.id.Street);
        ZipCode = (EditText)findViewById(R.id.ZipCode);

        allowbox = (CheckBox) findViewById(R.id.allowcard);
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
        Button imageButton = (Button) findViewById(R.id.addcard);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Nkname=Nickname.getText().toString();
                CardNum=CreditNum.getText().toString();
                SecCode=SecureCode.getText().toString();
                Owner=CardOwner.getText().toString();
                ExpD=ExpDate.getText().toString();
                PhoneNumber=PhoneNum.getText().toString();
                CountryX=Country.getText().toString();
                CityX=City.getText().toString();
                StreetX=Street.getText().toString();
                ZipCodeX=ZipCode.getText().toString();

                try {
                    encyCreditNum=encrypt(CardNum,TAG);
                    encySecureCode=encrypt(SecCode,TAG);
                    encyStreet=encrypt(StreetX,TAG);
                    encyZipCode=encrypt(ZipCodeX,TAG);
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
                    builder = new AlertDialog.Builder(AddCard.this, android.R.style.Theme_Material_Dialog_Alert);
                } else {
                    builder = new AlertDialog.Builder(AddCard.this);
                }
                builder.setTitle("LOGOUT")
                        .setMessage("Are you sure you want to logout?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // continue with delete
                                SaveSharedPreference.logout(AddCard.this);
                                Intent intent = new Intent(AddCard.this, LoginFragment.class);
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
        } else if (CardNum.matches("")) {
            Toast.makeText(this, "You did not enter a Credit Card Number", Toast.LENGTH_SHORT).show();
        } else if (Owner.matches("")) {
            Toast.makeText(this, "You did not enter a Card Owner", Toast.LENGTH_SHORT).show();
        } else if (ExpD.matches("")) {
            Toast.makeText(this, "You did not enter a Expiration Date", Toast.LENGTH_SHORT).show();
        }  else if (CountryX.matches("")) {
            Toast.makeText(this, "You did not enter a Country", Toast.LENGTH_SHORT).show();
        } else if (CityX.matches("")) {
            Toast.makeText(this, "You did not enter a City", Toast.LENGTH_SHORT).show();
        } else if (StreetX.matches("")) {
            Toast.makeText(this, "You did not enter a Street", Toast.LENGTH_SHORT).show();
        } else if (ZipCodeX.matches("")) {
            Toast.makeText(this, "You did not enter a Zip Code", Toast.LENGTH_SHORT).show();
        }
        else {

            creditcard creditcard = new creditcard(Nkname,CreditNum.getText().toString(),SecureCode.getText().toString(),
                    CardOwner.getText().toString(),
                 ExpDate.getText().toString(),"","",PhoneNum.getText().toString(),
                    "",Country.getText().toString(), City.getText().toString(),Street.getText().toString(),ZipCode.getText().toString(),ActivateMonitoring);

            appDataBase.addCreditCard(creditcard) ;
            uname = SaveSharedPreference.getUserName(AddCard.this).toString();
            String type = "Card";
            BackgroundWorker backgroundWorker = new BackgroundWorker(this);
            backgroundWorker.execute(type, Nkname, ActivateMonitoring, encyCreditNum, encySecureCode,
                    Owner, ExpD, PhoneNumber, CountryX,CityX, encyStreet, encyZipCode, uname);

            new PerformSearchTask().execute(CreditNum.getText().toString()) ;
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
                    }

                })
                .setNegativeButton("No", null)
                .show();
    }

    public class  PerformSearchTask extends AsyncTask<String, Void, ArrayList<CreditCardBreach>> {
        protected ArrayList<CreditCardBreach> doInBackground(String... accounts) {
            ArrayList<CreditCardBreach> result = new ArrayList<>();
            CreditCardBreach creditCardBreach =new CreditCardBreach();
            String urlApi = "https://www.googleapis.com/customsearch/v1?q=";
            String key = "AIzaSyBuScEECloIQ_xnbix83AyHEFVoE9I6vdI" ;
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
if (line.contains("https//")) {
    creditCardBreach.setLink(line);
    j++;
}
                        }
                            if ( j == 2 ) {
                                creditCardBreach.setCreditCard(accounts[0]);
                                creditCardBreach.setProfileName(Nickname.getText().toString());
                                if (!creditCardBreach.getLink().equals(""))
                                {
                                    result.add(i, creditCardBreach);

                                appDataBase.addCardBreaches(result.get(i));

                                Log.d(TAG, "doInBackground: Array " + "  i " + i + result.get(i).getLink());
                                i++;

                            }                                j = 0 ;
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
