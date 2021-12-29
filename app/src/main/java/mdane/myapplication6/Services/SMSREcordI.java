package mdane.myapplication6.Services;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by - on 6/24/2017.
 */

public class SMSREcordI extends Service {

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId){
       SMSContentObserver contentObserver = new SMSContentObserver(this, new Handler());
        Uri uri = Uri.parse("content://sms");
      //  getContentResolver().registerContentObserver(uri, true, contentObserver);
        getContentResolver().registerContentObserver(Uri.parse("content://sms/"), true,new SMSContentObserver(getApplicationContext(),new Handler()));
        return START_STICKY;}
    public class SMSContentObserver extends ContentObserver {
        private final Uri uri = Uri.parse("content://sms");
        private  final String COLUMN_TYPE = "type";
        private  final int MESSAGE_TYPE_SENT = 2;
        Context context;
        Handler handler;

        public SMSContentObserver(Context c, Handler handler) {
            super(handler);
            // TODO Auto-generated constructor stub
            this.context = c;
            this.handler = handler;
        }

        @Override
        public void onChange(boolean selfChange) {
            Cursor cursor = null;

            try {
                cursor = getContentResolver().query(uri, null, null, null, null);

                if (cursor != null && cursor.moveToFirst()) {
                    int type = cursor.getInt(cursor.getColumnIndex(COLUMN_TYPE));

                    if (type == MESSAGE_TYPE_SENT) {
                        // Sent message

                        Log.i("sent","sent");
                    }
                }
            }
            finally {
                if (cursor != null)
                    cursor.close();
            }
        }

        @Override            public boolean deliverSelfNotifications()
        {                return true;            }




}

}