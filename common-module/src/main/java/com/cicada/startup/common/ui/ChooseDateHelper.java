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
import com.cicada.startup.common.ui.view.wheelview.WheelViewDate;
import com.cicada.startup.common.utils.DateUtils;
import com.cicada.startup.common.utils.LogUtils;
import com.cicada.startup.common.utils.ToastUtils;

import java.util.Calendar;
import java.util.Date;

/**
 * 选择日期
 * <p>
 * Create time: 16/7/12 9:53
 *
 * @author liuyun.
 */
public class ChooseDateHelper {

    private PopupWindow dateSelectorPopupWindow;
    private View mParent;
    private SelectDateInterface selectDateInterface;
    private Context mContext;

    public static final String YEAR = "year";
    public static final String MONTH = "month";
    public static final String DAY = "day";
    private WheelView mYearWheelView;
    private WheelView mMonthWheelView;
    private WheelView mDayWheelView;


    private static final int DEFAULT_YEAR = 1970;
    private int year;
    private int month;
    private int day;
    private Calendar calendar = Calendar.getInstance();

    public ChooseDateHelper(Context context) {
        mContext = context;
    }

    /**
     * 初始化日期选择器
     *
     * @param parentView 显示日期选择器的view
     * @param year       当前显示的日期-year
     * @param month      当前显示的日期-moth
     * @param day        当前显示的日期-day
     * @author liuyun
     * @since v0.1
     */
    public void initDateSelector(View parentView, int year, int month, int day) {
        mParent = parentView;
        calendar.setTime(new Date());
        this.year = year;
        this.month = month;
        this.day = day;
        if (year <= 0) {
            this.year = calendar.get(Calendar.YEAR);
        }
        if (month <= 0) {
            this.month = calendar.get(Calendar.MONTH);
        }
        if (day <= 0) {
            this.day = calendar.get(Calendar.DAY_OF_MONTH);
        }
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

        mYearWheelView = (WheelView) popupView.findViewById(R.id.wheelviewYear);
        mMonthWheelView = (WheelView) popupView.findViewById(R.id.wheelviewMonth);
        mDayWheelView = (WheelView) popupView.findViewById(R.id.wheelviewDay);
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

                String dateStr = year + "-" + (month + 1) + "-" + (day + 1);
                if (validDate(dateStr)) {
                    if (selectDateInterface != null) {
                        selectDateInterface.selectedDate(year, month, day + 1);
                    }
                } else {
                    ToastUtils.showToastImage(mContext, mContext.getString(R.string.choose_date_future), 0);
                }
                dismissPopup();
            }
        });
    }

    private void initWheelView() {
        mYearWheelView.setIsShowCenterRect(false);
        mYearWheelView.setTextColor(mContext.getResources().getColor(R.color.wheelview_color_black), mContext.getResources().getColor(R.color.wheelview_color_gray));
        mYearWheelView.addChangingListener(new MyWheelViewOnChangingListener());

        mMonthWheelView.setIsShowCenterRect(false);
        mMonthWheelView.setTextColor(mContext.getResources().getColor(R.color.wheelview_color_black), mContext.getResources().getColor(R.color.wheelview_color_gray));
        mMonthWheelView.addChangingListener(new MyWheelViewOnChangingListener());

        mDayWheelView.setIsShowCenterRect(false);
        mDayWheelView.setTextColor(mContext.getResources().getColor(R.color.wheelview_color_black), mContext.getResources().getColor(R.color.wheelview_color_gray));
        mDayWheelView.addChangingListener(new MyWheelViewOnChangingListener());

        setCurWheelView();
        mMonthWheelView.setVisibleItems(5);
        mDayWheelView.setVisibleItems(5);
        mYearWheelView.setVisibleItems(5);
    }

    /**
     * 设置当前时间的滚轮
     */
    private void setCurWheelView() {
        new WheelViewDate(mContext, mYearWheelView, mMonthWheelView, mDayWheelView, DEFAULT_YEAR, calendar.get(Calendar.YEAR), year, month, day, true, false);
    }

    /**
     * 滚轮滑动监听器
     */
    private class MyWheelViewOnChangingListener implements OnWheelChangedListener {

        @Override
        public void onChanged(WheelView wheel, int oldValue, int newValue) {
            if (R.id.wheelviewYear == wheel.getId()) {
                year = newValue+DEFAULT_YEAR;
            } else if (R.id.wheelviewMonth == wheel.getId()) {
                month = newValue;
            } else if (R.id.wheelviewDay == wheel.getId()) {
                day = newValue;
            }
            LogUtils.d("hwp","year="+year+"  mouth="+month+" day="+day);
        }
    }

    /**
     * 校验选择的日期
     */
    private boolean validDate(String dateStr) {
        boolean isValid = false;
        Date date = DateUtils.getStringToDate_YYYY_MM_DD_EN(dateStr);
        Date nowDate = DateUtils.getDateNow();
        if (DateUtils.getDateDifferenceDays(date, nowDate) > 0) {
            isValid = false;
        } else {
            isValid = true;
        }

        return isValid;
    }

    public void showDateSelector() {
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

    public void setSelectDateInterface(SelectDateInterface selectDateInterface) {
        this.selectDateInterface = selectDateInterface;
    }

    public interface SelectDateInterface {
        void selectedDate(int selectedYear, int selectedMonth, int selectedDay);
    }
}
