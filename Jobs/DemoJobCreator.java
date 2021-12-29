package mdane.myapplication6.Jobs;

import com.evernote.android.job.Job;
import com.evernote.android.job.JobCreator;

/**
 * Created by - on 6/11/2017.
 */

public class DemoJobCreator implements JobCreator {

    @Override
    public Job create(String tag) {
        switch (tag) {
            case DemoSyncJob.TAG:
                return new DemoSyncJob();
            default:
                return null;
        }
    }
}