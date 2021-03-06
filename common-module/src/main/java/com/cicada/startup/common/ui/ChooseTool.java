package com.cicada.startup.common.ui;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.PopupWindow;

import com.cicada.startup.common.R;
import com.cicada.startup.common.ui.view.wheelview.OnWheelChangedListener;
import com.cicada.startup.common.ui.view.wheelview.WheelView;
import com.cicada.startup.common.ui.view.wheelview.WheelViewString;

import java.util.ArrayList;

/**
 * 选择日期
 * <p>
 * Create time: 16/7/12 9:53
 *
 * @author liuyun.
 */
public class ChooseTool {

    private PopupWindow dateSelectorPopupWindow;
    private View mParent;
    private ChooseInterface chooseInterface;
    private Context mContext;

    private WheelView mWheelView;
    private ArrayList<String> datas;
    private Object selectObj;

    public ChooseTool(Context context) {
        mContext = context;
    }

    /**
     * 初始化日期选择器
     *
     * @param parentView 显示日期选择器的view
     * @author liuyun
     * @since v0.1
     */
    public void initSelector(View parentView, ArrayList<String> datas) {
        mParent = parentView;
        this.datas = datas;
        initView();
        initWheelView();
    }

    private void initView() {
        // 创建一个popupwindow的载体View
        View popupView = LayoutInflater.from(mContext).inflate(R.layout.activity_date_selector, null);
        // 实例化PopupWindow
        if (dateSelectorPopupWindow == null) {
            dateSelectorPopupWindow = new PopupWindow(popupView, android.view.ViewGroup.LayoutParams.MATCH_PARENT, android.view.ViewGroup.LayoutParams.MATCH_PARENT, true);
        }
        // 这两个属性设置点击popup以外区域隐藏
        dateSelectorPopupWindow.setBackgroundDrawable(new BitmapDrawable());
        dateSelectorPopupWindow.setOutsideTouchable(true);
        dateSelectorPopupWindow.setAnimationStyle(R.style.popup_animation);

        mWheelView = (WheelView) popupView.findViewById(R.id.wheelviewYear);
        popupView.findViewById(R.id.wheelviewMonth).setVisibility(View.GONE);
        popupView.findViewById(R.id.wheelviewDay).setVisibility(View.GONE);

        popupView.findViewById(R.id.linearLayoutCancel).setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                dismissPopup();
            }
        });

        popupView.findViewById(R.id.buttonCancel).setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                dismissPopup();
            }
        });
        popupView.findViewById(R.id.buttonSure).setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                chooseInterface.selected(selectObj);
                dismissPopup();
            }
        });
    }

    private void initWheelView() {
        mWheelView.setIsShowCenterRect(false);
        mWheelView.setTextColor(mContext.getResources().getColor(R.color.wheelview_color_black), mContext.getResources().getColor(R.color.wheelview_color_gray));
        mWheelView.addChangingListener(new MyWheelViewOnChangingListener());
        setCurWheelView();
        mWheelView.setVisibleItems(datas.size()>3? 3 : datas.size());
        mWheelView.setCurrentItem(datas.size()>3?3:datas.size()-1);
    }

    /**
     * 设置当前时间的滚轮
     */
    private void setCurWheelView() {
        new WheelViewString(mContext, mWheelView, datas, 0);
    }

    /**
     * 滚轮滑动监听器
     */
    private class MyWheelViewOnChangingListener implements OnWheelChangedListener {

        @Override
        public void onChanged(WheelView wheel, int oldValue, int newValue) {
            selectObj = datas.get(newValue);
        }
    }



    public void showSelector() {
        if (!dateSelectorPopupWindow.isShowing()) {
            dateSelectorPopupWindow.getContentView().measure(0, 0);
            dateSelectorPopupWindow.showAtLocation(mParent, Gravity.CENTER, 0, 0);
            dateSelectorPopupWindow.update();
        }
    }

    private void dismissPopup() {
        if (dateSelectorPopupWindow != null) {
            dateSelectorPopupWindow.dismiss();
        }
    }

    public void setChooseInterface(ChooseInterface selectDateInterface) {
        this.chooseInterface = selectDateInterface;
    }

    public interface ChooseInterface {
        void selected(Object obj);
    }
}
