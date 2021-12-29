package mdane.myapplication6.util;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;

import mdane.myapplication6.Services.MyService;

/**
 * Created by - on 6/11/2017.
 */

public class JobSched {


        // schedule the start of the service every 10 - 30 seconds
        @RequiresApi(api = Build.VERSION_CODES.M)
        public static void scheduleJob(Context context) {
            ComponentName serviceComponent = new ComponentName(context, MyService.class);
            JobInfo.Builder builder = new JobInfo.Builder(0, serviceComponent);
            builder.setMinimumLatency(1 * 1000); // wait at least
            builder.setOverrideDeadline(3 * 1000); // maximum delay
            //builder.setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED); // require unmetered network
            //builder.setRequiresDeviceIdle(true); // device should be idle
            //builder.setRequiresCharging(false); // we don't care if the device is charging or not
            JobScheduler jobScheduler = context.getSystemService(JobScheduler.class);
            jobScheduler.schedule(builder.build());
        }

    }

