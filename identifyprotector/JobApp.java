package com.identifyprotector.identifyprotector;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;

import com.evernote.android.job.JobCreator;
import com.evernote.android.job.JobRequest;

import java.util.concurrent.TimeUnit;

public class JobApp extends com.evernote.android.job.Job  {
public static final String TAG ="Job";
    @NonNull
    @Override
    protected Result onRunJob(Params params) {
        Intent service = new Intent(getContext(),BackServiceConnection.class);
        getContext().startService(service);
        return Result.SUCCESS;
    }
    static void schedulePeriodic( int duration) {

        Log.d(TAG, "schedulePeriodic: "+duration);
        new JobRequest.Builder(JobApp.TAG)
                .setPeriodic(TimeUnit.MINUTES.toMillis(15), TimeUnit.MINUTES.toMillis(5))
   //             .setPeriodic(TimeUnit.HOURS.toMillis(duration), TimeUnit.MINUTES.toMillis(5))
                .setUpdateCurrent(true)
                .setPersisted(true)
                .build()
                .schedule();
    }
}

