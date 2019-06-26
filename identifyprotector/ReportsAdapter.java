package com.identifyprotector.identifyprotector;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import static android.support.constraint.Constraints.TAG;

public class ReportsAdapter extends RecyclerView.Adapter< ReportsAdapter.ViewHolder >{
String email  ;
    private Context context;
public static final  String emailTag = "Email" ;

    public static final  String IDTag = "ID" ;
    public static final  String PhoneTag = "Phone" ;
    private TextView reportName ;
    private TextView reportCategory ,mailText ;
//    private List<String> profileNames ,profileCategories ;
private List<ReportList> reportLists ;
    List<Breach> list = new ArrayList<>() ;
@NonNull
    @Override
    public ReportsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.list_item_reports,parent,false) ;

        return new ViewHolder(v) ;
    }


    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder( final  ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        final String[] cat = new String[1];
        final ReportList reportList ;
        reportList = reportLists.get(position) ;
        reportName.setText("ProfileName  : " + reportList.getProfileName());
reportCategory.setText( reportList.getProfileCategory()) ;
mailText.setText(reportList.getEmail());
  context = holder.parentLayout.getContext() ;
        Log.d(TAG, "onCLickViewHolder: "+position);
  //
        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        email = reportList.getEmail() ;
        cat[0] = reportList.getProfileCategory() ;
        Intent intent = new Intent(context,ReportDetails.class) ;

intent.putExtra("PCat", cat[0]);
intent.putExtra(emailTag,email);
        Log.d(TAG, "onClick: onBind" +email+ cat[0] +position);

        Log.d(TAG, "onBindViewHolder: "+reportList.getEmail());
        context.startActivity(intent);
    }
});
    }

    @Override
    public int getItemCount() {
        return reportLists.size() ;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        public View parentLayout;

ViewHolder(View itemView){
    super(itemView);
    reportCategory = (TextView) itemView.findViewById(R.id.reportCategory);
    reportName = (TextView) itemView.findViewById(R.id.reportName);
    parentLayout = itemView.findViewById(R.id.reportItem);
mailText = itemView.findViewById(R.id.mail) ;
}
    }

    public ReportsAdapter(List<ReportList> reportLists) {

        this.reportLists = reportLists ;
    for (ReportList r : reportLists){
        Log.d(TAG, "ReportsAdapter: "+r.getEmail()+r.getProfileCategory());
    }

    }
public  void clearData ()
{reportLists.clear();}
}
