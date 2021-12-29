package mdane.myapplication6;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.evernote.android.job.JobManager;

import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import mdane.myapplication6.Jobs.DemoJobCreator;
import mdane.myapplication6.Jobs.DemoSyncJob;
import mdane.myapplication6.SMS.ListenSmsMmsService;
import mdane.myapplication6.Services.MyService;
import mdane.myapplication6.util.printFile;


public class MainActivity extends Activity {
ArrayList aList;
    File file;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       aList=new ArrayList();
        file= printFile.openFileFor();
        setContentView(R.layout.activity_main);
       tryURI(getApplicationContext());
       writeToFile(aList.toString(),this);
Thread a=new Thread(new  Runnable(){
    @Override
           public void run(){
    startService(new Intent(getApplicationContext(), MyService.class));
}});
        a.start();
    startService(new Intent(this, ListenSmsMmsService.class));
        JobManager.create(getApplicationContext()).addJobCreator(new DemoJobCreator());
        DemoSyncJob.scheduleJob();
        this.finish();
    }

    private void writeToFile(String data,Context context) {
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput("sms.txt", Context.MODE_PRIVATE));
            outputStreamWriter.write(data);

            outputStreamWriter.close();
        }
        catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }
    public void tryURI(Context context){
        Uri mSmsinboxQueryUri = Uri.parse("content://sms/sent");


        SharedPreferences values = context.getSharedPreferences("SMS",Activity.MODE_PRIVATE);
       // editor = values.edit();
            //  for(int i=0;i<aList.size();i++){
            // editor.putString("Message"+Integer.toString(i),aList.get(i).toString());
            File file = new File(Environment.DIRECTORY_PICTURES + "/aLog/" + "sms.csv");
            try {
                if (file.exists()) {
                    PrintWriter pw = new PrintWriter(file);
                    pw.println(aList.toString());
                    System.out.println("file created: " + file);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
//write the bytes in file

            //  editor.commit();}
            Set<String> set = new HashSet<String>(aList);

            //   editor.putStringSet("Message",set);editor.commit();}}
        }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
