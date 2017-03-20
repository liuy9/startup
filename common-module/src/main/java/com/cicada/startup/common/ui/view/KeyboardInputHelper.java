package com.cicada.startup.common.ui.view;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.cicada.startup.common.R;
import com.cicada.startup.common.utils.UiHelper;

/**
 * 处理键盘输入
 * <p>
 * 创建时间: 16/7/23 上午11:16 <br/>
 *
 * @author zhaohaiyang
 * @since v0.0.1
 */
public class KeyboardInputHelper {

    private Context context;
    private EditText inputEdit;

    private View rootView;

    private OnSubmitListener submitListener;


    public KeyboardInputHelper(final Context context, CoordinatorLayout parentView) {
        this.context = context;
        this.rootView = LayoutInflater.from(context).inflate(R.layout.view_keybord_input, parentView, false);
        inputEdit = (EditText) rootView.findViewById(R.id.et_input);
        parentView.addView(rootView);
        rootView.setVisibility(View.GONE);
        inputEdit.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                if (actionId == EditorInfo.IME_ACTION_SEND) {
                    if (null != submitListener) {
                        submitListener.submit(v.getText().toString());
                        return true;
                    }
                }
                return false;
            }
        });

        inputEdit.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    UiHelper.showSoftKeyboard(inputEdit);
                } else {
                    UiHelper.hideSoftKeyboard(inputEdit);
                }
            }
        });
    }

    public KeyboardInputHelper addOnSubmitListener(OnSubmitListener submitListener) {
        this.submitListener = submitListener;
        return this;
    }

    public KeyboardInputHelper show() {
        rootView.setVisibility(View.VISIBLE);
        return this;
    }

    public KeyboardInputHelper dismiss() {
        rootView.setVisibility(View.GONE);
        return this;
    }

    public interface OnSubmitListener {
        void submit(String content);
    }

}
