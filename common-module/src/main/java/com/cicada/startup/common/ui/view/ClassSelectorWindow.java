package com.cicada.startup.common.ui.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.cicada.startup.common.R;
import com.cicada.startup.common.utils.DeviceUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 班级选择下拉框
 * <p/>
 * 创建时间: 15/9/17 下午3:20 <br/>
 *
 * @author zhaohaiyang
 * @since v0.0.1
 */
public class ClassSelectorWindow extends PopupWindow implements AdapterView.OnItemClickListener {

    private Context mContext;
    private ListView listView;
    private List<String> classList = new ArrayList<String>();

    private OnClassSelectedListener onClassSelectedListener;

    public void setOnClassSelectedListener(OnClassSelectedListener onClassSelectedListener) {
        this.onClassSelectedListener = onClassSelectedListener;
    }

    public ClassSelectorWindow(Activity mContext, List<String> classList) {
        this.mContext = mContext;
        this.classList = classList;
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View conentView = inflater.inflate(R.layout.popup_class_select, null);
        listView = (ListView) conentView.findViewById(R.id.lv_class_select);
        listView.setAdapter(new ArrayAdapter(mContext, R.layout.list_item_simple_text, R.id.tv_list_view_item, classList));
        int w = mContext.getWindowManager().getDefaultDisplay().getWidth();
        // 设置SelectPicPopupWindow的View
        this.setContentView(conentView);
        // 设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(w / 2);

        // 设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        if (classList.size() > 8) {
            this.setHeight(DeviceUtils.dip2Px(mContext, 300));
        }
        // 设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        this.setOutsideTouchable(true);
        // 刷新状态
        this.update();
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0000000000);
        // 点back键和其他地方使其消失,设置了这个才能触发OnDismisslistener ，设置其他控件变化等操作
        this.setBackgroundDrawable(dw);

        listView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        this.dismiss();
        if (null != onClassSelectedListener) {
            onClassSelectedListener.onClassSelect(position);
        }
    }

    public void show(View parent) {
        if (!this.isShowing()) {
            // 以下拉方式显示popupwindow
            showAsDropDown(parent, parent.getWidth() / 2 - this.getWidth() / 2, 0);
        } else {
            this.dismiss();
        }
    }

    public interface OnClassSelectedListener {
        void onClassSelect(int position);


    }
}
