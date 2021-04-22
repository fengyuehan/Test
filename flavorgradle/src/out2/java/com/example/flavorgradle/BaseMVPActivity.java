package com.example.flavorgradle;

import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public abstract class BaseMVPActivity extends AppCompatActivity
{
	public View getView()
	{
		ImageView imageView = new ImageView(this);
		imageView.setImageResource(R.mipmap.ic_launcher);
		return imageView;
	}
}