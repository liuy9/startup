/*
 * Copyright (c) 2013-2014, thinkjoy Inc. All Rights Reserved.
 * 
 * Project Name: StoneDemo
 * $Id: AppManager.java 2014年9月12日 下午5:57:30 $ 
 */
package com.cicada.startup.common.manager;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.cicada.startup.common.AppContext;
import com.cicada.startup.common.utils.UiHelper;

import java.util.List;
import java.util.Stack;

/**
 * 应用程序Activity管理类：用于Activity管理和应用程序退出
 * <p>
 *
 * @since v0.0.1
 */
public class AppManager {
    private static final String TAG = AppManager.class.getSimpleName();
    /**
     * activity栈
     */
    private static Stack<Activity> activityStack;
    /**
     * 业务相关activity栈
     */
    private static Stack<Activity> businessActivityStack;
    private static AppManager instance;

    private boolean isBusinessHolder;


    private AppManager() {
    }

    /**
     * 添加业务相关activity到栈
     *
     * @param activity
     * @author liuyun
     * @since v0.1
     */
    public void addBusinessActivity(Activity activity) {
        if (businessActivityStack == null) {
            businessActivityStack = new Stack<Activity>();
        }
        businessActivityStack.add(activity);
    }


    public void setBusinessHolder(boolean businessHolder) {
        isBusinessHolder = businessHolder;
    }

    /**
     * finish业务栈
     *
     * @author liuyun
     * @since v0.1
     */
    public void finishBusinessStack() {
        if (null == businessActivityStack) {
            return;
        }
        for (int i = 0, size = businessActivityStack.size(); i < size; i++) {
            if (null != businessActivityStack.get(i)) {
                businessActivityStack.get(i).finish();
            }
        }
        businessActivityStack.clear();
        this.isBusinessHolder = false;
    }

    /**
     * 清空业务栈
     *
     * @author liuyun
     * @since v0.1
     */
    public void clearBussinessStack() {
        if (businessActivityStack != null) {
            businessActivityStack.clear();
            businessActivityStack = null;
        }
    }

    /**
     * 单实例 , UI无需考虑多线程同步问题
     */
    public static AppManager getInstance() {
        if (instance == null) {
            instance = new AppManager();
        }
        return instance;
    }

    /**
     * 添加Activity到栈
     */
    public void addActivity(Activity activity) {
        if (activityStack == null) {
            activityStack = new Stack<Activity>();
        }
        activityStack.add(activity);

        if (isBusinessHolder) {
            addBusinessActivity(activity);
        }
    }

    /**
     * 获取当前Activity（栈顶Activity）
     */
    public Activity currentActivity() {
        if (activityStack == null || activityStack.isEmpty()) {
            return null;
        }
        Activity activity = activityStack.lastElement();
        return activity;
    }

    /**
     * 获取当前Activity（栈顶Activity） 没有找到则返回null
     */
    public Activity findActivity(Class<?> cls) {
        Activity activity = null;
        for (Activity aty : activityStack) {
            if (aty.getClass().equals(cls)) {
                activity = aty;
                break;
            }
        }
        return activity;
    }

    public void finishToActivity(Class<?> cls) {
        for (Activity activity : activityStack) {
            if (!activity.getClass().equals(cls)) {
                activity.finish();
            }
        }
    }

    /**
     * 结束当前Activity（栈顶Activity）
     */
    public void finishActivity() {
        if (activityStack != null && activityStack.size() > 0) {
            Activity activity = activityStack.lastElement();
            UiHelper.hideSoftInput(activity);
            finishActivity(activity);
        }
    }

    /**
     * 结束指定的Activity(重载)
     */
    public void finishActivity(Activity activity) {
        if (activity != null) {
            activityStack.remove(activity);
            activity.finish();
            activity = null;
        }
    }

    /**
     * 结束指定的Activity(重载)
     */
    public void finishActivity(Class<?> cls) {
        for (Activity activity : activityStack) {
            if (activity.getClass().equals(cls)) {
                finishActivity(activity);
                break;
            }
        }
    }

    /**
     * 关闭除了指定activity以外的全部activity 如果cls不存在于栈中，则栈全部清空
     *
     * @param cls
     */
    public void finishOthersActivity(Class<?> cls) {
        for (Activity activity : activityStack) {
            if (!(activity.getClass().equals(cls))) {
                finishActivity(activity);
            }
        }
    }

    public boolean isLastActivity() {
        if (null != activityStack && 1 == activityStack.size()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 结束所有Activity
     */
    public void finishAllActivity() {
        for (int i = 0, size = activityStack.size(); i < size; i++) {
            if (null != activityStack.get(i)) {
                activityStack.get(i).finish();
            }
        }
        activityStack.clear();
        this.isBusinessHolder = false;
    }

    /**
     * 程序是否在后台运行
     *
     * @return
     * @author xnjiang
     * @since v0.0.1
     */
    public static boolean isBackground() {
        ActivityManager activityManager = (ActivityManager) AppContext.getContext().getSystemService(Context.ACTIVITY_SERVICE);
        List<RunningAppProcessInfo> appProcesses = activityManager.getRunningAppProcesses();
        for (RunningAppProcessInfo appProcess : appProcesses) {
            if (appProcess.processName.equals(AppContext.getContext().getPackageName())) {
                if (appProcess.importance == RunningAppProcessInfo.IMPORTANCE_BACKGROUND) {
                    return true;
                } else {
                    return false;
                }
            }
        }
        return false;
    }

    /**
     * 用来判断服务是否运行.
     *
     * @param context
     * @param className 判断的服务名字
     * @return true 在运行 false 不在运行
     */
    public static boolean checkServiceRunning(Context context, String className) {
        boolean isRunning = false;
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> serviceList = activityManager.getRunningServices(100);
        if (!(serviceList.size() > 0)) {
            return false;
        }
        for (int i = 0; i < serviceList.size(); i++) {
            if (serviceList.get(i).service.getClassName().equals(className) == true) {
                isRunning = true;
                break;
            }
        }
        return isRunning;
    }


    public static boolean isAppOnForeground() {
        // Returns a list of application processes that are running on the
        // device

        ActivityManager activityManager = (ActivityManager) AppContext.getContext().getApplicationContext().getSystemService(Context.ACTIVITY_SERVICE);
        String packageName = AppContext.getContext().getApplicationContext().getPackageName();

        List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager
                .getRunningAppProcesses();
        if (null == appProcesses)
            return false;

        for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
            // The name of the process that this object is associated with.
            if (appProcess.processName.equals(packageName)
                    && appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                return true;
            }
        }
        return false;
    }

    public static String getPackageName(Context context) {
        String packageName = context.getPackageName();
        return packageName;
    }

    /**
     * 重新启动app
     */
    public void restartApp() {
        // 关闭通知栏显示
        UiHelper.cancelNotification(AppContext.getContext());

        // ToastManager.getInstance().showToast(this, "正在重新启动...");
        Intent intent = AppContext.getContext().getPackageManager().getLaunchIntentForPackage(getPackageName(AppContext.getContext()));
        PendingIntent restartIntent = PendingIntent.getActivity(AppContext.getContext(), 0, intent, Intent.FLAG_ACTIVITY_NEW_TASK);
        AlarmManager mgr = (AlarmManager) AppContext.getContext().getApplicationContext().getSystemService(Context.ALARM_SERVICE);
        mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 2000, restartIntent); // 2秒钟后重启应用

        AppManager.getInstance().finishAllActivity();// 结束所有Activity

        android.os.Process.killProcess(android.os.Process.myPid());// 关闭进程
        // System.exit(0);
    }


    public void init() {
        this.isBusinessHolder = false;
    }
}
