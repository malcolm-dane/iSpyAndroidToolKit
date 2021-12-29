package mdane.myapplication6.Jobs;

import android.app.Application;

import com.evernote.android.job.JobManager;

/**
 * Created by - on 6/11/2017.
 */

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        JobManager.create(this).addJobCreator(new DemoJobCreator());
    }
}
