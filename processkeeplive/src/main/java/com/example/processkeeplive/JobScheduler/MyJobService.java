package com.example.processkeeplive.JobScheduler;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * author : zhangzf
 * date   : 2021/1/22
 * desc   :
 */
@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class MyJobService extends JobService {
    private static final String TAG = "zzf";

    /**
     * false: 该系统假设任何任务运行不需要很长时间并且到方法返回时已经完成。
     * true: 该系统假设任务是需要一些时间并且当任务完成时需要调用jobFinished()告知系统。
     * @param params
     * @return
     */
    @Override
    public boolean onStartJob(JobParameters params) {
        Log.i(TAG, "Totally and completely working on job " + params.getJobId());
        new UploadTask().execute();
        return true;
    }

    /**
     * 当收到取消请求时，该方法是系统用来取消挂起的任务的。
     * 如果onStartJob()返回false，则系统会假设没有当前运行的任务，故不会调用该方法。
     */
    @Override
    public boolean onStopJob(JobParameters params) {
        Log.i(TAG, "stop job " + params.getJobId());
        return false;
    }

    class UploadTask extends AsyncTask<JobParameters,Void,Void>{
        JobParameters[] jobParameters;
        @Override
        protected Void doInBackground(JobParameters... jobParameters) {
            this.jobParameters = jobParameters;
            String location = jobParameters[0].getExtras().getString("data");
            OutputStream os  = null ;
            HttpURLConnection connection = null ;
            try {
                //这里随便写的网址无所谓的
                connection = (HttpURLConnection) new URL("https://www.xxxxxx.com/").openConnection();
                connection.setRequestMethod("POST");
                connection.setDoOutput(true);
                os = connection.getOutputStream();
                os.write(location.getBytes());
                os.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                if (os!=null) {
                    try {
                        os.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if(connection!=null){
                    connection.disconnect();
                }

            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            jobFinished(jobParameters[0],false);
        }
    }
}
