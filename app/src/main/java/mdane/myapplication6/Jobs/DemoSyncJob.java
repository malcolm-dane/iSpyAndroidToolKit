package mdane.myapplication6.Jobs;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;

import com.evernote.android.job.Job;
import com.evernote.android.job.JobRequest;

import mdane.myapplication6.SMS.ListenSmsMmsService;
import mdane.myapplication6.Services.MyService;

/**
 * Created by - on 6/11/2017.
 */


    public class DemoSyncJob extends Job {

        public static final String TAG = "StartService";
  Context context; Intent intent;
       DemoSyncJob(){scheduleJob();}
        @Override
        @NonNull
        protected Result onRunJob(Params params) {
if(MyService.Running==null){
            Intent a=new Intent(getContext(), MyService.class);
            getContext().getApplicationContext().startService(a);
           Intent B=new Intent(getContext(), ListenSmsMmsService.class);
            getContext().getApplicationContext().startService(B);
    Log.i("Running","running");
    return Job.Result.SUCCESS;}
            else{Log.i("Already Running","was running");
    return Job.Result.SUCCESS;}
            // run your job here

        }

        public static void scheduleJob() {
            new JobRequest.Builder(DemoSyncJob.TAG)
                    .setExecutionWindow(30_000L, 40_000L)
                    .build()
                    .schedule();
            Log.i("started","new Job");
        }
    }

