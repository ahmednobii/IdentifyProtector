package com.identifyprotector.identifyprotector;

import android.annotation.SuppressLint;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

public class AddLogin extends AppCompatActivity {

    private EditText Profilename, username, password, appname;
    private String filename="";
    private String user_name="";
    private String pass="";
    private String Appname;
    private CheckBox allowbox;
    private String uname ="";
    private String ActivateMonitoring="1";
    private String encyUser="";
    private String encyPass="";
    String TAG = "AddLogin";
    String AES="AES";
    Date date ;
    SimpleDateFormat simpleDateFormat ;

AppDataBase appDataBase ;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_credentials_interface);
        Profilename = (EditText)findViewById(R.id.profile);
        username = (EditText)findViewById(R.id.name);
        password = (EditText)findViewById(R.id.pass);
        appname = (EditText)findViewById(R.id.appname);
        allowbox = (CheckBox) findViewById(R.id.allow);
        appDataBase = new AppDataBase(this) ;

        date = Calendar.getInstance().getTime();
        simpleDateFormat = new SimpleDateFormat("yyyyMMdd");

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
       Button imageButton = (Button) findViewById(R.id.addlogin);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //CheckValid();

                user_name = username.getText().toString();
                pass = password.getText().toString();
                filename = Profilename.getText().toString();
                Appname=appname.getText().toString();

                try {
                    encyUser=encrypt(user_name,TAG);
                    encyPass=encrypt(pass,TAG);
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
                    builder = new AlertDialog.Builder(AddLogin.this, android.R.style.Theme_Material_Dialog_Alert);
                } else {
                    builder = new AlertDialog.Builder(AddLogin.this);
                }
                builder.setTitle("LOGOUT")
                        .setMessage("Are you sure you want to logout?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // continue with delete
                                SaveSharedPreference.logout(AddLogin.this);
                                Intent intent = new Intent(AddLogin.this, LoginFragment.class);
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
        //user_name = username.getText().toString();
       // pass = password.getText().toString();
      //  filename = Profilename.getText().toString();
     //   Appname=appname.getText().toString();

        if (filename.matches("")) {
            Toast.makeText(this, "You did not enter a Profile name", Toast.LENGTH_SHORT).show();
        } else if (user_name.matches("")) {
            Toast.makeText(this, "You did not enter a username", Toast.LENGTH_SHORT).show();
        } else if (pass.matches("")) {
            Toast.makeText(this, "You did not enter a  password", Toast.LENGTH_SHORT).show();
        } else if (Appname.matches("")) {
            Toast.makeText(this, "You did not enter a  App name", Toast.LENGTH_SHORT).show();
        } else {
            LoginCredential loginCredential = new LoginCredential(Profilename.getText().toString(),appname.getText().toString()
                    ,username.getText().toString(),password.getText().toString(),Integer.valueOf(ActivateMonitoring));
            appDataBase.addLoginCredential(loginCredential);

            uname = SaveSharedPreference.getUserName(AddLogin.this).toString();
            String type = "credential";
            BackgroundWorker backgroundWorker = new BackgroundWorker(this);
            backgroundWorker.execute(type, filename,Appname, ActivateMonitoring,encyUser, encyPass,uname );
            new PerformSearchTask().execute(appname.getText().toString());
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





    protected class PerformSearchTask extends AsyncTask<String, Void, ArrayList<String>> {
        protected ArrayList<String> doInBackground(String... accounts) {
            ArrayList<String> result = new ArrayList<>();
            CreditCardBreach creditCardBreach = new CreditCardBreach();
            String urlApi = "https://www.googleapis.com/customsearch/v1?q=";
            String key = "AIzaSyBuScEECloIQ_xnbix83AyHEFVoE9I6vdI";
            String customSearch = "004878726315063446531:jq4smo4-n3a";
            URL url = null;
            try {
                url = new URL(urlApi + accounts[0] + "&key=" + key + "&cx=" + customSearch + "&alt=json&fields=items"
                        +"&sort=review-date:r:"+"20180901"+":"+"20181023");
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            Log.d(TAG, "query: 11" + url);
            // URI uri =new URI(url.getProtocol(),url.getHost(),url.getPath(),url.getRef()) ;
            //url = uri.toURL() ;
            Log.d(TAG, "query: 22" + url);
            final HttpURLConnection connection;
            try {
                assert url != null;
                connection = (HttpURLConnection) url.openConnection();

                connection.setRequestMethod("GET");
                connection.connect();


                if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    Log.d(TAG, "query: " + connection.getResponseCode());
                    InputStream inputStream = new BufferedInputStream(connection.getInputStream());
                    BufferedReader r = new BufferedReader(new InputStreamReader(inputStream));
                    StringBuilder stringBuilder = new StringBuilder();
                    String line;
                    int j, i;
                    i = 0;
                    j = 0;
                    while ((line = r.readLine()) != null) {
                        if (!line.contains("{")) {
                            if (line.contains("link")) {
                                stringBuilder.append(line).append("\n");
                                Log.d(TAG, "doInBackground: " + stringBuilder.toString());

                                String t = line.replaceAll("\"title\":", "");
                                result.add(j,t);
                                j++;
                                Log.d(TAG, "doInBackground: "+t);
                            }
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            return result;
        }


        protected void onPostExecute(ArrayList<String> result) {

            // Create the history card if not already created by FetchHistoryTask

            if (result == null) {
                Toast.makeText(getBaseContext(), getString(R.string.error_result_null), Toast.LENGTH_SHORT).show();
            } else {
                Log.d(TAG, "onPostExecute: " + result.size());
                String des= appname.getText().toString()+"  may be hacked, please keep your data safe, hint that daily news";
                notification(des);

            }
        }
    }


    //**************************************************************************************************

    private void notification(String descrip) {

        NotificationCompat.Builder builder;
        builder = new NotificationCompat.Builder(this);
        builder.setSmallIcon(R.mipmap.ic_launcher_round);
        builder.setContentTitle("Alert");
        builder.setContentText(descrip);

        Intent notificationIntent = new Intent(this, AddLogin.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(contentIntent);
        // Add as notification
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(0, builder.build());
    }

}
