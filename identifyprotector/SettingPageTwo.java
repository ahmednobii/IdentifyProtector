package com.identifyprotector.identifyprotector;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class SettingPageTwo extends Activity {
    CheckBox receiveNotification,monitoringDuration ;
RadioGroup radioGroup ;
RadioButton dailyRadioButton,monthlyRadioButton,weeklyRadioButton;
Button saveSet ;
@Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    setContentView(R.layout.setting_page_two);
dailyRadioButton = findViewById(R.id.dialyRadioButton);
monthlyRadioButton = findViewById(R.id.monthlyRadioButton);
weeklyRadioButton  = findViewById(R.id.weeklyRadioButton);
radioGroup = findViewById(R.id.radioGroup);
receiveNotification = findViewById(R.id.receiveNotification);
monitoringDuration=findViewById(R.id.monitoringDuration);
 saveSet = findViewById(R.id.setting_Save);
}
 @Override
    public void onStart() {
    super.onStart();
receiveNotification.setChecked(true);
dailyRadioButton.setChecked(true);
        saveSet.setOnClickListener(new View.OnClickListener() {
int hours ;
    @Override
    public void onClick(View v) {
if (dailyRadioButton.isChecked()) {
    hours = 24;
    JobApp.schedulePeriodic(hours);
}   else if (weeklyRadioButton.isChecked()) {
    hours = 7 * 24;
    JobApp.schedulePeriodic(hours);
}   else if (monthlyRadioButton.isChecked())
{ hours = 7*24*30 ;
    JobApp.schedulePeriodic(hours);
}

       // Intent intent1 = new Intent(getApplicationContext(),SettingPageTwo.class);
finish();
    }
        });

        }
}
