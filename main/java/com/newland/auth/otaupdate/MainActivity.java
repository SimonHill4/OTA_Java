package com.newland.auth.otaupdate;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.FileCallBack;

import java.io.File;
import java.io.IOException;

import okhttp3.Call;
import okhttp3.Request;

public class MainActivity extends AppCompatActivity {


    private static final String TAG = "MainActivity";

    private TextView tv_result;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv_result = findViewById(R.id.tv_result);
        findViewById(R.id.bt_download).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DownloadAsyncTask().execute();
            }
        });

    }
    String url;

    @Override
    protected void onStart() {
        super.onStart();
        url = Constants.HTTP + Constants.IP + ":" + Constants.PORT + Constants.DOWNLOAD_FILE;
        Log.e(TAG, "onStart: " + url );

    }

    public class DownloadAsyncTask extends AsyncTask<Integer, String, String>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            tv_result.setText("正在下载...");
        }

        @Override
        protected String doInBackground(Integer... integers) {
            File downFile = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), "/N910 Pro_A10_OTA_V1.0.00_V1.0.01.zip");
            if (!downFile.exists()){
                try {
                    downFile.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            OkHttpUtils
                    .post()
                    .url(url)
                    .build()
                    .execute(new FileCallBack(Environment.getExternalStorageDirectory().getAbsolutePath(), "/N910 Pro_A10_OTA_V1.0.00_V1.0.01.zip")
                    {

                        @Override
                        public void onBefore(Request request, int id)
                        {

                        }

                        @Override
                        public void onError(Call call, Exception e, int id)
                        {
                           publishProgress("下载失败" + e.getMessage());
                        }

                        @Override
                        public void onResponse(File file, int id)
                        {
                            publishProgress("下载成功");
                        }
                    });
            return null;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
            if (values[0] != null){
                tv_result.setText(values[0]);
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }
    }
}