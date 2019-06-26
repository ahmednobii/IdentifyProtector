package com.identifyprotector.identifyprotector;

import com.evernote.android.job.Job;
import com.evernote.android.job.JobCreator;
import static com.identifyprotector.identifyprotector.JobApp.TAG;

public class jobCreatorApp implements JobCreator {


    @Override
    public Job create(String tag) {
        switch(tag)
        {
            case TAG:
                return new JobApp();
            default:
                return null;
        }
    }
}
