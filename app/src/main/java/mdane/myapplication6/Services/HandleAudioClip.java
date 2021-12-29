package mdane.myapplication6.Services;

import android.app.Service;
import android.content.Intent;
import android.media.MediaRecorder;
import android.os.Environment;
import android.os.IBinder;

import com.github.piasy.rxandroidaudio.AudioRecorder;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class HandleAudioClip extends Service implements Runnable {
    AudioRecorder mAudioRecorder;
static public boolean running;
    public HandleAudioClip() {
    }
@Override
public void onCreate(){
    new Thread(this).start();running=true;   recordingNow();
}
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {


        return START_NOT_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public void recordingNow() {
        if (mAudioRecorder == null) {

            mAudioRecorder = AudioRecorder.getInstance();

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy_mm_dd_hh_mm_ss", Locale.getDefault());
            File mAudioFile = openFileFor();
            mAudioRecorder.prepareRecord(MediaRecorder.AudioSource.CAMCORDER,
                    MediaRecorder.OutputFormat.MPEG_4, MediaRecorder.AudioEncoder.AAC,
                    mAudioFile);

            mAudioRecorder.startRecord();

        }
    }

    @Override
    public void onDestroy() {
        running=false;
        mAudioRecorder.stopRecord();
        mAudioRecorder = null;

    }


    private File openFileFor() {
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
                        File.separator +
                        dateFormat.format(new Date()) + ".file.mp4");
            }
        }
        return null;
    }

    @Override
    public void run() {

    }
}
