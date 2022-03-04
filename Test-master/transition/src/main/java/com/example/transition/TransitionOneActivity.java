package com.example.transition;

import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.transition.ChangeBounds;
import android.transition.ChangeClipBounds;
import android.transition.ChangeScroll;
import android.transition.ChangeTransform;
import android.transition.Explode;
import android.transition.Fade;
import android.transition.Slide;
import android.transition.TransitionManager;
import android.transition.TransitionSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.AnticipateOvershootInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.Button;
import android.widget.FrameLayout;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TransitionOneActivity extends AppCompatActivity {

    @BindView(R.id.view_1)
    FrameLayout view1;
    @BindView(R.id.view_2)
    FrameLayout view2;
    @BindView(R.id.view_3)
    FrameLayout view3;
    @BindView(R.id.view_4)
    FrameLayout view4;
    @BindView(R.id.view_one)
    ConstraintLayout viewOne;

    private View[] views;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transition_one);
        ButterKnife.bind(this);
        views = new View[]{view1,view2,view3,view4};
    }



    @RequiresApi(api = Build.VERSION_CODES.M)
    @OnClick({R.id.bt_one_transition1, R.id.bt_one_transition2, R.id.bt_one_transition3, R.id.bt_one_transition4, R.id.bt_one_transition5, R.id.bt_one_transition6, R.id.bt_one_transition7, R.id.bt_one_transition8, R.id.bt_one_transition9})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_one_transition1:
                TransitionManager.beginDelayedTransition(viewOne);
                TransitionUtils.toggleViewVisible(view1);
                break;
            case R.id.bt_one_transition2:
                /**
                 * 向下离开屏幕出场，向上进入屏幕入场
                 */
                Slide slide = new Slide();
                slide.setInterpolator(new AccelerateDecelerateInterpolator());
                TransitionManager.beginDelayedTransition(viewOne,slide);
                TransitionUtils.toggleViewVisible(view2);
                break;
            case R.id.bt_one_transition3:
                /**
                 * 淡出 出场，淡入 入场
                 */
                Fade fade = new Fade();
                TransitionManager.beginDelayedTransition(viewOne,fade);
                TransitionUtils.toggleViewVisible(view3);
                break;
            case R.id.bt_one_transition4:
                /**
                 * 四边散开出场，四边汇入入场
                 */
                Explode explode = new Explode();
                TransitionManager.beginDelayedTransition(viewOne,explode);
                TransitionUtils.toggleViewVisible(view4);
                break;
            case R.id.bt_one_transition5:
                Fade fade1 = new Fade();
                Slide slide1 = new Slide();
                TransitionSet set = new TransitionSet();
                set.addTransition(fade1)
                        .addTransition(slide1)
                        .setOrdering(TransitionSet.ORDERING_TOGETHER);
                TransitionManager.beginDelayedTransition(viewOne,set);
                TransitionUtils.toggleViewVisible(view);
                break;
            case R.id.bt_one_transition6:
                ChangeBounds changeBounds = new ChangeBounds();
                changeBounds.setInterpolator(new AnticipateInterpolator());
                TransitionManager.beginDelayedTransition(viewOne,changeBounds);
                ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) view3.getLayoutParams();
                if (layoutParams.leftMargin == 400){
                    layoutParams.leftMargin = 10;
                }else {
                    layoutParams.leftMargin = 200;
                }
                view3.setLayoutParams(layoutParams);
                break;
            case R.id.bt_one_transition7:
                ChangeClipBounds changeClipBounds = new ChangeClipBounds();
                changeClipBounds.setInterpolator(new BounceInterpolator());
                TransitionManager.beginDelayedTransition(viewOne,changeClipBounds);
                int width = view4.getWidth();
                int height = view4.getHeight();
                int gap = 140;
                Rect rect = new Rect(0,gap,width,height-gap);
                if (rect.equals(view4.getClipBounds())){
                    view4.setClipBounds(null);
                }else {
                    view4.setClipBounds(rect);
                }
                break;
            case R.id.bt_one_transition8:
                ChangeScroll changeScroll = new ChangeScroll();
                changeScroll.setInterpolator(new AnticipateOvershootInterpolator());
                TransitionManager.beginDelayedTransition(viewOne,changeScroll);
                if (view1.getScaleX() == -100 && view1.getScaleY() == -100){
                    view1.scrollTo(0,0);
                }else {
                    view1.scrollTo(-100,-100);
                }
                break;
            case R.id.bt_one_transition9:
                ChangeTransform transition = new ChangeTransform();
                transition.setInterpolator(new OvershootInterpolator());
                TransitionManager.beginDelayedTransition(viewOne, transition);
                if (view1.getTranslationX() == 100 && view1.getTranslationY() == 100) {
                    view1.setTranslationX(0);
                    view1.setTranslationY(0);
                } else {
                    view1.setTranslationX(100);
                    view1.setTranslationY(100);
                }

                if (view2.getRotationX() == 30f) {
                    view2.setRotationX(0);
                } else {
                    view2.setRotationX(30);
                }

                if (view3.getRotationY() == 30f) {
                    view3.setRotationY(0);
                } else {
                    view3.setRotationY(30);
                }
                if (view4.getScaleX() == 0.5f && view4.getScaleY() == 0.5f) {
                    view4.setScaleX(1f);
                    view4.setScaleY(1f);
                } else {
                    view4.setScaleX(0.5f);
                    view4.setScaleY(0.5f);
                }
                break;
        }
    }
}
