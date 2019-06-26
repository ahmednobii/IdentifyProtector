package com.identifyprotector.identifyprotector;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.identifyprotector.identifyprotector.models.ServerRequest;
import com.identifyprotector.identifyprotector.models.ServerResponse;
import com.identifyprotector.identifyprotector.models.User;

import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegisterFragment extends AppCompatActivity {


    private ProgressBar progress;
    /////
    private CheckBox check1;
    private String duration;
    private String ActivateAlert;
    private RadioButton d;
    private RadioButton w;
    private RadioButton m;
    private EditText username;
    private EditText password;
    private EditText email;
    private  String sUsername;
    private String sPassword;
    private String sEmail;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_page2);
        addListnerToCheckBox();


        username = (EditText) findViewById(R.id.editname);
        password = (EditText) findViewById(R.id.editpass);
        email = (EditText) findViewById(R.id.editMail);
        progress = (ProgressBar)findViewById(R.id.progress);




        Button imageButton = (Button) findViewById(R.id.save);
        imageButton.setOnClickListener(new View.OnClickListener() {
            int hours ;
            @Override
            public void onClick(View v) {

                if (d.isChecked()) {
                    hours = 24;
                    JobApp.schedulePeriodic(hours);
                }   else if (w.isChecked()) {
                    hours = 7 * 24;
                    JobApp.schedulePeriodic(hours);
                }   else if (m.isChecked())
                { hours = 7*24*30 ;
                    JobApp.schedulePeriodic(hours);
                }


                if(CheckValid()){

                    registerProcess(sUsername,sEmail,sPassword,duration,ActivateAlert);
                    progress.setVisibility(View.VISIBLE);


                }
            }
        });

    }


    private void registerProcess(String username, String email, String password, String duration,String ActivateAlert){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RequestInterface requestInterface = retrofit.create(RequestInterface.class);

        User user = new User();
        user.setName(username);
        user.setEmail(email);
        user.setPassword(password);
        user.setDuration(duration);
        user.setActivateAlert(ActivateAlert);
        ServerRequest request = new ServerRequest();
        request.setOperation(Constants.REGISTER_OPERATION);
        request.setUser(user);
        Call<ServerResponse> response = requestInterface.operation(request);

        response.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, retrofit2.Response<ServerResponse> response) {

                ServerResponse resp = response.body();
                Snackbar.make(getCurrentFocus(), resp.getMessage(), Snackbar.LENGTH_LONG).show();

              if(resp.getMessage().equals("User Registered Successfully!")){
                goToLogin();}

                progress.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {

                progress.setVisibility(View.INVISIBLE);
                Log.d(Constants.TAG,"failed");
                Snackbar.make(getCurrentFocus(), t.getLocalizedMessage(), Snackbar.LENGTH_LONG).show();


            }
        });
    }

    private void goToLogin(){

        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.commit();
        Intent intent = new Intent(this, login.class);
        startActivity(intent);
        SaveSharedPreference newpref = new SaveSharedPreference();
        newpref.setUserName(this, sUsername);
    }

    public void addListnerToCheckBox() {

        ActivateAlert = "1";

        check1 = (CheckBox) findViewById(R.id.mybox);
        check1.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (((CheckBox) v).isChecked()) {
                            Toast.makeText(getBaseContext(), "is checked", Toast.LENGTH_SHORT).show();
                            ActivateAlert = "1";

                        } else {
                            ActivateAlert = "0";
                        }
                    }
                }
        );
        d = (RadioButton) findViewById(R.id.daily);
        if (d.isChecked()) {
            duration = "1";
        }
        d.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (((RadioButton) v).isChecked()) {
                            Toast.makeText(getBaseContext(), "daily is checked", Toast.LENGTH_SHORT).show();
                            duration = "1";

                        }
                    }
                }
        );

        w = (RadioButton) findViewById(R.id.weekly);
        w.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (((RadioButton) v).isChecked()) {
                            Toast.makeText(getBaseContext(), "weekly is checked", Toast.LENGTH_SHORT).show();
                            duration = "2";

                        }
                    }
                }
        );


        m = (RadioButton) findViewById(R.id.monthly);
        m.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (((RadioButton) v).isChecked()) {
                            Toast.makeText(getBaseContext(), "monthly is checked", Toast.LENGTH_SHORT).show();
                            duration = "3";

                        }
                    }
                }
        );


    }

    public boolean CheckValid(){
        sUsername = username.getText().toString();
        sPassword = password.getText().toString();
        sEmail = email.getText().toString();
        Pattern UpperCasePatten = Pattern.compile("[A-Z ]");
        Pattern specailCharPatten = Pattern.compile("[@#$%^&+=]");
        Pattern digitCasePatten = Pattern.compile("[0-9 ]");
        Pattern LowerCasePatten = Pattern.compile("[a-z ]");

        if (sUsername.matches("")) {
            Toast.makeText(this, "You did not enter a username", Toast.LENGTH_SHORT).show();
            return false;
        }else if (sUsername.length()<4) {
            Toast.makeText(this, "Username should not be less than 4 character ", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if (sPassword.matches("")){
            Toast.makeText(this, "You did not enter a password", Toast.LENGTH_SHORT).show();
            return false;
        }else if (!UpperCasePatten.matcher(sPassword).find()){
            Toast.makeText(this, "Password must have at least 1 Capital letter ", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if (!specailCharPatten.matcher(sPassword).find()){
            Toast.makeText(this, "Password must have at least 1 special character Ex.@#$%^&+=", Toast.LENGTH_SHORT).show();
            return false;
        }else if (!digitCasePatten.matcher(sPassword).find()){
            Toast.makeText(this, "Password must have at least 1 digit", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if (!LowerCasePatten.matcher(sPassword).find()){
            Toast.makeText(this, "Password must have at least 1 small letter ", Toast.LENGTH_SHORT).show();
            return false;
        }else if (sPassword.length()<8) {
            Toast.makeText(this, "Password should not be less than 8 character ", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if (sEmail.matches("")) {
            Toast.makeText(this, "Email should not be empty ", Toast.LENGTH_SHORT).show();
            return false;
        }else if (sEmail.length()<10) {
            Toast.makeText(this, "Email should not be less than 10 character", Toast.LENGTH_SHORT).show();
            return false;
        } else return true;
    }
}
