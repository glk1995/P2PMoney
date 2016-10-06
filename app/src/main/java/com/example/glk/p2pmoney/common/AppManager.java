package com.example.glk.p2pmoney.common;

import android.app.Activity;

import java.util.Stack;

/**
 * Created by zgqdg on 2016/9/23.
 *
 * AppManager设计成单例模式
 *
 * 统一app程序最当中所有activity栈管理
 *
 * 添加、删除指定、删除当前、删除所有、求栈大小....
 *
 */

public class AppManager {

    Stack<Activity> activityStack= new Stack<>();

    public static AppManager appManager = null;

    private  AppManager(){

    }

    //单例模式
    public static AppManager getInstance() {
        if(appManager == null){
            appManager = new AppManager();
        }

        return appManager;
    }

    //添加Activity到栈中
    public void addActivity(Activity activity){
        activityStack.add(activity);
    }

    //关闭一个指定的Activity
    public void removeActivity(Activity activity){
        for(int i = activityStack.size() - 1; i >= 0;i--){
            Activity temp = activityStack.get(i);
            if(temp.getClass().equals(activity.getClass())){
                temp.finish();
                activityStack.remove(temp);
                break;
            }
        }
    }

    //删除当前Activity,其实我觉得不建议用Stack，用ArrayDeque的效率更高
    public void removeCurrent(){
        Activity lastElement = activityStack.lastElement();
        lastElement.finish();
        activityStack.remove(lastElement);
    }

    public void removeAll(){
        for(int i = activityStack.size() - 1; i >= 0;i--){
            Activity temp = activityStack.get(i);
            temp.finish();
            activityStack.remove(temp);
        }
    }

    //返回当前栈多少个元素
    public int getSize(){
        return activityStack.size();
    }


}
