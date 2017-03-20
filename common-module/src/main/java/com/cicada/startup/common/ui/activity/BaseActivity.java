package com.cicada.startup.common.ui.activity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bugtags.library.Bugtags;
import com.cicada.startup.common.R;
import com.cicada.startup.common.cache.CacheManager;
import com.cicada.startup.common.manager.AppManager;
import com.cicada.startup.common.ui.view.LoadingDialog;
import com.cicada.startup.common.utils.LogUtils;
import com.cicada.startup.common.utils.StatusBarUtil;
import com.tendcloud.tenddata.TCAgent;

import java.io.IOException;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * <p/>
 * 创建时间: 16/6/28 下午2:48 <br/>å
 *
 * @author zhaohaiyang
 * @since v0.0.1
 */


public class BaseActivity extends AppCompatActivity implements IBaseView {

    private static final int BASE_LAYOUT_RES_ID = R.layout.activity_base;
    private static final LinearLayout.LayoutParams LAYOUT_PARAMS =
            new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);

    private LinearLayout parentView;
    private Toolbar toolbar;
    private TextView textViewTitle;
    private TextView textViewRightTitle;
    private ImageView rightRedPoint;
    private ImageView imageViewShare;
    private boolean isDestroy = false;
    private Unbinder unbinder;
    protected CacheManager cacheManager;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        this.isDestroy = false;
        super.onCreate(savedInstanceState);
        setContentView(BASE_LAYOUT_RES_ID);
        getWindow().setWindowAnimations(R.style.ActivityAnim);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//竖屏
        init();
    }

    protected String getTAG() {
        return this.getClass().getSimpleName();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Bugtags.onResume(this);
        TCAgent.onPageStart(this, this.getTAG());
    }

    @Override
    protected void onPause() {
        super.onPause();
        Bugtags.onPause(this);
        TCAgent.onPageEnd(this, this.getTAG());
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        //注：回调 3
        Bugtags.onDispatchTouchEvent(this, event);
        return super.dispatchTouchEvent(event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.isDestroy = true;
        if (null != unbinder) {
            unbinder.unbind();
        }
        AppManager.getInstance().finishActivity(this);
        dismissWaitDialog();
    }

    protected void goBack() {
        AppManager.getInstance().finishActivity(this);
    }

    private void init() {
        initToolBar();
        AppManager.getInstance().addActivity(this);

        try {
            this.cacheManager = new CacheManager(this);
        } catch (IOException e) {
            LogUtils.e(this.getClass().getSimpleName(), e.getMessage());
        }
    }


    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        if (BASE_LAYOUT_RES_ID == layoutResID) {
            super.setContentView(layoutResID);
            parentView = (LinearLayout) findViewById(R.id.base_parent_view);
            toolbar = (Toolbar) findViewById(R.id.toolbar_view);
            textViewTitle = (TextView) findViewById(R.id.tv_title);
            textViewRightTitle = (TextView) findViewById(R.id.tv_right_title);
            imageViewShare = (ImageView) findViewById(R.id.toobal_share);
            rightRedPoint = (ImageView) findViewById(R.id.right_red_point);
        } else {
            parentView.addView(getLayoutInflater().inflate(layoutResID, null), LAYOUT_PARAMS);
            unbinder = ButterKnife.bind(this);
        }
    }

    public ViewGroup getParentView(){
        return parentView;
    }

    private void initToolBar() {
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        if (null != getSupportActionBar()) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
        setStatusBar();
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goBack();
            }
        });
    }

    protected void setToolbarStyleWhite() {
        getToolbar().setNavigationIcon(R.drawable.button_back_red);
        getToolbar().setBackgroundColor(getResources().getColor(R.color.colorPrimary));
    }

    protected void setStatusBar() {
        setToolbarStyleWhite();
//        if (StatusBarUtil.SYSTEM_OTHERS == StatusBarUtil.StatusBarLightMode(this)) {
//            StatusBarUtil.setColor(this, getResources().getColor(R.color.colorPrimary), 200);
//        } else {
//            StatusBarUtil.setColor(this, getResources().getColor(R.color.colorPrimary));
//        }

        StatusBarUtil.setColor(this, getResources().getColor(R.color.colorPrimaryDark));
    }

    protected Toolbar getToolbar() {
        return this.toolbar;
    }

    public TextView getRightTitleView() {
        this.textViewRightTitle.setVisibility(View.VISIBLE);
        return this.textViewRightTitle;
    }

    public ImageView getRightRedPoint(){
        this.rightRedPoint.setVisibility(View.VISIBLE);
        return this.rightRedPoint;
    }


    public void setViewTitle(@StringRes int resId) {
        if (null != textViewTitle) {
            textViewTitle.setText(resId);
        }
    }

    public void setViewTitle(CharSequence title) {
        if (null != textViewTitle) {
            textViewTitle.setText(title);
        }
    }

    protected void setViewTitle(@StringRes int resId, int color) {
        if (null != textViewTitle) {
            textViewTitle.setText(resId);
            textViewTitle.setTextColor(color);
        }
    }

    public void setViewTitle(CharSequence title, int color) {
        if (null != textViewTitle) {
            textViewTitle.setText(title);
            textViewTitle.setTextColor(color);
        }
    }


    public void setToolbarBackageColor(int color) {
        if (toolbar != null) {
            toolbar.setBackgroundColor(color);
        }
    }

    public void setToolbarVisible(boolean isVisible) {
        toolbar.setVisibility(isVisible ? View.VISIBLE : View.GONE);
    }

    public void setShareVisiable(boolean isVisible){
        imageViewShare.setVisibility(isVisible ? View.VISIBLE : View.GONE);
    }

    public ImageView getShare(){
        return imageViewShare;
    }

    @Override
    public boolean isDestroy() {
        return false;
    }

    private LoadingDialog dialog;

    /**
     * 显示进度对话框
     *
     * @param canCancel
     */
    public void showWaitDialog(final boolean canCancel) {

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (null == dialog) {
                    dialog = new LoadingDialog.Builder(BaseActivity.this).setMessage(getString(R.string.dialog_title_waiting))
                            .setCanCancel(canCancel)
                            .setCanceledOnTouchOutside(false).create();
                } else {
                    dialog.show();
                }
            }
        });

    }


    @Override
    public void showWaitDialog() {
        showWaitDialog(true);
    }

    @Override
    public void dismissWaitDialog() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                LoadingDialog.dismiss(dialog);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        AppManager.getInstance().finishActivity();
    }

}
