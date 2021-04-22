package com.example.flavorgradle;

import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import talex.zsw.basecore.view.dialog.rxdialog.RxDialogSure;

/**
 * 作用：基于MVP架构的Activity基类
 * 作者：赵小白 email:vvtale@gmail.com  
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public abstract class BaseMVPActivity extends AppCompatActivity
{
	public View getView()
	{
		TextView textView = new TextView(this);
		textView.setText("3333333333");
		final RxDialogSure dialog = new RxDialogSure(this);
		dialog.setTitle("Test3");
		dialog.show();
		dialog.setSureListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});
		return textView;
	}
}
