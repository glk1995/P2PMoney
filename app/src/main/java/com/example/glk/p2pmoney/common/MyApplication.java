package com.example.glk.p2pmoney.common;

import android.app.Application;
import android.content.Context;
import android.os.Handler;

/**
 * Created by zgqdg on 2016/9/22.
 */

public class MyApplication extends Application {

    //不要定义过多的静态变量
    public static Context context = null;

    public static Handler handler = null;

    public static Thread mainThread = null;

    public static int mainThreadId = 0;//判断当前代码是否在主线程当中

    @Override
    public void onCreate() {
        context = getApplicationContext();
        handler = new Handler();
        mainThread = Thread.currentThread();
        mainThreadId = android.os.Process.myTid();//myTid当前线程ID,myPid当前进程ID
        CrashHandler.getInstance().init(this);
    }
}
