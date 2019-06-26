package com.identifyprotector.identifyprotector;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.identifyprotector.identifyprotector.models.ServerRequest;
import com.identifyprotector.identifyprotector.models.ServerResponse;
import com.identifyprotector.identifyprotector.models.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class LoginFragment extends AppCompatActivity {


    private TextView tv_register;
    private ProgressBar progress;
    private EditText username;
    private EditText password;
    private String sUsername;
    private String sPassword;
    private TextView reset_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_login);


        reset_password= (TextView)findViewById(R.id.tv_reset_password);
        username = (EditText)findViewById(R.id.editname);
        password = (EditText)findViewById(R.id.editpass);
        tv_register = (TextView)findViewById(R.id.tv_register);
        progress = (ProgressBar)findViewById(R.id.progress);

        Button imageButton = (Button) findViewById(R.id.login);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if(CheckValid()){
//
//                    loginProcess(sUsername,sPassword);
//
//                }
goToProfile();

            }
        });

        reset_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(v.getContext(), ResetPasswordFragment.class);
                startActivity(intent);
                finish();

            }
        });

        tv_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(v.getContext(), RegisterFragment.class);
                startActivity(intent);
                finish();

            }
        });



    }


    private void loginProcess(String username, String password){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RequestInterface requestInterface = retrofit.create(RequestInterface.class);

        User user = new User();
        user.setName(username);
        user.setPassword(password);
        ServerRequest request = new ServerRequest();
        request.setOperation(Constants.LOGIN_OPERATION);
        request.setUser(user);
        Call<ServerResponse> response = requestInterface.operation(request);

        response.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, retrofit2.Response<ServerResponse> response) {

                ServerResponse resp = response.body();
                Snackbar.make(getCurrentFocus(), resp.getMessage(), Snackbar.LENGTH_LONG).show();

                if(resp.getResult().equals(Constants.SUCCESS)){
                    goToProfile();

                }
                progress.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {

             //   progress.setVisibility(View.INVISIBLE);
                Log.d(Constants.TAG,"failed");
                Snackbar.make(getCurrentFocus(), t.getLocalizedMessage(), Snackbar.LENGTH_LONG).show();

            }
        });
    }


    private void goToProfile(){



        FragmentTransaction ftt = getFragmentManager().beginTransaction();
        ftt.commit();
        Intent intent = new Intent(this, login.class);
        startActivity(intent);
        SaveSharedPreference newpref = new SaveSharedPreference();
        newpref.setUserName(this, sUsername);
    }


    public boolean CheckValid(){

        sUsername = username.getText().toString();
        sPassword = password.getText().toString();
        progress.setVisibility(View.VISIBLE);

        if (sUsername.matches("")) {
            Toast.makeText(this, "You did not enter a username", Toast.LENGTH_SHORT).show();
            return false;
        }

        if(sPassword.matches("")){
            Toast.makeText(this, "You did not enter a password", Toast.LENGTH_SHORT).show();
            return false;
        }
        else{
            return true;

        }




    }
}
