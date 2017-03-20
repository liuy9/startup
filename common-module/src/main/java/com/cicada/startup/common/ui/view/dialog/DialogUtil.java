package com.cicada.startup.common.ui.view.dialog;

import android.content.Context;
import android.view.View;

import java.util.ArrayList;
public class DialogUtil {

	/**
	 * 通用dialog  
	 * @param context
	 * @param title
	 * @param content
	 * @param left
	 * @param right
	 * @param listener
	 */
	public static void showConfigDialog(Context context,String title,String content,String left,String right,DialogView.AlertDlgBuilder.IDialogClick listener){
		showDialog(context, title, content, left, right, listener);
	}
	/**
	 * 自定义dialog  
	 * @param context
	 * @param view 自定义view
	 * @param listener 回调监听
	 * @param IDs  自定义控件需要点击操作的id
	 */
	public static void showCustomeDialog(Context context, View view, ArrayList<Integer> IDs,DialogView.AlertDlgBuilder.IDialogClick listener){
		showCustomDialog(context, view,IDs, listener);
	}
	
	
	private static void showDialog(Context context,String title,String content,
			String left,String right,DialogView.AlertDlgBuilder.IDialogClick listener){
		DialogView.AlertDlgBuilder builder = new DialogView.AlertDlgBuilder(context);
		builder.initView(title, content, left, right, listener,null);
		DialogView dialog =  builder.create();
		dialog.show();
	}
	
	private static void showCustomDialog(Context context, View view, ArrayList<Integer> IDs,DialogView.AlertDlgBuilder.IDialogClick listener){
		DialogView.AlertDlgBuilder builder = new DialogView.AlertDlgBuilder(context);
		builder.initView(null, null, null, null, listener,IDs);
		DialogView dialog = builder.create(view);
		dialog.show();
	}
	
}
