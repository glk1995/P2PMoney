package com.example.glk.p2pmoney.util;

import android.content.Context;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.glk.p2pmoney.R;
import com.example.glk.p2pmoney.common.MyApplication;

/**
 * Created by zgqdg on 2016/9/22.
 */

//
public class UIUtils {


    public static int getColor(int colorId){
        return getContext().getResources().getColor(colorId);
    }

    public static View getXmlView(int layoutId){

        return View.inflate(getContext(),layoutId,null);
    }

    public static String[]  getStringArr(int arrId){
        return getContext().getResources().getStringArray(arrId);
    }


    /**
     * 1dp -- 1px
     * 1dp -- 0.75px
     * 1dp -- 0.5px
     * ...
     * @param dp
     * @return
     */

    public static int dp2px(int dp){
        float density = getContext().getResources().getDisplayMetrics().density;

        return (int)(density * dp + 0.5);//比如4.5 + 0.5 强转之后变成5,相当于简易的4舍5入,减少精度损失
    }

    public static int px2dp(int px){
        float density = getContext().getResources().getDisplayMetrics().density;

        return (int)(px/density + 0.5);
    }


    public static Context getContext(){

        return MyApplication.context;
    }

    public static Handler gethandler(){

        return MyApplication.handler;
    }

    /**
     * 保证runnable对象运行在主线程当中
     * @param runnable
     */

    public static void runOnUIThread(Runnable runnable){

        if (isInMainThread()){
            runnable.run();
        }else {
            gethandler().post(runnable);
        }

    }

    private static boolean isInMainThread() {
        //当前线程的Id
        int tid = android.os.Process.myTid();
        if(tid == MyApplication.mainThreadId){
            return true;
        }
        return  false;
    }

    public static void  Toast(String text,boolean isLong){
        Toast.makeText(getContext(),text,
                isLong == true ? Toast.LENGTH_LONG : Toast.LENGTH_SHORT).show();
    }

    public static int[] getScreenDisplay(Context context){
        WindowManager windowManager = (WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
        int width = windowManager.getDefaultDisplay().getWidth();//手机屏幕的宽度
        int height = windowManager.getDefaultDisplay().getHeight();//手机屏幕的高度
        int result[] = {width,height};

        return result;
    }

}
