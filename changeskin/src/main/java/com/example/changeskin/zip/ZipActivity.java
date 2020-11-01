package com.example.changeskin.zip;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.Toolbar;

import com.example.changeskin.R;
import com.example.changeskin.activity.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import skin.support.SkinCompatManager;
import skin.support.utils.SkinPreference;

public class ZipActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.zip_src)
    ImageView zipSrc;
    @BindView(R.id.default_theme)
    Button defaultTheme;
    @BindView(R.id.default_zip_theme)
    Button defaultZipTheme;
    @BindView(R.id.zip_theme)
    Button zipTheme;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zip);
        ButterKnife.bind(this);
        initToolBar(true);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (SkinPreference.getInstance().getSkinStrategy() == ZipSDCardLoader.SKIN_LOADER_STRATEGY_ZIP){
            getToolbar().setTitle("图片来自Zip包");
        }else {
            getToolbar().setTitle("图片来自应用本身");
        }
    }

    @OnClick({R.id.toolbar, R.id.default_theme, R.id.default_zip_theme, R.id.zip_theme})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.toolbar:
                finish();
                break;
            case R.id.default_theme:
                SkinCompatManager.getInstance().restoreDefaultTheme();
                getToolbar().setTitle("图片来自应用本身");
                break;
            case R.id.default_zip_theme:
                SkinCompatManager.getInstance().loadSkin("", ZipSDCardLoader.SKIN_LOADER_STRATEGY_ZIP);
                getToolbar().setTitle("图片来自Zip包");
                break;
            case R.id.zip_theme:
                /**
                 * night.skin为assets里面的一个文件
                 * 这个文件需要先创建一个module，然后打包成apk,然后将apk的后缀改成.skin即可
                 */
                SkinCompatManager.getInstance().loadSkin("night.skin", ZipSDCardLoader.SKIN_LOADER_STRATEGY_ZIP);
                getToolbar().setTitle("图片来自Zip包");
                break;
        }
    }
}
