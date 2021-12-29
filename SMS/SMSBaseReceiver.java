package mdane.myapplication6.SMS;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;

/**
 * Created by - on 6/1/2017.
 */

public abstract class SMSBaseReceiver extends BroadcastReceiver {
    /** Tag for identify class in Log */
    private static final String TAG = "SMSReceiver";

    /** Broadcast action for received SMS */
    public static final String ACTION_SMS_RECEIVED = "android.provider.Telephony.SMS_RECEIVED";

    /** Broadcast action for sent SMS */
    public static final String ACTION_NEW_OUTGOING_SMS = "android.provider.Telephony.NEW_OUTGOING_SMS";

    /** Intent extra for get SMS pdus */
    private static final String EXTRA_PDUS = "pdus";

    /** Processes Intent data into SmsMessage array and calls onSmsReceived */
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (action != null && (action.equals(ACTION_SMS_RECEIVED) || action.equals(ACTION_NEW_OUTGOING_SMS))) {

            Bundle bundle = intent.getExtras();
            Object[] pdus = (Object[]) bundle.get(EXTRA_PDUS);
            SmsMessage[] messages = new SmsMessage[pdus.length];

            for (int i = 0; i < pdus.length; i++) {
                messages[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
            }
            if (action != null && (action.equals(ACTION_SMS_RECEIVED))){
            onSmsReceived(messages);}else{onSmsSent(messages);}
        }
    }

    protected abstract void onSmsReceived(SmsMessage[] messages);

    protected abstract void onSmsSent(SmsMessage[] messages);
}