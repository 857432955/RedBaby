package com.itheima.redbaby;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class SplashActivity extends Activity {

    private static final String GET_URL = "";
    @Bind(R.id.progLoadProgressBar)
    ProgressBar progLoadProgressBar;
    @Bind(R.id.splash_tv_version)
    TextView splashTvVersion;
    /** 进度条 */
    private ProgressDialog mProgressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
      new Thread(new Runnable() {
          @Override
          public void run() {
              try {
                  Thread.sleep(3000);
              } catch (InterruptedException e) {
                  e.printStackTrace();
              };
              Intent intent = new Intent(SplashActivity.this,MainActivity.class);
              startActivity(intent);
              finish();
          }
      }).start();


    }

    /**
     * 获取当前版本
     */
    private String getClientVersion() throws PackageManager.NameNotFoundException {
        PackageManager packageManager = getPackageManager();
        PackageInfo packageInfo = packageManager.getPackageInfo(getPackageName(), 0);

        return packageInfo.versionName;
    }
    /**
     * 获取网络版本
     * okhttp异步请求,会自己开启子线程
     */
    private void getServerVersion() {
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder().get().url(GET_URL).build();


        okHttpClient.newCall(request).enqueue(new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
                System.out.print("请求失败");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                System.out.print("请求成功");
                if (!response.isSuccessful()) {
                    throw new IOException("Unexpected code " + response);

                } else {
                    String serverVersion1 = response.header("appversion");
                }
            }
        });

    }

    /**
     * 进入主页
     */
    private void gotoHome() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    /**
     * 初始化进度条
     */
    private void initProgressDialog() {
        mProgressDialog = new ProgressDialog(this);// 进度条初始化
        mProgressDialog.setCancelable(false);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.setMessage(getString(R.string.downning));
        mProgressDialog.show();
    }

}
