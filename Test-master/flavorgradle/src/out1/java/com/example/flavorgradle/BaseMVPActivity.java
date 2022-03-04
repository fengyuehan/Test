package com.example.flavorgradle;

import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public abstract class BaseMVPActivity extends AppCompatActivity
{
	public View getView()
	{
		TextView textView = new TextView(this);
		textView.setText("1111111111");
		return textView;
	}
}
