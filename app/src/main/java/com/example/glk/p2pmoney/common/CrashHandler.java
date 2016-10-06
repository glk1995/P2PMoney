package com.example.glk.p2pmoney.common;

import android.content.Context;
import android.os.Build;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by zgqdg on 2016/9/23.
 *
 *因为异常都交给系统处理，所以设置默认默认异常处理器
 *
 * 因为整个APP只需要这个一个来捕获异常，所以设计为单例模式
 */

//当程序崩溃，回调这个uncaughtException
public class CrashHandler implements Thread.UncaughtExceptionHandler {
    //为什么在这边不new，因为有时候不需要用这个对象，那这边new对象就没用了
    private static CrashHandler crashHandler = null;

    private CrashHandler(){

    }

    public static  CrashHandler getInstance() {
        if(crashHandler == null){
            crashHandler = new CrashHandler();
        }
        return crashHandler;
    }

    private Context mContext;
    private Thread.UncaughtExceptionHandler defaultUncaughtExceptionHandler;

    //将init()方法放入MyApplication的onCreate()方法，可以一开始就捕获异常
    public void init(Context context){

        //将CrashHandler作为系统的默认异常处理器
        this.mContext = context;
        //获取系统默认的处理器
        defaultUncaughtExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
        //讲CrashHandler设置为系统默认处理器
        Thread.setDefaultUncaughtExceptionHandler(this);

    }

    /**
     * 把这个提示信息汉化，记录一下日志信息，反馈到后台
     *
     * @param thread
     * @param ex
     */
    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        //有些需要自己处理的，就调用自己的处理方法，有些懒得处理，可以交给系统原有的处理
        if(isHandle(ex)){
            handleException(thread, ex);
        }else{
            defaultUncaughtExceptionHandler.uncaughtException(thread,ex);
        }
    }

    /**
     * 一般在实际过程中，有些异常不需要记录到后台或者不需要处理，true表示需要处理，false不需要
     * @param ex
     * @return
     */
    public boolean isHandle(Throwable ex){
        if(ex == null){
            return false;
        }
        else {
            return true;
        }
    }

    /**
     * 自定义异常处理
     * @param thread
     * @param ex
     */
    private void handleException(Thread thread, Throwable ex) {
        //如果直接这样写，不会弹出信息，因为这是对UI进行操作
        new Thread()
        {
            @Override
            public void run() {
                //在Androi系统当中，默认情况下，线程是没有开启looper消息处理的，但是主线程除外
                Looper.prepare();
                Toast.makeText(mContext, "抱歉，系统出现未知异常，即将退出...", Toast.LENGTH_SHORT).show();
                Looper.loop();
            }
        }.start();

        //收集错误信息
        collectionException(ex);

        try {
            //这个是thread,是一个对象，不是类
            thread.sleep(2000);
            //关闭所有的Activity栈
            AppManager.getInstance().removeAll();
            //杀进程
            android.os.Process.killProcess(android.os.Process.myPid());
            //关闭虚拟机,释放所有内存
            System.exit(0);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 收集一下崩溃异常信息
     * @param ex
     */
    private void collectionException(Throwable ex) {
        final String deviceInfo = Build.DEVICE + Build.VERSION.SDK_INT + Build.MODEL + Build.PRODUCT;
        final String errorInfo = ex.getMessage();

        new Thread(){
            @Override
            public void run() {
                //这里没有收集到信息，只是把错误信息打印到控制台
                Log.e("zoubo","deviceInfo---" + deviceInfo + ":errorInfo" + errorInfo);
            }
        }.start();
    }
}
