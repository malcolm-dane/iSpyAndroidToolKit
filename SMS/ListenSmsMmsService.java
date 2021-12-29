package mdane.myapplication6.SMS;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import android.app.Service;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.ContentObserver;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import mdane.myapplication6.util.BetterPrinter;

public class ListenSmsMmsService extends Service {

    private ContentResolver contentResolver;


    String substr;
    int k;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        Log.v("Debug", " service creatd.........");
    }

    public void registerObserver() {

        contentResolver = getContentResolver();
        contentResolver.registerContentObserver(
                Uri.parse("content://sms/"),
                true, new SMSObserver(new Handler()));
        Log.v("Debug", " in registerObserver method.........");
    }

    //start the service and register observer for lifetime
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.v("Debug", "Service has been started..");
        Toast.makeText(getApplicationContext(),
                "Service has been started.. ",
                Toast.LENGTH_SHORT).show();
        registerObserver();

        return START_STICKY;
    }
    public class SMSObserver extends ContentObserver {
        private Handler m_handler = null;

        public SMSObserver(Handler handler){
            super(handler);
            m_handler = handler;
        }

        @Override
        public void onChange(boolean selfChange) {
            super.onChange(true);
            Uri uriSMSURI = Uri.parse("content://sms");

            Cursor cur = getContentResolver().query(uriSMSURI, null, null,
                    null, null);
            cur.moveToNext();

            String protocol = cur.getString(cur.getColumnIndex("protocol"));

            if(protocol == null) {

              HashMap aMap=new HashMap();
               ArrayList aList=new ArrayList();
                aMap.put("body",cur.getString(cur.getColumnIndex("body")));
                aMap.put("num", cur.getString(cur.getColumnIndex("address")));
                aMap.put("Time",cur.getString(cur.getColumnIndex("date")));
                aMap.put("Direction","sent");
               aList.add(aMap);
                BetterPrinter.maketheFile(aList,"OKFINALLYWOKING.txt");
                Log.i("sent",cur.getString(cur.getColumnIndex("body")));
                Log.i("sent",aList.toString());
                //the message is sent out just now
            }
            else {
                //the message is received just now
            }
        }
    }

}