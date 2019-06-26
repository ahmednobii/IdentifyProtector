package com.identifyprotector.identifyprotector;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Setting_UP extends AppCompatActivity {

    private String duration, uname;
    private String ActivateAlert;
    private RadioButton d;
    private RadioButton w;
    private RadioButton m;
    private CheckBox check1;
    private String ID;
    private String URL_UPSetting = "http://192.168.43.74/loadSetting.php";
  //  private String URL_UPSetting = "http://192.168.100.14/loadSetting.php?UserID=";
    RequestQueue rq;
    private TextView du;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_setting);
        uname = SaveSharedPreference.getUserName(Setting_UP.this).toString();
        rq= Volley.newRequestQueue(this);
      //  du=(TextView)findViewById(R.id.du);


        sendQueue();
    }

    public void sendQueue(){


        JsonObjectRequest josenObjectRequest = new JsonObjectRequest(Request.Method.GET, URL_UPSetting , null,
            new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {


                    ID=response.getString("ID");
                    ActivateAlert=response.getString("ActivateAlert");
                    duration=response.getString("duration");

                    du.setText("1");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();

            }
        });
        Toast.makeText(Setting_UP.this, ID, Toast.LENGTH_SHORT).show();

              rq.add(josenObjectRequest);

    }


}
