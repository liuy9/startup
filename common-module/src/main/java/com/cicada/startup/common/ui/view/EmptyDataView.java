package com.cicada.startup.common.ui.view;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cicada.startup.common.R;

/**
 * @author houwenpeng
 * @version V1.0
 * @Package zl-cicada
 * @Title com.cicada.daydaybaby.common.ui.view
 * @date 16/8/5
 * @Description:
 */
public class EmptyDataView extends RelativeLayout {

    private Context context;
    private ImageView emptyImg;
    private TextView emptyDes;
    private TextView emptyAction;


    private IEmptyListener listener;


    public EmptyDataView(Context context) {
        super(context);
        initView(context);
    }

    public EmptyDataView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public EmptyDataView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context){
        this.context = context;
        LinearLayout root = new LinearLayout(context);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setGravity(Gravity.CENTER);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
//        params.setLayoutDirection(Gravity.CENTER_HORIZONTAL);
        params.gravity = Gravity.CENTER_HORIZONTAL;
        emptyImg = new ImageView(context);
        emptyAction = new TextView(context);
        emptyDes = new TextView(context);

        emptyDes.setPadding(0,30,0,10);
        emptyAction.setTextColor(getResources().getColor(R.color.red_point_color));
        emptyDes.setTextColor(getResources().getColor(R.color.text_color_gray3));

        root.addView(emptyImg,params);
        root.addView(emptyDes,params);
        root.addView(emptyAction,params);

        RelativeLayout.LayoutParams p = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        addView(root,p);
    }

    /**
     *
     * @param imageId  图片资源
     * @param des   默认文案
     * @param action  行为动作
     * @param listener  执行相应的监听
     */
    public void setEmptyData(int imageId, String des, String action, final IEmptyListener listener){
        emptyImg.setImageResource(imageId);
        emptyDes.setText(TextUtils.isEmpty(des)?"":des);
        emptyAction.setText(TextUtils.isEmpty(action)?"":action);
        this.listener = listener;

        if(emptyAction.getText().toString().length()>0){
            emptyAction.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.doEmptyAction(view.getId(),null);
                }
            });
        }
    }


    public interface IEmptyListener{
        /**
         *
         * @param id  控件id
         * @param obj  行为对象
         */
        void doEmptyAction(int id,Object obj);
    }

}
