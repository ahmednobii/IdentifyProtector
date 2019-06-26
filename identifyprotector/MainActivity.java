package com.identifyprotector.identifyprotector;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import android.graphics.drawable.AnimationDrawable;
import android.support.design.widget.TextInputLayout;
import android.widget.LinearLayout;
import com.example.library.UniversalPreferences;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        prefs = getSharedPreferences("com.identifyprotector.identifyprotector", MODE_PRIVATE);


        if (prefs.getBoolean("firstrun", true)) {

            // Do first run stuff here then set 'firstrun' as false
            prefs.edit().putBoolean("firstrun", false).commit();

            // using the following line to edit/commit prefs
            setContentView(R.layout.activity_main);

            Button imageButton = (Button) findViewById(R.id.RegisterButton);
            imageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(getBaseContext(), RegisterFragment.class);
                    startActivity(i);

                }
            });
            Button imageButton1 = (Button) findViewById(R.id.firstlogin);
            imageButton1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent m = new Intent(getBaseContext(), LoginFragment.class);
                    startActivity(m);

                }
            });


        }
        //if logged in, redirect user to login activity
         else if (SaveSharedPreference.getUserName(MainActivity.this).length() == 0) {
            // call Login Activity
            Intent intent = new Intent(this, LoginFragment.class);
           // Intent intent = new Intent(this, LoginFragment.class);
            startActivity(intent);
            finish();
        }
        else
        {
            // call verify Activity
           // Intent l = new Intent(getBaseContext(), login.class);
            Intent l = new Intent(getBaseContext(), login.class);
            startActivity(l);
            finish();
        }

        /*  else if (isLoggedIn) {
            Intent intent = new Intent(this, login.class);
            startActivity(intent);
            finish();
        }*/








    }


    }

