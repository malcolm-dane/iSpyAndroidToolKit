package mdane.myapplication6.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.UiThread;
import android.util.Log;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by - on 6/10/2017.
 */

public class ThreadedModel extends Activity {
Intent intent;
    Context ccontext;
    public static void ThreadedService(final Context context, final Object aObject){

        Thread aThread=new Thread(
                new Runnable() {
                    @Override
                    public void run() {
Log.i("Threaded","Thread");
             Intent intent= new Intent(context, (Class<?>) aObject);
                    context.getApplicationContext().startService(intent);}

   }); aThread.start();

    }
    public Thread ThreadedMethod(final Context context, final Object aObject){

        Thread aThread=new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        Intent intent= new Intent(context, (Class<?>) aObject);
                        context.getApplicationContext().startActivity(intent);

                    }

                });}});aThread.start(); return aThread;

    }

}
