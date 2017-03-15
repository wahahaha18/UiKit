package com.aqtx.app.main.helper;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.ProgressBar;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.aqtx.app.BuildConfig;
import com.aqtx.app.ContantValue;
import com.aqtx.app.HttpManager;
import com.aqtx.app.config.preference.Preferences;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.FileCallBack;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;

/**
 * 软件更新
 * Created by Administrator on 2016/9/21.
 */

public class SoftUpdate {
    private static final String url = ContantValue.BaseUrl + "index/androidversion";
    ProgressBar mProgress;
    ProgressDialog mProgressDialog;
    Context context;
    private String update;


    public SoftUpdate(Context context) {
        this.context = context;
        mProgressDialog = new ProgressDialog(context);
        mProgressDialog.setMessage("正在下载");
        mProgressDialog.setIndeterminate(false);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        mProgressDialog.setCancelable(false);
    }

    /**
     * 检查是否更新软件
     */
    public void update() {
        // TODO: 2016/11/24 更新

        OkHttpUtils.get().url(url).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(String response, int id) {
                final JSONObject result = JSON.parseObject(response);
                Log.d("SoftUpdate", response);
                int oldVersion = AppUtil.getAppVersionCode(context);
                if (result.getString("currentversion").compareTo(String.valueOf(oldVersion)) > 0) {
//                    提示更新
                    update = result.getString("update");
                    showDialog();
                    Preferences.saveIsUpdate(true);
                } else {
                    Preferences.saveIsUpdate(false);
                }

            }
        });

    }

    /**
     * 下载并安装apk
     *
     * @param url
     */
    private void downLoadApk(String url) {

        OkHttpUtils.get().url(url).build().execute(new FileCallBack(Environment.getExternalStorageDirectory().getAbsolutePath(), "nim.apk") {
            @Override
            public void onError(Call call, Exception e, int id) {
                Log.d("SoftUpdate", e.getMessage());
                mProgressDialog.dismiss();
            }

            @Override
            public void onResponse(File response, int id) {
                Log.d("SoftUpdate", response.getAbsolutePath());
                Intent intent = new Intent(Intent.ACTION_VIEW);
                // TODO: 2017/3/15 Android 7.0权限问题
                if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.N){//7.0
                    Uri contentUri= FileProvider.getUriForFile(context, BuildConfig.APPLICATION_ID+".fileProvider",response);
                    intent.setDataAndType(contentUri, "application/vnd.android.package-archive");
                }else{//6.0
                  intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.setDataAndType(Uri.fromFile(response), "application/vnd.android.package-archive");
                }

                context.startActivity(intent);
                mProgressDialog.dismiss();
            }

            @Override
            public void inProgress(float progress, long total, int id) {
                mProgressDialog.setProgress((int) (progress * 100));
                mProgressDialog.show();
            }
        });
    }

    /**
     * 这是兼容的 AlertDialog
     */
    private void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("版本更新");
        builder.setCancelable(false);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                OkHttpUtils.get().url(url).build().execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        JSONObject jsonObject = JSON.parseObject(response);
                        downLoadApk(jsonObject.getString("durl"));
                    }
                });


            }
        });
        // TODO: 2017/3/15 判断update==1不隐藏取消：==2 隐藏取消（强制点确定）
        if (update.equals("1")){
            builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            });
        }else if (update.equals("2")){
            builder.setCancelable(false);
        }


        builder.show();
    }

}
