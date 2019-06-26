package com.identifyprotector.identifyprotector;

import android.annotation.SuppressLint;
import android.app.Application;
import android.util.Log;

import com.evernote.android.job.JobManager;

import static com.identifyprotector.identifyprotector.JobApp.TAG;

/**
 *
 */
public class CreatorMain extends Application {

  public CreatorMain() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        JobManager.create(getApplicationContext()).addJobCreator(new jobCreatorApp());

        //JobManager.instance().getConfig().setAllowSmallerIntervalsForMarshmallow(true);
        Log.d(TAG, "onCreate: MainApp");
    }
}
