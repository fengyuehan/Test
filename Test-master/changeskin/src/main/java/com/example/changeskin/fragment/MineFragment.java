package com.example.changeskin.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.changeskin.R;
import com.example.changeskin.WidgetListItem;
import com.example.changeskin.util.Constants;

import butterknife.BindView;
import butterknife.OnClick;
import skin.support.SkinCompatManager;
import skin.support.utils.SkinPreference;


public class MineFragment extends BaseFragment {


    @BindView(R.id.rb_skin_default)
    RadioButton rbSkinDefault;
    @BindView(R.id.rb_skin_yellow)
    RadioButton rbSkinYellow;
    @BindView(R.id.rb_skin_red)
    RadioButton rbSkinRed;
    @BindView(R.id.rb_skin_pink)
    RadioButton rbSkinPink;
    @BindView(R.id.rb_skin_black)
    RadioButton rbSkinBlack;
    @BindView(R.id.rb_skin_white)
    RadioButton rbSkinWhite;
    @BindView(R.id.rb_skin_blue)
    RadioButton rbSkinBlue;
    @BindView(R.id.rb_skin_green)
    RadioButton rbSkinGreen;
    @BindView(R.id.rb_skin_orange)
    RadioButton rbSkinOrange;
    @BindView(R.id.rg_skin)
    RadioGroup rgSkin;
    @BindView(R.id.wli_color_picker)
    WidgetListItem wliColorPicker;
    @BindView(R.id.wli_about)
    WidgetListItem wliAbout;


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_mine;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rgSkin.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.rb_skin_default:
                        SkinCompatManager.getInstance().restoreDefaultTheme();
                        break;
                    case R.id.rb_skin_yellow:
                        SkinCompatManager.getInstance()
                                .loadSkin(Constants.SKIN_NAME.YELLOW, SkinCompatManager.SKIN_LOADER_STRATEGY_BUILD_IN);
                        break;
                    case R.id.rb_skin_red:
                        SkinCompatManager.getInstance()
                                .loadSkin(Constants.SKIN_NAME.RED, SkinCompatManager.SKIN_LOADER_STRATEGY_BUILD_IN);
                        break;
                    case R.id.rb_skin_pink:
                        SkinCompatManager.getInstance()
                                .loadSkin(Constants.SKIN_NAME.PINK, SkinCompatManager.SKIN_LOADER_STRATEGY_BUILD_IN);
                        break;
                    case R.id.rb_skin_black:
                        SkinCompatManager.getInstance()
                                .loadSkin(Constants.SKIN_NAME.BLACK, SkinCompatManager.SKIN_LOADER_STRATEGY_BUILD_IN);
                        break;
                    case R.id.rb_skin_white:
                        SkinCompatManager.getInstance()
                                .loadSkin(Constants.SKIN_NAME.WHITE, SkinCompatManager.SKIN_LOADER_STRATEGY_BUILD_IN);
                        break;
                    case R.id.rb_skin_blue:
                        SkinCompatManager.getInstance()
                                .loadSkin(Constants.SKIN_NAME.BLUE, SkinCompatManager.SKIN_LOADER_STRATEGY_BUILD_IN);
                        break;
                    case R.id.rb_skin_green:
                        SkinCompatManager.getInstance()
                                .loadSkin(Constants.SKIN_NAME.GREEN, SkinCompatManager.SKIN_LOADER_STRATEGY_BUILD_IN);
                        break;
                    case R.id.rb_skin_orange:
                        SkinCompatManager.getInstance()
                                .loadSkin(Constants.SKIN_NAME.ORANGE, SkinCompatManager.SKIN_LOADER_STRATEGY_BUILD_IN);
                        break;
                }
            }
        });
        initRg();
    }

    private void initRg() {
        String skinName = SkinPreference.getInstance().getSkinName();
        Log.e("zzf",skinName);
        int checkedId = R.id.rb_skin_default;
        if (!TextUtils.isEmpty(skinName)) {
            switch (skinName) {
                case Constants.SKIN_NAME.BLACK:
                    checkedId = R.id.rb_skin_black;
                    break;
                case Constants.SKIN_NAME.BLUE:
                    checkedId = R.id.rb_skin_blue;
                    break;
                case Constants.SKIN_NAME.GREEN:
                    checkedId = R.id.rb_skin_green;
                    break;
                case Constants.SKIN_NAME.ORANGE:
                    checkedId = R.id.rb_skin_orange;
                    break;
                case Constants.SKIN_NAME.PINK:
                    checkedId = R.id.rb_skin_pink;
                    break;
                case Constants.SKIN_NAME.RED:
                    checkedId = R.id.rb_skin_red;
                    break;
                case Constants.SKIN_NAME.WHITE:
                    checkedId = R.id.rb_skin_white;
                    break;
                case Constants.SKIN_NAME.YELLOW:
                    checkedId = R.id.rb_skin_yellow;
                    break;
            }
        }
        rgSkin.check(checkedId);
    }
}
