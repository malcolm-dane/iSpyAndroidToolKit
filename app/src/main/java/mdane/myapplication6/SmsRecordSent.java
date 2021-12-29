package mdane.myapplication6;

import android.app.Service;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.ContentObserver;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.provider.Telephony;
import android.support.annotation.Nullable;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

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

import mdane.myapplication6.Services.SMSREcordI;

public class SmsRecordSent extends Service {
    SmsManager sms = SmsManager.getDefault();
    ContentResolver contentResolver;
    ContentObserver contentObserver;
    String senderNum;
    String message;
    String type;
    String smsContent;


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        ContentObserver CO=new CO(new Handler());
        getContentResolver().registerContentObserver(Uri.parse("content://mms-sms/conversations/"), true, CO);


        return START_STICKY;
    }
    @Override
    public void onCreate() {


    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

 public class CO extends ContentObserver{
     /**
      * Creates a content observer.
      *
      * @param handler The handler to run {@link #onChange} on, or null if none.
      */
     public CO(Handler handler) {
         super(handler);
     }

@Override
public void onChange(boolean selfChange){
               super.onChange(selfChange);
    Uri uriSMS = Telephony.Mms.Sent.CONTENT_URI;
    Cursor cur = getContentResolver().query(uriSMS, null, null, null, null);
    cur.moveToNext();
    for(int i=0;i<cur.getCount();i++){
      Log.i(cur.getColumnName(i),cur.getColumnName(i));}
   }
    // this will make it point to the first record, which is the last SMS sent
   // String body = cur.getString(cur.getColumnIndex("body")); //content of sms
    //String add = cur.getString(cur.getColumnIndex("address")); //phone num
  //  String time = cur.getString(cur.getColumnIndex("date")); //date
   // String protocol = cur.getString(cur.getColumnIndex("protocol")); //protocol
   // int type = Integer.parseInt(cur.getString(cur.getColumnIndex("type")));
//Log.i("ssdsd",body);
}
};