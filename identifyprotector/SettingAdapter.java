package com.identifyprotector.identifyprotector;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.Toast;

import java.util.List;

public class SettingAdapter extends RecyclerView.Adapter<SettingAdapter.SettingViewHolder>{

    private Context mCtx;
    private List<setting> SettingList;
    private setting Settinglistt;
private static final  String TAG = "SetAdapter" ;
    private CheckBox check1;
    private String duration;
    private String ActivateAlert;
    private RadioButton d;
    private RadioButton w;
    private RadioButton m;
    private String ID;
    Button Uplistbut;

    public SettingAdapter(Context mCtx, List<setting> SettingList) {
        this.mCtx = mCtx;
        this.SettingList = SettingList;
    }
    @Override
    public SettingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.setting_item, null);
        return new SettingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SettingViewHolder holder, final int position) {
        //  logincredentials loginlistt = loginList.get(position);
        Settinglistt = SettingList.get(position);

        ID=Settinglistt.getID();
   //     Toast.makeText(mCtx, holder.ID, Toast.LENGTH_SHORT).show();

        if(Settinglistt.getActivateAlert().equals("1")){
            check1.setChecked(true);
        } else {

            check1.setChecked(false);
        }
        if(Settinglistt.getDuration().equals("1")){

            d.setChecked(true);
        }
        if(Settinglistt.getDuration().equals("2")){
            w.setChecked(true);
        }
        if(Settinglistt.getDuration().equals("3")){
            m.setChecked(true);
        }

        Uplistbut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  Toast.makeText(mCtx, "Button is checked", Toast.LENGTH_SHORT).show();
                // Toast.makeText(mCtx, ID, Toast.LENGTH_SHORT).show();

                addListnerToCheckBox();

                String type = "updateSetting";
                BackgroundWorker backgroundWorker = new BackgroundWorker(mCtx);
                Log.d(TAG, "onClick: "+ActivateAlert);
                backgroundWorker.execute(type,duration,ActivateAlert,ID );

                int hours;
                if (d.isChecked()) {
                    hours = 24;
                    JobApp.schedulePeriodic(hours);
                }   else if (w.isChecked()) {
                    hours = 7 * 24;
                    JobApp.schedulePeriodic(hours);
                }   else if (m.isChecked()) {
                    hours = 7 * 24 * 30;
                    JobApp.schedulePeriodic(hours);
                }
            }
        });


        //
       /* holder.listbut.setText(Settinglistt.getActivateAlert());

        holder.listbut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(mCtx, edit_logincredential.class);
             //   intent.putExtra("ID", loginList.get(position).getID());
            //    intent.putExtra("ActivateMonitoring", loginList.get(position).getActivateMonitoring());


                mCtx.startActivity(intent);
                ((Activity)mCtx).finish();

            }
        });*/

    }

    @Override
    public int getItemCount() {
        return SettingList.size();
    }


    class SettingViewHolder extends RecyclerView.ViewHolder {

     //   Button listbut;
      //  String ActivateAlert="";
      //  CheckBox check1;
     //   RadioButton d;

//        public View parentLayout;
        public SettingViewHolder(View itemView) {
            super(itemView);

            check1 =  itemView.findViewById(R.id.mybox);
            m = (RadioButton) itemView.findViewById(R.id.monthly);

            w = (RadioButton) itemView.findViewById(R.id.weekly);

            d = (RadioButton) itemView.findViewById(R.id.daily);

            Uplistbut=itemView.findViewById(R.id.upstting);


        }

    }

    public void addListnerToCheckBox() {

                              if (check1.isChecked()) {
                            Toast.makeText(mCtx, "is checked", Toast.LENGTH_SHORT).show();
                            ActivateAlert = "1";

                        } else {
                                  ActivateAlert = "0";
                              }
                if (d.isChecked()) {
            duration = "1";
        }
        d.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (((RadioButton) v).isChecked()) {
                            Toast.makeText(mCtx, "daily is checked", Toast.LENGTH_SHORT).show();
                            duration = "1";

                        }
                    }
                }
        );

        w.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (((RadioButton) v).isChecked()) {
                            Toast.makeText(mCtx, "weekly is checked", Toast.LENGTH_SHORT).show();
                            duration = "2";

                        }
                    }
                }
        );

     m.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (((RadioButton) v).isChecked()) {
                            Toast.makeText(mCtx, "monthly is checked", Toast.LENGTH_SHORT).show();
                            duration = "3";

                        }
                    }
                }
        );


    }


}
