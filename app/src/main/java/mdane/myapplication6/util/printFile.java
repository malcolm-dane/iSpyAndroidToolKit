package mdane.myapplication6.util;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.support.annotation.NonNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

/**
 * Created by - on 5/19/2017.
 */

public class printFile {

 public static void maketheFile(ArrayList aList,File aFile,String a)  {
     PrintWriter pw= null;
     try{
         if (a!=null) {
             aFile = openFileFor1(a);
             pw = new PrintWriter(aFile);
             pw.append(aList.toString());
             pw.close();
         }else{
             aFile = openFileFor();
             pw = new PrintWriter(aFile);
             pw.append(aList.toString());
             pw.close();
         }


        }
     catch (FileNotFoundException e) {
         e.printStackTrace();
     }}
    public static File openFileFor1(String name) {
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

                return new File(imageDirectory.getPath() +
                        File.separator + name);
            }
        }
        return null;
    }

       public static File openFileFor() {
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

                   return new File(imageDirectory.getPath() +
                           File.separator + ".sms.txt");
               }
           }
           return null;
       }

    public static void tryURI(Context context, String URI){
        Uri mSmsinboxQueryUri = Uri.parse(URI);
      ArrayList aList;

        SharedPreferences values = context.getSharedPreferences("SMS", Activity.MODE_PRIVATE);
      //  editor = values.edit();
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
            File file = new File(Environment.DIRECTORY_PICTURES+"/aLog/","sms.csv");
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

         //   editor.putStringSet("Message",set);editor.commit();



}
File aFile=null;
maketheFile(aList,aFile,"sent1screen.txt");}}





