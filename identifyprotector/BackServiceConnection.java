package com.identifyprotector.identifyprotector;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
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
import java.security.Provider;
import java.util.ArrayList;
import java.util.List;

public class BackServiceConnection extends Service {
    private static final String TAG ="Service" ;
    AppDataBase appDataBase ;
    List<Information> informationList =new ArrayList<>() ;
    List<creditcard> creditCardList = new ArrayList<>();
public String constProfile ;
    private NotificationManager mNM;

    public class LocalBinder extends Binder {
       BackServiceConnection  getService() {
            return BackServiceConnection.this;
        }
    }
    @Override
    public void onCreate() {
        mNM = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        //Toast.makeText(getApplicationContext(), R.string.local_service_stopped, Toast.LENGTH_SHORT).show();
        appDataBase = new AppDataBase(this);
        // Display a notification about us starting.  We put an icon in the status bar.
       appDataBase.removeAllCreditCardBreaches();
       appDataBase.removeAllInformationBreaches();
        showNotification();
        fitchData();
        fitchtCreditCards();

    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("LocalService", "Received start id " + startId + ": " + intent);
        // Toast.makeText(this, R.string.local_service_stopped, Toast.LENGTH_SHORT).show();
       if( !informationList.isEmpty()){
        for (Information info : informationList ) {
            constProfile = info.getProfileName();
            new PerformSearchTaskInformation().execute(info.geteMail1());

        }}
        if( !creditCardList.isEmpty()){
            for (creditcard info : creditCardList ) {
                constProfile = info.getNickname();
                new PerformSearchTaskInformation().execute(info.getCreditNum());

            }}notification("Your reports are updated");
        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    private final IBinder mBinder = new LocalBinder();

   @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void showNotification() {
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
                new Intent(this, MainActivity.class), 0);
        Notification notification = new Notification.Builder(this)
                .setSmallIcon(R.drawable.ic_launcher_background)  // the status icon
                .setTicker("updating...")  // the status text
                .setWhen(System.currentTimeMillis())  // the time stamp
                .setContentTitle("Alert")  // the label of the entry
                .setContentText("Keep the connection on")  // the contents of the entry
                .setContentIntent(contentIntent)  // The intent to send when the entry is clicked
                .build();

        // Send the notification.
        mNM.notify( 0,notification);
    }


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
        NotificationManager manager = (NotificationManager) getSystemService(getApplicationContext().NOTIFICATION_SERVICE);
        manager.notify(0, builder.build());
    }

    public  class  PerformSearchTaskCreditCard extends AsyncTask<String, Void, ArrayList<CreditCardBreach>> {
        protected ArrayList<CreditCardBreach> doInBackground( String... accounts) {
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
                                creditCardBreach.setProfileName(constProfile);
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
                                  appDataBase.addCardBreaches(result.get(i));
                }



            }
        }
    }

    public class PerformSearchTaskInformation extends AsyncTask<String, Void, ArrayList<Breach>> {
        protected ArrayList<Breach> doInBackground(String... accounts) {
            HaveIbeenPwnedAPI api = new HaveIbeenPwnedAPI();
            ArrayList<Breach> result = new ArrayList<Breach>();
            try {
                result = api.query(accounts[0],constProfile);
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
                  //  result.get(i).setProfileName(In);

                    appDataBase.addBreaches(result.get(i));
                }
            }
        }


    }

    private void fitchData() {
        Cursor cursor = appDataBase.getDataInformation();

        informationList.clear();
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
            }}

    }
    public void fitchtCreditCards() {
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
                creditCardList.add(i, creditCard);
            Log.d(TAG, "getCreditCards:size "+creditCardList.size());
        i++ ;
            }
    }

}

    }
