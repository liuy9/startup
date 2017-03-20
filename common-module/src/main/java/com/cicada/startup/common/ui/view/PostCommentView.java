package com.cicada.startup.common.ui.view;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.cicada.startup.common.R;

/**
 * 发表评论组件
 * <p>
 * 创建时间: 16/8/2 上午10:03 <br/>
 *
 * @author zhaohaiyang
 * @since v0.0.1
 */
public class PostCommentView {


    private Context context;
    private View commentView;
    private PopupWindow popupWindow;

    private TextView cancelView;
    private Button okBtn;
    private EditText contentEdit;
    private LinearLayout linearLayoutBg;

    private OnSendListener sendListener;

    public void addSendListener(OnSendListener sendListener) {
        this.sendListener = sendListener;
    }

    public PostCommentView(Context context) {
        this.context = context;
        initView();
    }

    private void initView() {
        commentView = View.inflate(context, R.layout.view_post_comment, null);


        // 实例化PopupWindow
        if (popupWindow == null) {
            popupWindow = new PopupWindow(commentView, android.view.ViewGroup.LayoutParams.MATCH_PARENT,
                    android.view.ViewGroup.LayoutParams.MATCH_PARENT, true);
        }
        // 这两个属性设置点击popup以外区域隐藏
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setOutsideTouchable(true);
        popupWindow.setFocusable(true);
        popupWindow.setAnimationStyle(R.style.popup_animation);
        popupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        cancelView = (TextView) commentView.findViewById(R.id.tv_cancel);
        okBtn = (Button) commentView.findViewById(R.id.btn_send);
        contentEdit = (EditText) commentView.findViewById(R.id.et_content);
        commentView.findViewById(R.id.linearLayoutCancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismissPopup();
            }
        });

        cancelView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismissPopup();
            }
        });

        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSend();
            }
        });

        contentEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkStatus();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        checkStatus();
    }

    private void checkStatus() {
        if (TextUtils.isEmpty(contentEdit.getText().toString())) {
            okBtn.setEnabled(false);
            okBtn.setAlpha(0.5f);
        } else {
            okBtn.setEnabled(true);
            okBtn.setAlpha(1f);
        }
    }

    private void onSend() {

        if (null != sendListener) {
            String comment = contentEdit.getText().toString().trim();
            if (!TextUtils.isEmpty(comment)) {
                sendListener.doSend(comment);
            }

        }
        dismissPopup();
    }


    public void show(View parent) {
        if (!popupWindow.isShowing()) {
            contentEdit.requestFocus();
            popupWindow.getContentView().measure(0, 0);
            popupWindow.showAtLocation(parent, Gravity.CENTER, 0, 0);
            popupWindow.update();

            openKeyboard(new Handler(), 100);
        }
    }

    private void dismissPopup() {
        InputMethodManager inputMgr = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMgr.toggleSoftInput(InputMethodManager.HIDE_NOT_ALWAYS, 0);// 输入法软键盘打开时关闭,关闭时打开
        popupWindow.setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        popupWindow.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
        if (popupWindow != null) {
            popupWindow.dismiss();
        }
    }

    public interface OnSendListener {

        void doSend(String content);
    }

    private void openKeyboard(Handler mHandler, int s) {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }, s);
    }

}
