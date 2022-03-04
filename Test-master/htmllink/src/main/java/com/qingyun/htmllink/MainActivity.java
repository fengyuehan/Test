package com.qingyun.htmllink;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.method.ScrollingMovementMethod;
import android.text.style.URLSpan;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    String str = "<p><span style=\"font-weight: bold;\">第一讲：区块链的基础知识&nbsp; &nbsp;</span>学习密码：1774<br></p><p><a href=\"https://v.youku.com/v_show/id_XNDc2MjE0NTc3Ng==.html\" target=\"_blank\">https://v.youku.com/v_show/id_XNDc2MjE0NTc3Ng==.html</a><br></p><p><span style=\"font-weight: bold;\">第二讲：行业名词解释&nbsp; &nbsp; &nbsp; &nbsp; &nbsp;</span>学习密码：1774<br></p><a href=\"https://v.youku.com/v_show/id_XNDc2MzU2MDU2OA==.html\" target=\"_blank\">https://v.youku.com/v_show/id_XNDc2MzU2MDU2OA==.html</a><br><p><span style=\"font-weight: bold;\">第三讲：区块链应用领域&nbsp; &nbsp; &nbsp;&nbsp;</span>学习密码：5967<br></p><p><a href=\"https://v.youku.com/v_show/id_XNDc2NTI0OTkyOA==.html\" target=\"_blank\">https://v.youku.com/v_show/id_XNDc2NTI0OTkyOA==.html</a><br></p><p><span style=\"font-weight: bold;\">第四讲：项目运营要点分析</span>&nbsp; &nbsp;学习密码：6008<br></p><p><a href=\"https://v.youku.com/v_show/id_XNDc2NTE2NzU4OA==.html\" target=\"_blank\">https://v.youku.com/v_show/id_XNDc2NTE2NzU4OA==.html</a><br></p>";

    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.tv);
        /**
         * 拦截链接的点击事件，不会直接用浏览器打开
         */
        /*Spanned spanned = Html.fromHtml(str);
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(spanned);
        URLSpan[] urls = spannableStringBuilder.getSpans(0, spanned.length(), URLSpan.class);
        for (URLSpan url : urls) {
            MyURLSpan myURLSpan = new MyURLSpan(url.getURL());
            int start = spannableStringBuilder.getSpanStart(url);
            int end = spannableStringBuilder.getSpanEnd(url);
            int flags = spannableStringBuilder.getSpanFlags(url);
            spannableStringBuilder.setSpan(myURLSpan, start, end, flags);
            //一定要加上这一句,看过很多网上的方法，都没加这一句，导致ClickableSpan的onClick方法没有回调，直接用浏览器打开了
            //spannableStringBuilder.removeSpan(url);
        }
        textView.setText(spannableStringBuilder);*/
        textView.setText(Html.fromHtml(str));
        textView.setMovementMethod(LinkMovementMethod.getInstance());
        /**
         * 如果当数字过多textview放不下的时候，需要滑动，不能采用下面的方法设置，该方法会导致
         * 点击链接无效，直接在xml文件中设置android:scrollbars="vertical"即可
         */
        //textView.setMovementMethod(ScrollingMovementMethod.getInstance());
    }
}
