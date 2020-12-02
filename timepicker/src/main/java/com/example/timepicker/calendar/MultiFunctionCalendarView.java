package com.example.timepicker.calendar;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.customdatelibrary.Constant;
import com.example.customdatelibrary.MethodUtil;
import com.example.customdatelibrary.adapter.PagerViewAdapter;
import com.example.customdatelibrary.bean.Customdatebean;
import com.example.timepicker.R;
import com.example.timepicker.calendar.adapter.DayAdapter;
import com.example.timepicker.calendar.adapter.MonthAdapter;
import com.example.timepicker.calendar.bean.CalendarBean;
import com.example.timepicker.calendar.listener.OnCalendarDoubleSelectListener;
import com.example.timepicker.calendar.listener.OnCalendarSingleSelectListener;
import com.example.timepicker.calendar.listener.OnDoubleSelectListener;
import com.example.timepicker.calendar.listener.OnMonthClickListener;
import com.example.timepicker.calendar.listener.OnSingleSelectListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * author : zhangzf
 * date   : 2020/11/30
 * desc   :
 */
public class MultiFunctionCalendarView extends LinearLayout implements View.OnClickListener {
    private Context mContext;
    private View view;
    /**
     * 年月日
     */
    private int year = -1;
    private int month = -1;
    private int day = -1;

    private List<Integer> mYear = new ArrayList<>();
    private List<String> mMonthList = new ArrayList<>();
    private List<List<String>> mDayList = new ArrayList<>();
    private Message message;

    private LayoutInflater mLayoutInflater;
    private int type = -1;

    private MonthAdapter mMonthAdapter;
    private List<DayAdapter> mDayAdapters = new ArrayList<>();
    private DayAdapter dayAdapter;
    /**
     * 保存前后bs年的数据
     */
    private int bs = 2;
    private int minyear = 0;
    private int maxyear = 3000;

    private ImageView iv_left;
    private TextView tv_year;
    private ImageView iv_right;
    private LinearLayout ll_year;
    private RecyclerView rv;
    private NoSlidingViewPager vp;

    private int nowpager = 0;

    //自定义日期数据源
    //默认里面只有今天的数据
    //数据类型为 Customdatebean
    private List<Customdatebean> customdates = new ArrayList<>();

    //日期下面备注下标信息
    private List<List<CalendarBean>> mCalendarBeanList = new ArrayList<>();

    private OnSingleSelectListener onSingleSelectListener;
    private OnDoubleSelectListener onDoubleSelectListener;

    public void setOnSingleSelectListener(OnSingleSelectListener onSingleSelectListener) {
        this.onSingleSelectListener = onSingleSelectListener;
    }

    public void setOnDoubleSelectListener(OnDoubleSelectListener onDoubleSelectListener) {
        this.onDoubleSelectListener = onDoubleSelectListener;
    }

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 001:
                    //拿到当前年份
                    //拿到倍数
                    //计算下个年份距离现在年份差
                    int y = Integer.parseInt(MethodUtil.getSystemTime().split("年")[0]);
                    int t = year-y;
                    int m = 12*t + bs/2 *12 +1;
                    nowpager = m;
                    mMonthAdapter.setIndex(0);
                    vp.setCurrentItem(nowpager-1);
                    tv_year.setText(String.valueOf(year));
                    break;
                case 002:
                    tv_year.setText(String.valueOf(year));
                    break;
            }
        }
    };

    public MultiFunctionCalendarView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.DateLayout);
        Constant.MONTH_XHX = a.getString(R.styleable.DateLayout_month_xhxcolor)==null?"#54CE9C":a.getString(R.styleable.DateLayout_month_xhxcolor);
        Constant.DAYITEM = a.getString(R.styleable.DateLayout_dayitemcolor)==null?"#54CE9C":a.getString(R.styleable.DateLayout_dayitemcolor);
        Constant.YEAR_FONTCOLOR = a.getString(R.styleable.DateLayout_year_fontcolor)==null?"#000000":a.getString(R.styleable.DateLayout_year_fontcolor);
        bs = a.getInteger(R.styleable.DateLayout_bs,2);
        Constant.YEAR_FONTSIZE = a.getInteger(R.styleable.DateLayout_year_fontsize,16);
        Constant.MOUTH_FONTSIZE = a.getInteger(R.styleable.DateLayout_month_fontsize,15);
        Constant.DAY_FONTSIZE = a.getInteger(R.styleable.DateLayout_day_fontsize,14);
        Constant.DAYNOTE_FONTSIZE = a.getInteger(R.styleable.DateLayout_daynote_fontsize,8);
        mYear.clear();
        mMonthList.clear();
        mDayList.clear();
        customdates.clear();
        mCalendarBeanList.clear();
        customdatesAdd(new Customdatebean(Integer.parseInt(MethodUtil.getSystemTime().split("年")[0])+ "年" +
                Integer.parseInt(MethodUtil.getSystemTime().split("年")[1].split("月")[0])+"月"+
                Integer.valueOf(MethodUtil.getSystemTime().split("月")[1].split("日")[0]),"今天"));
        initData();
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = mLayoutInflater.inflate(R.layout.calendar_view, this, true);
        initView(view);
        initmonth();
        initday();
    }

    private void initday() {
        mDayAdapters.clear();
        List<View> tlist = new ArrayList<>();
        for (int i = 0; i < 12*(bs+1); i++) {
            View dayview = mLayoutInflater.inflate(R.layout.fragment_month, null);
            final RecyclerView dayview_R = dayview.findViewById(R.id.fragment_rv);
            final TextView titleview_R = dayview.findViewById(R.id.fragment_title);
            int y = Integer.parseInt(MethodUtil.getSystemTime().split("年")[0]);
            int b = y - bs/2;
            titleview_R.setText(b+MethodUtil.getNowYear(i)+"年"+(i - 12*MethodUtil.getNowYear(i) +1)+"月");
            GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, 7);
            dayAdapter = new DayAdapter(mContext,mCalendarBeanList.get(i),mDayList.get(i), type);
            dayAdapter.setOnCalendarSingleSelectListener(new OnCalendarSingleSelectListener() {
                @Override
                public void onCalendarSingleSelect(String date) {
                    if (onSingleSelectListener != null){
                        onSingleSelectListener.onSingleSelect(date);
                    }
                }
            });
            dayAdapter.setOnCalendarDoubleSelectListener(new OnCalendarDoubleSelectListener() {
                @Override
                public void onCalendarDoubleSelect(String date1, String date2) {
                    if (onDoubleSelectListener != null){
                        onDoubleSelectListener.onDoubleSelect(date1,date2);
                    }
                }
            });
            mDayAdapters.add(dayAdapter);
            dayview_R.setLayoutManager(gridLayoutManager);
            dayview_R.setAdapter(dayAdapter);
            tlist.add(dayview);
        }
        initViewPager(tlist);
    }

    private void initViewPager(List<View> tlist) {
        PagerViewAdapter p = new PagerViewAdapter(tlist);
        vp.setAdapter(p);
        vp.setCurrentItem(nowpager-1);
        vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                if (mMonthAdapter.getIndex() == 11 && (i-(i/12)*12) == 0){
                    year ++;
                }
                if (mMonthAdapter.getIndex() == 0 && (i-(i/12)*12) == 11){
                    year --;
                }
                message = new Message();
                message.what = 002;
                handler.sendMessage(message);
                mMonthAdapter.setIndex(i-(i/12)*12);
                rv.scrollToPosition(i-(i/12)*12);
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }

    private void initmonth() {
        mMonthAdapter = new MonthAdapter(mMonthList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rv.setLayoutManager(linearLayoutManager);
        rv.setAdapter(mMonthAdapter);
        mMonthAdapter.setIndex(month - 1);
        mMonthAdapter.setOnMonthClickListener(new OnMonthClickListener() {
            @Override
            public void onClick(int position) {
                //计算基数
                int y = Integer.parseInt(MethodUtil.getSystemTime().split("年")[0]);//2020
                int b = y - bs/2;//2019
                int t = year - b;//1
                nowpager = position + t*12;
                vp.setCurrentItem(nowpager);
            }
        });
    }

    private void initView(View view) {
        iv_left = view.findViewById(R.id.iv_left);
        iv_right = view.findViewById(R.id.iv_right);
        tv_year = view.findViewById(R.id.tv_year);
        ll_year = view.findViewById(R.id.ll_year);
        rv = view.findViewById(R.id.rv);
        vp = view.findViewById(R.id.vp);
        ll_year.setVisibility(GONE);
        int w = MethodUtil.getScreenWidth(mContext);
        ViewGroup.LayoutParams params = vp.getLayoutParams();
        params.height = w / 7 * 6;
        vp.setLayoutParams(params);

        tv_year.setTextSize(Constant.YEAR_FONTSIZE);
        tv_year.setTextColor(Color.parseColor(Constant.YEAR_FONTCOLOR));

        iv_left.setOnClickListener(this);
        iv_right.setOnClickListener(this);
    }

    private void initData() {
        mMonthList.add("一月");
        mMonthList.add("二月");
        mMonthList.add("三月");
        mMonthList.add("四月");
        mMonthList.add("五月");
        mMonthList.add("六月");
        mMonthList.add("七月");
        mMonthList.add("八月");
        mMonthList.add("九月");
        mMonthList.add("十月");
        mMonthList.add("十一月");
        mMonthList.add("十二月");

        year = Integer.parseInt(MethodUtil.getSystemTime().split("年")[0]);
        month = Integer.parseInt(MethodUtil.getSystemTime().split("年")[1].split("月")[0]);
        day = Integer.valueOf(MethodUtil.getSystemTime().split("月")[1].split("日")[0]);

        nowpager = bs/2 *12 +1 + month - 1;
        minyear = year - bs/2;
        maxyear = year + bs/2;

        createMonthDay(year);
    }

    /**
     * 生成当前年份的十二个月信息
     * @param year
     */
    private void createMonthDay(int year) {
        int c = bs/2;
        int y = year - c;
        for (int m=0;m<bs+1;m++){
            for (int i = 1; i < 13; i++) {
                List<String> tl = new ArrayList<>();
                List<CalendarBean> calendarBeans = new ArrayList<>();
                tl.add("日");
                tl.add("一");
                tl.add("二");
                tl.add("三");
                tl.add("四");
                tl.add("五");
                tl.add("六");
                for (int s = 0;s<7;s++){
                    calendarBeans.add(new CalendarBean("",""));
                }
                int t = MethodUtil.getWeekdayOfMonth(y+m, i, 1);
                if (t != 7) {
                    for (int j = 0; j < t; j++) {
                        tl.add("");
                        calendarBeans.add(new CalendarBean("",""));
                    }
                }
                int monthOfDay = MethodUtil.getMonthOfDay(y+m, i);
                loadDay(tl,monthOfDay,y,m,i,calendarBeans);
                mYear.add(y + m);
                mDayList.add(tl);
                mCalendarBeanList.add(calendarBeans);
            }
        }
    }

    /**
     * 装载日期数据
     * @param tl
     * @param monthOfDay
     * @param y
     * @param m
     * @param i
     * @param calendarBeans
     */
    private void loadDay(List<String> tl, int monthOfDay, int y, int m, int i, List<CalendarBean> calendarBeans) {
        for (int n = 1; n <= monthOfDay; n++) {
            String s = y+m+"年"+i+"月"+String.valueOf(n);
            String s2 = y+m+"年"+(i<10?"0"+i:i)+"月"+String.valueOf(n<10?"0"+n:n);
            tl.add(s);
            calendarBeans.add(new CalendarBean(s,""));
        }
        for (int p = 0;p < customdates.size();p++){
            for (int u = 0;u < tl.size();u++){
                if (customdates.get(p).getOlddate().equals(tl.get(u))){
                    Collections.replaceAll(tl,tl.get(u),y+m+"年"+i+"月"+customdates.get(p).getNewdata());
                }
            }
        }
    }

    private void customdatesAdd(Customdatebean customdatebean){
        for (Customdatebean cb : customdates){
            if (cb.getOlddate().equals(customdatebean.getOlddate())){
                Collections.replaceAll(customdates,cb,customdatebean);
                return;
            }
        }
        customdates.add(customdatebean);
    }

    /**
     * 刷新数据
     */
    private void refreshData(){
        synchronized (this){
            initView(view);
            initmonth();
            initday();
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.iv_left){
            if (year <= minyear){
                return;
            }
            year--;
        }else if (v.getId() == R.id.iv_right){
            if (year >= maxyear){
                return;
            }
            year++;
        }
        message = new Message();
        message.what = 001;
        handler.sendMessage(message);
    }

    /**
     * 返回所有选中数据集合
     * @return
     */
    public List<String> getList(){
        List<String> res = new ArrayList<>();
        for (int i=0;i<mDayList.size();i++){
            for (String s : mDayAdapters.get(i).getList()){
                if (s.indexOf("今天") != -1){
                    s = MethodUtil.getSystemTime().split("日")[0];
                }
                res.add(s);
            }
        }
        return res;
    }

    /**
     * 设置类型
     * 1：表示单选
     * 2：表示连续多选
     * @param type
     */
    public void setType(int type) {
        this.type = type;
        vp.setNoScroll(false);
        refreshData();
    }

    /**
     * 重置数据
     */
    public void reset(){
        for (DayAdapter dayAdapter:mDayAdapters){
            dayAdapter.clearcolor();
            dayAdapter.notifyDataSetChanged();
        }
        refreshData();
    }

    /**
     * 在多选模式下，默认选中的代码，list只能是两条数据，一个头一个尾
     * @param list
     */
    public void setSelectDay(List<String> list){
        if (type == 2){
            if (list.size() != 2){
                return;
            }
        }
        int month = Integer.parseInt(list.get(0).split("年")[0]);
        int month1 = Integer.parseInt(list.get(1).split("年")[0]);
        /**
         * 表示不在同一个月份
         */
        if (month != month1){
            setType(1);
        }else {
            String str = tranfer(month);
            for (int i = 0;i < mMonthList.size();i++){
                if (str.equals(mMonthList.get(i))){
                    vp.setCurrentItem(i);
                    mMonthAdapter.setIndex(i-(i/12)*12);
                    rv.scrollToPosition(i-(i/12)*12);
                }
            }
        }
        for (int i = 0; i<mDayAdapters.size();i++){
            mDayAdapters.get(i).setDefaultData(list);
            mDayAdapters.get(i).notifyDataSetChanged();
        }
    }

    /**
     * 阿拉伯数字与中文的转换
     */
    private String tranfer(int num){
        String mStr;
        switch (num){
            case 1:
                mStr = "一月";
                return mStr;
            case 2:
                mStr = "二月";
                return mStr;
            case 3:
                mStr = "三月";
                return mStr;
            case 4:
                mStr = "四月";
                return mStr;
            case 5:
                mStr = "五月";
                return mStr;
            case 6:
                mStr = "六月";
                return mStr;
            case 7:
                mStr = "七月";
                return mStr;
            case 8:
                mStr = "八月";
                return mStr;
            case 9:
                mStr = "九月";
                return mStr;
            case 10:
                mStr = "十月";
                return mStr;
            case 11:
                mStr = "十一月";
                return mStr;
            case 12:
                mStr = "十二月";
                return mStr;
        }
        return "一月";
    }


}
