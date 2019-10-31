package com.example.slidingverification;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.mingle.widget.ShapeLoadingDialog;

import me.wangyuwei.slackloadingview.SlackLoadingView;

public class MainActivity extends AppCompatActivity {

    private Button button;
    private ShapeLoadingDialog mShapeLoadingDialog;
    private LoadingDialog mLoadingDialog;
    private SlackLoadingView slackLoadingView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = findViewById(R.id.btn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*if (mLoadingDialog == null){
                    mLoadingDialog = new LoadingDialog(MainActivity.this);
                }
                mLoadingDialog.show();*/
                slackLoadingView.reset();
            }
        });
        slackLoadingView = findViewById(R.id.slackLoadingView);
        slackLoadingView.setLineLength(0.01f);
        slackLoadingView.setDuration(0.2f);
        slackLoadingView.start();
    }

}
