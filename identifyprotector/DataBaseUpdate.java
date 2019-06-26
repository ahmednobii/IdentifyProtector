package com.identifyprotector.identifyprotector;

import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class DataBaseUpdate extends Service {
    String AES = "AES";
    String TAG = "AddPersonal";
    List<creditcard> creditList;
    String TAGLOG = "AddLogin";
    private static final String URL_Login = "http://192.168.43.74/logins.php?UserID=";
    List<Information> informationList = new ArrayList<>();
    List<creditcard> creditCardList = new ArrayList<>();
    List <LoginCredential> logList = new ArrayList<>();
    public String constProfile;
    Context context;
    private static final String URL_Credit = "http://192.168.43.74/creditcard.php?UserID=";
    //private static final String URL_Credit = "http://192.168.43.235/creditcard.php?UserID=";
    private String Nkname, MiddleN, LastN, DOBp, NationalIDp, PassportNum, email, Phone, Countryp, Cityp, Streetp, ZipCodep;
    private final IBinder mBinder = new LocalBinder();
    private static final String URL_personal = "http://192.168.43.74/Personals.php?UserID=";
    //   private static final String URL_personal = "http://192.168.43.235/Personals.php?UserID=";
    private String uname;
    //a list to store all the products
    List<personalinformation> PersonalList;

    AppDataBase appDataBase;
    //Methods

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        appDataBase = new AppDataBase(this);


        uname = SaveSharedPreference.getUserName(this).toString();
        Log.d(TAG, "onCreate: " + "Service");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("DATAUPDATE", "onStartCommand: " + intent + flags + startId);

        if (intent.getStringExtra("T").equals("UI")) {
            Log.d("DATAUPDATE", "onStartCommand: " + "UI");
        appDataBase.removeAllInformationTable();
            UpdateInformationTable();
           // appDataBase.removeAllInformationBreaches();
         // appDataBase.removeIDBreaches() ;
          //appDataBase.removePhonesBreaches();
            fitchData();

            if (!informationList.isEmpty()) {
                for (Information info : informationList) {
                    constProfile = info.getProfileName();
                    new PerformSearchTaskInformation().execute(info.geteMail1(),info.getProfileName());
                    new PerformSearchTaskID().execute(info.getNationalID(), info.getProfileName());
                    new PerformSearchTaskPhone().execute(info.getPhoneNumber1(), info.getProfileName());

                }
            }
            stopSelf();

            } else if (intent.getStringExtra("T").equals("UC")) {

            Log.d("DATAUPDATE", "onStartCommand: " + "UC");
           // appDataBase.removeAllCreditCardBreaches();
            appDataBase.removeAllCardsTable();
UpdateCreditCardTable();
            fitchtCreditCards();
            Log.d("DATAUPDATE", "onStartCommand: "+creditCardList.size());
            if( !creditCardList.isEmpty()){
                for (creditcard info : creditCardList ) {
                    constProfile = info.getNickname();
                    Log.d("DATAUPDATE", "onStartCommand: "+info.getCreditNum());
                    new PerformSearchTaskCreditCard().execute(info.getCreditNum(),info.getNickname());

                }
                stopSelf();}

            } else if (intent.getStringExtra("T").equals("UL") ) {
            Log.d("DATAUPDATE", "onStartCommand: "+intent.getStringExtra("T"));
            appDataBase.removeLoginCredentials();
UpdatelogCredTable();
            fitchLOGCRED();
            if( ! logList.isEmpty()){
                for (LoginCredential info : logList ) {
                    constProfile = info.getProfileName();
                    if (info.getUserName().matches("[a-zA-Z0-9._-]+@[a-z]+.[a-z]+")) {
                        new PerformSearchTaskUsers().execute(info.getUserName(), info.getProfileName());

                    }
                }}

            stopSelf();}

            return START_NOT_STICKY;
        }
        public class LocalBinder extends Binder {
            DataBaseUpdate getServices() {
                return DataBaseUpdate.this;
            }
        }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("DATAUPDATE", "onDestroy: ");
    }

    //MEthods PArt

        private void UpdateInformationTable () {

            /*
             * Creating a String Request
             * The request type is GET defined by first parameter
             * The URL is defined in the second parameter
             * Then we have a Response Listener and a Error Listener
             * In response listener we will get the JSON response as a String
             * */
            StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_personal + uname,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                //converting the string to json array object
                                Log.d("DATAUPDATE", "onResponse: " + response);
                                JSONArray array = new JSONArray(response);
                                Log.d(TAG, "onResponse: " + array.length());
                                //traversing through all the object
                                for (int i = 0; i < array.length(); i++) {

                                    //getting product object from json array
                                    JSONObject product = array.getJSONObject(i);
                                    Log.d(TAG, "onResponse: " + product.get("ID"));
                                    personalinformation personalinformation = new personalinformation(
                                            product.getString("ID"),
                                            product.getString("Nickname"),
                                            product.getString("ActivateMonitoring"),
                                            product.getString("FirstName"),
                                            product.getString("MiddleMame"),
                                            product.getString("LastName"),
                                            product.getString("DOB"),
                                            product.getString("NationalID"),
                                            product.getString("PassportNumber"),
                                            product.getString("mail"),
                                            product.getString("PhoneNum"),
                                            product.getString("Country"),
                                            product.getString("City"),
                                            product.getString("Street"),
                                            product.getString("ZipCode"));
                                    appDataBase.addInformation(setInformation(personalinformation));
                                    Log.d(TAG, "onResponse: " + "TRUE");
                                }


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    });

            //adding our stringrequest to queue
            Volley.newRequestQueue(this).add(stringRequest);
        }

        public Information setInformation (personalinformation personalinformation){


            try {
                NationalIDp = decrypt(personalinformation.getNationalID(), TAG);
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                PassportNum = decrypt(personalinformation.getPassportNumber(), TAG);
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                email = decrypt(personalinformation.getMail(), TAG);
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                Phone = decrypt(personalinformation.getPhoneNum(), TAG);
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                Streetp = decrypt(personalinformation.getStreet(), TAG);
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                ZipCodep = decrypt(personalinformation.getZipCode(), TAG);
            } catch (Exception e) {
                e.printStackTrace();
            }


            return new Information(personalinformation.getNickname(), personalinformation.getFirstName(),
                    personalinformation.getMiddleMame(), personalinformation.getLastName(), personalinformation.getDOB(), NationalIDp,
                    PassportNum, email, "", Phone, "", personalinformation.getCountry(),
                    personalinformation.getCity(), Streetp, ZipCodep);
        }

        private String decrypt (String outputString, String password)throws Exception {
            SecretKeySpec key = generateKey(password);
            Cipher c = Cipher.getInstance(AES);
            c.init(Cipher.DECRYPT_MODE, key);
            byte[] decodeValue = Base64.decode(outputString, Base64.DEFAULT);
            byte[] decValue = c.doFinal(decodeValue);
            String decryptedValue = new String(decValue);
            return decryptedValue;


        }


        private SecretKeySpec generateKey (String password)throws Exception {
            final MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte bytes[] = password.getBytes("UTF-8");
            digest.update(bytes, 0, bytes.length);
            byte key[] = digest.digest();
            SecretKeySpec secretKeySpec = new SecretKeySpec(key, "AES");
            return secretKeySpec;

        }

        private void UpdateCreditCardTable () {

            /*
             * Creating a String Request
             * The request type is GET defined by first parameter
             * The URL is defined in the second parameter
             * Then we have a Response Listener and a Error Listener
             * In response listener we will get the JSON response as a String
             * */
            StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_Credit + uname,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                //converting the string to json array object
                                JSONArray array = new JSONArray(response);
                                Log.d("DATAUPDATE", "onResponse: UC ");
                                //traversing through all the object
                                for (int i = 0; i < array.length(); i++) {

                                    //getting product object from json array
                                    JSONObject product = array.getJSONObject(i);


                                    creditcard creditcard = new creditcard(
                                            product.getString("ID"),
                                            product.getString("Nickname"),
                                            product.getString("ActivateMonitoring"),
                                            product.getString("CreditNum"),
                                            product.getString("SecureCode"),
                                            product.getString("CardOwner"),
                                            product.getString("ExpDate"),
                                            product.getString("PhoneNum"),
                                            product.getString("Country"),
                                            product.getString("City"),
                                            product.getString("Street"),
                                            product.getString("ZipCode"));

                                    appDataBase.addCreditCard(setCards(creditcard));

                                }


                                //creating adapter object and setting it to recyclerview
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    });

            //adding our stringrequest to queue
            Volley.newRequestQueue(this).add(stringRequest);
        }
        String TAGCard = "AddCard";
        String CardNum, StreetX, SecCode, ZipCodeX;


        public creditcard setCards (creditcard creditcard){

            try {
                CardNum = decrypt(creditcard.getCreditNum(), TAGCard);
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                SecCode = decrypt(creditcard.getSecureCode(), TAGCard);
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                StreetX = decrypt(creditcard.getStreet(), TAGCard);
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                ZipCodeX = decrypt(creditcard.getZipCode(), TAGCard);
            } catch (Exception e) {
                e.printStackTrace();
            }

            return new creditcard(creditcard.getNickname(), CardNum, SecCode
                    , creditcard.getCardOwner(), creditcard.getExpDate(), "", "", creditcard.getPhoneNum(),
                    "", creditcard.getCountry(), creditcard.getCity(), StreetX, ZipCodeX, creditcard.getActivateMonitoring());
        }


        private void UpdatelogCredTable () {

            /*
             * Creating a String Request
             * The request type is GET defined by first parameter
             * The URL is defined in the second parameter
             * Then we have a Response Listener and a Error Listener
             * In response listener we will get the JSON response as a String
             * */
            StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_Login + uname,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                //converting the string to json array object
                                JSONArray array = new JSONArray(response);

                                //traversing through all the object
                                for (int i = 0; i < array.length(); i++) {

                                    //getting product object from json array
                                    JSONObject product = array.getJSONObject(i);


                                    logincredentials loginCredential = new logincredentials(
                                            product.getString("ID"),
                                            product.getString("Nickname"),
                                            product.getString("appname"),
                                            product.getString("ActivateMonitoring"),
                                            product.getString("user"),
                                            product.getString("pass")
                                    );
                                    appDataBase.addLoginCredential(setLoginCred(loginCredential));
                                }

                                //creating adapter object and setting it to recyclerview


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    });

            //adding our stringrequest to queue
            Volley.newRequestQueue(this).add(stringRequest);


        }
        String user_name, pass;
        public LoginCredential setLoginCred (logincredentials log){


            try {
                user_name = decrypt(log.getUser(), TAGLOG);
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                pass = decrypt(log.getPass(), TAGLOG);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return new LoginCredential(log.getNickname(), log.getAppname()
                    , user_name, pass, Integer.valueOf(log.getActivateMonitoring()));

        }
//TASKS
public class PerformSearchTaskCreditCard extends AsyncTask<String, Void, ArrayList<CreditCardBreach>> {
    protected ArrayList<CreditCardBreach> doInBackground(String... accounts) {
        ArrayList<CreditCardBreach> result = new ArrayList<>();
        CreditCardBreach creditCardBreach = new CreditCardBreach();
        String urlApi = "https://www.googleapis.com/customsearch/v1?q=";
        String key = "AIzaSyDkaSeJ8cNr8YqTkgK6neaybOTqwIBlduM";
        String customSearch = "004878726315063446531:jq4smo4-n3a";
        URL url = null;
        try {
            url = new URL(urlApi + accounts[0] + "&key=" + key + "&cx=" + customSearch + "&alt=json&fields=items");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        Log.d("DATAUPDATE", "query: 11" + url);
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
                        if (line.contains("title")) {
                            stringBuilder.append(line).append("\n");
                            Log.d("DATAUPDATE", "doInBackground: " + stringBuilder.toString());
                            j++;
                            String t = line.replaceAll("\"title\":", "");
                            creditCardBreach.setTitle(t);
                        }
                        if (line.contains("link")) {

                            creditCardBreach.setLink(line);
                            j++;
                        }
                        if (j == 2) {
                            creditCardBreach.setCreditCard(accounts[0]);
                            creditCardBreach.setProfileName(accounts[1]);
                            result.add(i, creditCardBreach);
                            Log.d("DATAUPDATE", "doInBackground: Array " + "  i " + i + result.get(i).getTitle());
                            appDataBase.addCardBreaches(result.get(i));
                            i++;
                            j = 0;
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }


    protected void onPostExecute(ArrayList<CreditCardBreach> result) {

        // Create the history card if not already created by FetchHistoryTask

        if (result == null) {
            Toast.makeText(getBaseContext(), getString(R.string.error_result_null), Toast.LENGTH_SHORT).show();
        } else {
            Log.d("DATAUPDATE", "onPostExecute: " + result.size());
            for (int i = 0; i < result.size() - 1; i++) {
                Log.d("DATAUPDATE", "onPostExecute: " + i + result.size() + result.get(i).getTitle());
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
                result = api.query(accounts[0], accounts[1]);
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

                for (int i = 0; i <= result.size() - 1; i++) {
                    Log.d(TAG, "onPostExecute: " + result.size() + result.get(i).getName());
                    //  result.get(i).setProfileName(In);

                    appDataBase.addBreaches(result.get(i));
                }
            }

        }


    }
    public class PerformSearchTaskUsers extends AsyncTask<String, Void, ArrayList<Breach>> {
        protected ArrayList<Breach> doInBackground(String... accounts) {
            HaveIbeenPwnedAPI api = new HaveIbeenPwnedAPI();
            ArrayList<Breach> result = new ArrayList<Breach>();
            try {
                result = api.query(accounts[0], accounts[1]);
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

                for (int i = 0; i <= result.size() - 1; i++) {
                    Log.d(TAG, "onPostExecute: " + result.size() + result.get(i).getName());
                    //  result.get(i).setProfileName(In);

                    appDataBase.addUsersBreaches(result.get(i));
                }
            }

        }


    }

    private void fitchData () {
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
            }
        }

    }
    public void fitchtCreditCards () {
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
                Log.d("DATAUPDATE", "getCreditCards:size " + creditCardList.size());

                i++;
            }
        }


    }


    public class PerformSearchTaskID extends AsyncTask<String, Void, ArrayList<CreditCardBreach>> {
        protected ArrayList<CreditCardBreach> doInBackground(String... accounts) {
            ArrayList<CreditCardBreach> result = new ArrayList<>();
            CreditCardBreach creditCardBreach = new CreditCardBreach();
            String urlApi = "https://www.googleapis.com/customsearch/v1?q=";
            String key = "AIzaSyBuScEECloIQ_xnbix83AyHEFVoE9I6vdI";
            String customSearch = "004878726315063446531:mqofxqhbfmm";
            URL url = null;
            try {
                url = new URL(urlApi + accounts[0] + "&key=" + key + "&cx=" + customSearch + "&alt=json&fields=items");
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            Log.d(TAG, "query:ID 11" + url);
            // URI uri =new URI(url.getProtocol(),url.getHost(),url.getPath(),url.getRef()) ;
            //url = uri.toURL() ;
            Log.d(TAG, "query: ID 22" + url);
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
                            if (line.contains("title")) {
                                stringBuilder.append(line).append("\n");
                                Log.d(TAG, "doInBackground: " + stringBuilder.toString());
                                j++;
                                String t = line.replaceAll("\"title\":", "");
                                creditCardBreach.setTitle(t);
                            }
                            if (line.contains("link")) {

                                creditCardBreach.setLink(line);
                                j++;
                            }
                            if (j == 2) {
                                creditCardBreach.setCreditCard(accounts[0]);
                                creditCardBreach.setProfileName(accounts[1]);
                                result.add(i, creditCardBreach);
                                appDataBase.addIDBreaches(result.get(i));
                                Log.d(TAG, "doInBackground: Array " + "  i " + i + result.get(i).getTitle());
                                i++;
                                j = 0;
                            }
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            return result;
        }


        protected void onPostExecute(ArrayList<CreditCardBreach> result) {

            // Create the history card if not already created by FetchHistoryTask

            if (result == null) {
                Toast.makeText(getBaseContext(), getString(R.string.error_result_null), Toast.LENGTH_SHORT).show();
            } else {
                Log.d(TAG, "onPostExecute: " + result.size());
                for (int i = 0; i < result.size() - 1; i++) {
                    Log.d(TAG, "onPostExecute: " + i + result.size() + result.get(i).getTitle());
                    //               appDataBase.addCardBreaches(result.get(i));
                }


            }
        }
    }


    class PerformSearchTaskPhone extends AsyncTask<String, Void, ArrayList<CreditCardBreach>> {
        protected ArrayList<CreditCardBreach> doInBackground(String... accounts) {
            ArrayList<CreditCardBreach> result = new ArrayList<>();
            CreditCardBreach creditCardBreach = new CreditCardBreach();
            String urlApi = "https://www.googleapis.com/customsearch/v1?q=";
            String key = "AIzaSyBuScEECloIQ_xnbix83AyHEFVoE9I6vdI";
            String customSearch = "004878726315063446531:mp-97z08qoi";
            URL url = null;
            try {
                url = new URL(urlApi + accounts[0] + "&key=" + key + "&cx=" + customSearch + "&alt=json&fields=items");
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            Log.d(TAG, "query:pho 11" + url);
            // URI uri =new URI(url.getProtocol(),url.getHost(),url.getPath(),url.getRef()) ;
            //url = uri.toURL() ;
            Log.d(TAG, "query:Pho 22" + url);
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
                            if (line.contains("title")) {
                                stringBuilder.append(line).append("\n");
                                Log.d(TAG, "doInBackground: " + stringBuilder.toString());
                                j++;
                                String t = line.replaceAll("\"title\":", "");
                                creditCardBreach.setTitle(t);
                            }
                            if (line.contains("link")) {

                                creditCardBreach.setLink(line);
                                j++;
                            }
                            if (j == 2) {
                                creditCardBreach.setCreditCard(accounts[0]);
                                creditCardBreach.setProfileName(accounts[1]);
                                result.add(i, creditCardBreach);
                                appDataBase.addPhoneBreaches(result.get(i));
                                Log.d(TAG, "doInBackground: Array " + "  i " + i + result.get(i).getTitle());
                                i++;
                                j = 0;
                            }
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            return result;
        }


        protected void onPostExecute(ArrayList<CreditCardBreach> result) {

            // Create the history card if not already created by FetchHistoryTask

            if (result == null) {
                Toast.makeText(getBaseContext(), getString(R.string.error_result_null), Toast.LENGTH_SHORT).show();
            } else {
                Log.d(TAG, "onPostExecute: " + result.size());
                for (int i = 0; i < result.size() - 1; i++) {
                    Log.d(TAG, "onPostExecute: " + i + result.size() + result.get(i).getTitle());
                    //               appDataBase.addCardBreaches(result.get(i));
                }


            }
        }
    }



    public void fitchLOGCRED () {
        Cursor cursor = appDataBase.getDataLOGCRED();
       logList.clear();
        int i = 0;
        if (cursor != null) {
            while (cursor.moveToNext()) {
                LoginCredential loginCredential =new LoginCredential  (cursor.getInt(0), cursor.getString(1),
                        cursor.getString(2), cursor.getString(3), cursor.getString(4),
                        cursor.getInt(5));
                logList.add( loginCredential);
                Log.d(TAG, "getCreditCards:size "+creditCardList.size());

            }
        }



    }
}


