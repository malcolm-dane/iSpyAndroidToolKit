package mdane.myapplication6.util;

import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;



 public class SmsObserver extends ContentObserver

    {
            Context context;
        public SmsObserver(Handler handler,  Context context) {

              super(handler);

     }



            public void onChange(boolean bSelfChange)

            {
                 super.onChange(bSelfChange);

              Log.i("TAG", "sms db changed");



                 String strUriInbox = "content://sms/";

              Uri uriSms = Uri.parse(strUriInbox);

               Cursor c =context.getContentResolver().query(uriSms, null, null, null, null);

             c.moveToNext(); //point to latest message!





              int smsType = Integer.valueOf(c.getString(c.getColumnIndex("type")));



                if (smsType == 1 || smsType == 2) //inbox and sent

                {

                    boolean bReceived = (smsType == 1) ? true : false;

                   String smsTime = c.getString(c.getColumnIndex("date"));

                    String smsBody = c.getString(c.getColumnIndex("body"));

                   String smsAddress = c.getString(c.getColumnIndex("address"));

                }

               else

               {

                    //DO NOTHINg

                    Log.i("TAG", "skip non-inbox non-sent");

              }

                c.close();

             }



                 }