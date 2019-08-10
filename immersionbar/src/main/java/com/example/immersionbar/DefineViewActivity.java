package com.example.immersionbar;

import android.animation.ObjectAnimator;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.EditText;

/**
 * @author zzf
 * @date 2019/8/5/005
 * 描述：
 */
public class DefineViewActivity extends BaseActivity {
    private EditText mEditText;
    private UnderLineView mUnderLineView;
    private View view_left,view_right;

    @Override
    protected void initListener() {
        mEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!TextUtils.isEmpty(s)&&s != null){
                    ObjectAnimator mObjectAnimator = ObjectAnimator.ofFloat(mUnderLineView,"alpha",0.3f,0.5f,1f);
                    mObjectAnimator.setDuration(1000);
                    mObjectAnimator.start();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    protected void initView() {
        mEditText = findViewById(R.id.et);
        mUnderLineView = findViewById(R.id.ulv);
        view_left = findViewById(R.id.view_left);
        view_right = findViewById(R.id.view_right);
        view_left.setVisibility(View.GONE);
        view_right.setVisibility(View.GONE);

    }

    @Override
    protected int getLayout() {
        return R.layout.activity_define_view;
    }
}
