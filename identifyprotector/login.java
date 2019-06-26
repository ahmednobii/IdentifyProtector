package com.identifyprotector.identifyprotector;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import com.identifyprotector.identifyprotector.SaveSharedPreference;
import com.identifyprotector.identifyprotector.utils.FingerprintAuthenticationHelper;

public class login  extends AppCompatActivity {

    private static final String KEY_NAME = "THE_SUPER_SECRET_KEY_NAME";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //to retrive username
       // String uname = SaveSharedPreference.getUserName(login.this).toString();

        // Initialize TextView to be used for simple feedback to the user
        TextView feedbackTextView = findViewById(R.id.tvFeedbackTextView);
    //    TextView ntext= findViewById(R.id.newtext);
      //  ntext.setText(uname);


        // Implement Fingerprint Authentication
       FingerprintAuthenticationHelper fingerprintAuthenticationHelper = new FingerprintAuthenticationHelper(KEY_NAME);
       fingerprintAuthenticationHelper.completeFingerprintAuthentication(this, feedbackTextView);


    }

}
