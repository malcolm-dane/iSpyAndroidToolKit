package mdane.myapplication6.Receivers;

import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageManager;

import mdane.myapplication6.Receivers.MyReceiver2;

/**
 * Created by - on 5/18/2017.
 */

public class receiverState {

    public void disableIt(Context context) {
        ComponentName component = new ComponentName(context, MyReceiver2.class);
        PackageManager pm = context.getPackageManager();
        pm.setComponentEnabledSetting(
                component,
                PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                PackageManager.DONT_KILL_APP);
    }
    public void EnableIt(Context context) {
        ComponentName component = new ComponentName(context, MyReceiver2.class);
        PackageManager pm = context.getPackageManager();
        pm.setComponentEnabledSetting(
                component,
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                PackageManager.DONT_KILL_APP);
    }

}
