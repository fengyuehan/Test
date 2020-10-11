package com.example.transition;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.transition.TransitionManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TransitionTwoActivity extends AppCompatActivity {
    @BindView(R.id.tv_two_transition1)
    TextView tvTwoTransition1;
    @BindView(R.id.cl_two_transition1)
    ConstraintLayout clTwoTransition1;
    @BindView(R.id.tv_two_transition2)
    TextView tvTwoTransition2;
    @BindView(R.id.cl_two_transition2)
    ConstraintLayout clTwoTransition2;
    @BindView(R.id.view_two_transition3)
    TransitionView viewTwoTransition3;
    @BindView(R.id.cl_two_transition3)
    ConstraintLayout clTwoTransition3;
    @BindView(R.id.iv_two_transition4)
    ImageView ivTwoTransition4;
    @BindView(R.id.cl_two_transition4)
    ConstraintLayout clTwoTransition4;
    @BindView(R.id.root_transition_two)
    ConstraintLayout rootTransitionTwo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transition_two);
        ButterKnife.bind(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @OnClick({R.id.bt_two_transition1, R.id.bt_two_transition6, R.id.bt_two_transition7, R.id.bt_two_transition8, R.id.bt_two_transition2, R.id.bt_two_transition3, R.id.bt_two_transition4, R.id.bt_two_transition5})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_two_transition1:
                ChangeTextTransition changeTextTransition = new ChangeTextTransition();
                changeTextTransition.addTarget(tvTwoTransition1);
                TransitionManager.beginDelayedTransition(clTwoTransition1, changeTextTransition);
                if (TextUtils.equals(tvTwoTransition1.getText(), "Hello")) {
                    tvTwoTransition1.setText("World");
                } else {
                    tvTwoTransition1.setText("Hello");
                }
                break;
            case R.id.bt_two_transition6:
                ChangeTextTransition changeTextColorTransition = new ChangeTextTransition();
                changeTextColorTransition.addTarget(tvTwoTransition1);
                TransitionManager.beginDelayedTransition(clTwoTransition1, changeTextColorTransition);
                if (tvTwoTransition1.getCurrentTextColor() == TransitionUtils.getColor(8)) {
                    tvTwoTransition1.setTextColor(TransitionUtils.getColor(0));
                } else {
                    tvTwoTransition1.setTextColor(TransitionUtils.getColor(8));
                }
                break;
            case R.id.bt_two_transition7:
                ChangeTextTransition changeTextSizeTransition = new ChangeTextTransition();
                changeTextSizeTransition.addTarget(tvTwoTransition1);
                TransitionManager.beginDelayedTransition(clTwoTransition1, changeTextSizeTransition);
                int textSize = getResources().getDimensionPixelSize(R.dimen.text_size);
                if (tvTwoTransition1.getTextSize() == textSize) {
                    tvTwoTransition1.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize / 3);
                } else {
                    tvTwoTransition1.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
                }
                break;
            case R.id.bt_two_transition8:
                ChangeTextTransition changeTextTypefaceTransition = new ChangeTextTransition();
                changeTextTypefaceTransition.addTarget(tvTwoTransition1);
                TransitionManager.beginDelayedTransition(clTwoTransition1, changeTextTypefaceTransition);
                int level = 1;
                Object tag = tvTwoTransition1.getTag(R.id.type_face_level);
                if (tag instanceof Integer) {
                    level = (int) tag;
                }
                if (level == 1) {
                    tvTwoTransition1.setTag(R.id.type_face_level, 5);
                } else {
                    tvTwoTransition1.setTag(R.id.type_face_level, 1);
                }
                break;
            case R.id.bt_two_transition2:
                ChangeBackgroundAlphaTransition changeBackgroundAlphaTransition = new ChangeBackgroundAlphaTransition();
                TransitionManager.beginDelayedTransition(clTwoTransition2, changeBackgroundAlphaTransition);
                int color1 = Color.parseColor("#FF7043");
                int color2 = Color.parseColor("#AB47BC");
                if (((ColorDrawable) clTwoTransition2.getBackground()).getColor() == color1) {
                    clTwoTransition2.setBackground(new ColorDrawable(color2));
                } else {
                    clTwoTransition2.setBackground(new ColorDrawable(color1));
                }
                break;
            case R.id.bt_two_transition3:
                ChangeBackgroundColorTransition changeBackgroundColorTransition = new ChangeBackgroundColorTransition();
                TransitionManager.beginDelayedTransition(clTwoTransition2, changeBackgroundColorTransition);
                int color3 = Color.parseColor("#FF7043");
                int color4 = Color.parseColor("#AB47BC");
                if (((ColorDrawable) clTwoTransition2.getBackground()).getColor() == color3) {
                    clTwoTransition2.setBackground(new ColorDrawable(color4));
                } else {
                    clTwoTransition2.setBackground(new ColorDrawable(color3));
                }
                break;
            case R.id.bt_two_transition4:
                ChangeImageResourceTransition changeImageResourceTransition = new ChangeImageResourceTransition();
                changeImageResourceTransition.addTarget(ivTwoTransition4);
                TransitionManager.beginDelayedTransition(clTwoTransition4, changeImageResourceTransition);
                String tag1 = (String) ivTwoTransition4.getTag();
                if (TextUtils.equals(tag1, "girl")) {
                    ivTwoTransition4.setImageResource(R.drawable.boy);
                    ivTwoTransition4.setTag("boy");
                } else {
                    ivTwoTransition4.setImageResource(R.drawable.girl);
                    ivTwoTransition4.setTag("girl");
                }
                break;
            case R.id.bt_two_transition5:
                ChangeCustomTransition changeCustomTransition = new ChangeCustomTransition();
                changeCustomTransition.addTarget(viewTwoTransition3);
                TransitionManager.beginDelayedTransition(clTwoTransition3, changeCustomTransition);
                if (viewTwoTransition3.getRatio() == 0f) {
                    viewTwoTransition3.setRatio(1f);
                } else {
                    viewTwoTransition3.setRatio(0f);
                }
                break;
        }
    }
}
