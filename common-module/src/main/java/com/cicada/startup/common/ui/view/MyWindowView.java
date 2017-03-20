package com.cicada.startup.common.ui.view;

import android.content.Context;
import android.graphics.PixelFormat;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.cicada.startup.common.AppContext;
import com.cicada.startup.common.R;

/**
 * @author houwenpeng
 * @version V1.0
 * @Package daydaybaby
 * @Title com.cicada.daydaybaby.common.ui.view
 * @date 16/9/1
 * @Description:
 */
public class MyWindowView  {

    private static MyWindowView instance;

    private boolean isShown = false;
    private WindowManager windowManager;
    private View v;
    public static MyWindowView getInstance(){
        if(instance == null){
            synchronized (MyWindowView.class){
                instance = new MyWindowView();
            }
        }
        return instance;
    }

    private View getView(){
        v = ((LayoutInflater)AppContext.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.mywindow_layout,null);
        return v;
    }


    public void setTextInfo(final String a,final String b,final String c){
        if(windowManager==null){
            startWindow();
        }
        if(isShown){
            new Handler(Looper.getMainLooper()){
                @Override
                public void handleMessage(Message msg) {
                    super.handleMessage(msg);
                    ((TextView)v.findViewById(R.id.memory_total)).setText(a);
                    ((TextView)v.findViewById(R.id.current_memory)).setText(b);
                    ((TextView)v.findViewById(R.id.memory_low)).setText(c);
                }
            }.sendEmptyMessage(0);

        }
    }

    public void startWindow(){
        if(windowManager == null){
            initWindowManager();
        }

    }

    private void initWindowManager(){
        windowManager = (WindowManager) AppContext.getContext().getSystemService(Context.WINDOW_SERVICE);
        final WindowManager.LayoutParams params = new WindowManager.LayoutParams();

        // 类型
        params.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;

        // WindowManager.LayoutParams.TYPE_SYSTEM_ALERT

        // 设置flag

        int flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        // | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        // 如果设置了WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE，弹出的View收不到Back键的事件
        params.flags = flags;
        // 不设置这个弹出框的透明遮罩显示为黑色
        params.format = PixelFormat.TRANSLUCENT;
        // FLAG_NOT_TOUCH_MODAL不阻塞事件传递到后面的窗口
        // 设置 FLAG_NOT_FOCUSABLE 悬浮窗口较小时，后面的应用图标由不可长按变为可长按
        // 不设置这个flag的话，home页的划屏会有问题

        params.width = WindowManager.LayoutParams.WRAP_CONTENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;

        params.gravity = Gravity.TOP | Gravity.RIGHT;

        windowManager.addView(getView(), params);
        isShown = true;
    }


    public void destoryWindow(){
        if (isShown && null != v){
            windowManager.removeView(v);
            isShown = false;
            windowManager = null;
        }
    }


}
