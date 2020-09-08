package com.example.animation;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.animation.Interpolator.DecelerateAccelerateInterpolator;
import com.example.animation.ObjectAnimator.ColorEvaluator;
import com.example.animation.ObjectAnimator.MyView2;

public class MainActivity extends AppCompatActivity {
    //setContentView与addContentView的区别

    /**
     * setContentView（）
     * ViewGroup contentParent = mSubDecor.findViewById(android.R.id.content);
     * contentParent.removeAllViews();
     * contentParent.addView(v, lp);
     *  setContentView会先removeAllViews之后然后再添加view,这样就没有叠加效果
     *
     *  addContentView
     *  ViewGroup contentParent = mSubDecor.findViewById(android.R.id.content);
     *  contentParent.addView(v, lp);
     *
     * addContentView则直接再后面添加
     */

    private TextView mTextView;
    private MyView2 myView2;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTextView = findViewById(R.id.tv);
        myView2 = findViewById(R.id.my_view);
        button = findViewById(R.id.btn);

        float curTranslationX = mTextView.getTranslationX();
        ObjectAnimator animator = ObjectAnimator.ofFloat(mTextView,"translationX",curTranslationX,300,curTranslationX);
        animator.setDuration(2000);
        animator.setInterpolator(new DecelerateAccelerateInterpolator());
        animator.start();

        /**
         * color这个是对应MyView里面的成员变量
         * 步骤1：根据颜色估值器不断 改变 值
         * 步骤2：调用set（）设置背景颜色的属性值（实际上是通过画笔进行颜色设置）
         * 步骤3：调用invalidate()刷新视图，即调用onDraw（）重新绘制，从而实现动画效果
         */
        ObjectAnimator animator1 = ObjectAnimator.ofObject(myView2,"color",new ColorEvaluator(),"#0000FF", "#FF0000");
        animator1.setDuration(2000);
        animator1.setRepeatCount(ValueAnimator.INFINITE);
        animator1.setRepeatMode(ValueAnimator.REVERSE);
        animator1.start();

        buttonAnimator();
    }

    private void buttonAnimator() {
        final ViewWrapper viewWrapper = new ViewWrapper(button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ObjectAnimator.ofInt(viewWrapper,"width",500,1000,800).setDuration(2000).start();
            }
        });
    }

    private static class ViewWrapper{
        private View mTarget;

        public ViewWrapper(View mTarget) {
            this.mTarget = mTarget;
        }

        public  int getWidth(){
            return mTarget.getLayoutParams().width;
        }

        public void setWidth(int width){
            mTarget.getLayoutParams().width = width;
            mTarget.requestLayout();
        }
    }
}
