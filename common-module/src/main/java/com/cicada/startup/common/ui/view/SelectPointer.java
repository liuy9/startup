package com.cicada.startup.common.ui.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.cicada.startup.common.R;

import java.util.ArrayList;

/**
 * @author houwenpeng
 * @version V1.0
 * @Package zla-cicada2
 * @Title com.cicada.daydaybb.common.ui.view
 * @date 16/7/23
 * @Description:
 */
public class SelectPointer {

    private ArrayList<ImageView> poinerList = new ArrayList<>();
    private Context mContext ;
    private LayoutInflater mInfalter;
    private Bitmap selectbitmap;
    private Bitmap unselectbitmap;
    private LinearLayout mRootView;
    private int pointSum = 1;

    public SelectPointer(Context c, int pointSum,int defaultpos) {
        this.mContext = c;
        this.pointSum = pointSum;
        mInfalter=(LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mRootView = (LinearLayout) mInfalter.inflate(R.layout.select_pointer_layout, null);
        selectbitmap = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.select_oval);
        unselectbitmap = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.select_no_oval);
        for(int i=0;i<pointSum;++i){
            ImageView img = new ImageView(mContext);
            poinerList.add(img);
            img.setPadding(0, 0,10, img.getMeasuredHeight());
            mRootView.addView(img);
        }
        poinerList.get(defaultpos).setImageBitmap(selectbitmap);
        this.setDefaultWithOut(defaultpos);

    }
    private void setDefaultWithOut(int index){
        for(int i = 0; i < poinerList.size();++i){
            if(index == i){
                continue;
            }
            poinerList.get(i).setImageBitmap(unselectbitmap);
        }
    }
    public void movetoIndex(int index){
        this.setDefaultWithOut(index);
        poinerList.get(index).setImageBitmap(selectbitmap);
    }

    public LinearLayout getPointView(){
        return mRootView;
    }

    public int getPointSum(){
        return pointSum;
    }
}
