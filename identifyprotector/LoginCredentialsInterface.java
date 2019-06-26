package com.identifyprotector.identifyprotector;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationCompat.Builder;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import static android.support.v4.app.NotificationCompat.*;

public class LoginCredentialsInterface extends Activity {
    private NotificationManager mNM;
    private int NOTIFICATION = R.string.local_service_started;
    private static final String TAG = "LoginConditionals";
    EditText profileName, appName, userName, password;
    CheckBox mState;
    FloatingActionButton mSave;
    AppDataBase appDataBase;
    Date date ;
    SimpleDateFormat simpleDateFormat ;
    @SuppressLint("SimpleDateFormat")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_credentials_interface);
         date = Calendar.getInstance().getTime();
         simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        Log.d(TAG, "onCreate: " + simpleDateFormat.format(date));
        profileName = findViewById(R.id.login_profileName);
        appName = findViewById(R.id.login_AppName);
        userName = findViewById(R.id.login_userName);
        password = findViewById(R.id.login_Password);
        mSave = findViewById(R.id.login_save);
        mState = findViewById(R.id.login_CheckBox);
        mState.setChecked(true);
        appDataBase = new AppDataBase(this);


    }

    @Override
    protected void onStart() {
        super.onStart();

        mSave.setOnClickListener(new View.OnClickListener() {
            int state = 0;

            @Override
            public void onClick(View v) {
                if (mState.isChecked())
                    state = 1;
                LoginCredential loginCredential = new LoginCredential(profileName.getText().toString(), appName.getText().toString(), userName.getText().toString()
                        , password.getText().toString(), state);
                appDataBase.addLoginCredential(loginCredential);
new PerformSearchTask().execute(appName.getText().toString());


//notification();

            }
        });
    }

    protected class PerformSearchTask extends AsyncTask<String, Void, ArrayList<String>> {
        protected ArrayList<String> doInBackground(String... accounts) {
            ArrayList<String> result = new ArrayList<>();
            CreditCardBreach creditCardBreach = new CreditCardBreach();
            String urlApi = "https://www.googleapis.com/customsearch/v1?q=";
            String key = "AIzaSyDkaSeJ8cNr8YqTkgK6neaybOTqwIBlduM";
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
      String des= appName.getText().toString()+"  may be hacked, please keep your data safe, hint that daily news";
                notification(des);

            }
        }
    }


 //**************************************************************************************************

    private void notification(String descrip) {

        Builder builder;
        builder = new Builder(LoginCredentialsInterface.this);
        builder.setSmallIcon(R.mipmap.ic_launcher_round);
        builder.setContentTitle("Alert");
        builder.setContentText(descrip);

        Intent notificationIntent = new Intent(this, LoginCredentialsInterface.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(contentIntent);

        // Add as notification
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(0, builder.build());
    }

}