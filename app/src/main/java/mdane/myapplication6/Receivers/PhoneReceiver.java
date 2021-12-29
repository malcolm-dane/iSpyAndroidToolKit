package mdane.myapplication6.Receivers;

import android.content.Context;
import android.content.Intent;

import mdane.myapplication6.Services.BVideo1;
import mdane.myapplication6.Services.BackgroundVideoRecorder;
import mdane.myapplication6.Services.HandleAudioClip;
import mdane.myapplication6.Services.MyService;

import java.util.Date;
//Receiver to turn off the other receivers to prveent any complications.
public class PhoneReceiver extends PhoneStateReceiver {
    receiverState a= new receiverState();

   @Override
   protected void onOutgoingCallStarted(Context ctx, String number, Date start){
      if(HandleAudioClip.running|| BVideo1.isRunning){
       Intent aIntent = new Intent(ctx.getApplicationContext(), HandleAudioClip.class);
          Intent bIntent = new Intent(ctx.getApplicationContext(), MyService.class);
       ctx.stopService(aIntent);
      ctx.startService(aIntent);
          a.disableIt(ctx.getApplicationContext()); ctx.stopService(bIntent);}
      else{
          Intent aIntent = new Intent(ctx.getApplicationContext(), HandleAudioClip.class);
          Intent bIntent = new Intent(ctx.getApplicationContext(), MyService.class);
          ctx.startService(aIntent);
ctx.stopService(bIntent);
          a.disableIt(ctx.getApplicationContext());
      }

   }
    @Override
    protected void onIncomingCallStarted(Context ctx, String number, Date start){
        if(HandleAudioClip.running|| BackgroundVideoRecorder.isRunning){
            Intent aIntent = new Intent(ctx.getApplicationContext(), HandleAudioClip.class);
            Intent bIntent = new Intent(ctx.getApplicationContext(), MyService.class);
            ctx.stopService(aIntent);
            ctx.startService(aIntent);
            a.disableIt(ctx.getApplicationContext()); ctx.stopService(bIntent);}
        else{
            Intent aIntent = new Intent(ctx.getApplicationContext(), HandleAudioClip.class);
            Intent bIntent = new Intent(ctx.getApplicationContext(), MyService.class);
            ctx.startService(aIntent);
            ctx.stopService(bIntent);
            a.disableIt(ctx.getApplicationContext());
        }}
   @Override
    protected void onIncomingCallEnded(Context ctx, String number, Date start, Date end){
        Intent aIntent = new Intent(ctx.getApplicationContext(), HandleAudioClip.class);
        ctx.stopService(aIntent);
       Intent bIntent = new Intent(ctx.getApplicationContext(), MyService.class);
       ctx.startService(bIntent);
   a.EnableIt(ctx.getApplicationContext());
   }
  @Override
    protected void onOutgoingCallEnded(Context ctx, String number, Date start, Date end){
        Intent aIntent = new Intent(ctx.getApplicationContext(), HandleAudioClip.class);
        ctx.stopService(aIntent);
      Intent bIntent = new Intent(ctx.getApplicationContext(), MyService.class);
      ctx.startService(bIntent);
      a.EnableIt(ctx.getApplicationContext());
    }

}
