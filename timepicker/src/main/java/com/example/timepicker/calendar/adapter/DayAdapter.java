package com.example.timepicker.calendar.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.customdatelibrary.Constant;
import com.example.customdatelibrary.MethodUtil;
import com.example.timepicker.R;
import com.example.timepicker.calendar.bean.CalendarBean;
import com.example.timepicker.calendar.listener.OnCalendarDoubleSelectListener;
import com.example.timepicker.calendar.listener.OnCalendarSingleSelectListener;

import java.util.ArrayList;
import java.util.List;

/**
 * author : zhangzf
 * date   : 2020/11/30
 * desc   :
 */
public class DayAdapter extends RecyclerView.Adapter<DayAdapter.DayViewHolder> {

    private Context mContext;
    private List<CalendarBean> mCalendarList;
    /**
     * 每个月的所有天数,包含星期一至星期日以及当月的天数
     */
    private List<String> mList;
    /**
     * 所有ViewHolder的集合
     */
    private List<DayViewHolder> mHolderList = new ArrayList<>();
    /**
     * 单选的回调
     */
    private OnCalendarSingleSelectListener mOnCalendarSingleSelectListener;
    /**
     * 多选的回调
     */
    private OnCalendarDoubleSelectListener mOnCalendarDoubleSelectListener;
    /**
     * 模式
     * 1：单选
     * 2：多选
     */
    private int type = 1;

    public void setOnCalendarSingleSelectListener(OnCalendarSingleSelectListener mOnCalendarSingleSelectListener) {
        this.mOnCalendarSingleSelectListener = mOnCalendarSingleSelectListener;
    }

    public void setOnCalendarDoubleSelectListener(OnCalendarDoubleSelectListener mOnCalendarDoubleSelectListener) {
        this.mOnCalendarDoubleSelectListener = mOnCalendarDoubleSelectListener;
    }

    public DayAdapter(Context mContext,List<CalendarBean> mCalendarList, List<String> mList, int type) {
        this.mContext = mContext;
        this.mCalendarList = mCalendarList;
        this.mList = mList;
        this.type = type;
    }

    @NonNull
    @Override
    public DayViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.day_item,null);
        DayViewHolder viewHolder = new DayViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull DayViewHolder holder, int position) {
        holder.tv_day.setText(mList.get(position).indexOf("月")!= -1?String.valueOf(mList.get(position)).split("月")[1]:mList.get(position));
        holder.tv_day.setHint(mList.get(position));
        if (mList.get(position).indexOf("月")!= -1){
            if ("今天".equals(String.valueOf(mList.get(position)).split("月")[1])){
                holder.tv_day.setTextColor(Color.parseColor("#3DBFB0"));
            }else {
                holder.tv_day.setTextColor(Color.parseColor("#333333"));
            }
        }
        holder.tv_date.setText(mCalendarList.get(position).getNote());
        holder.tv_date.setVisibility(TextUtils.isEmpty(mCalendarList.get(position).getNote())?View.GONE:View.VISIBLE);

        if (position < 7){
            holder.tv_day.setTextColor(Color.parseColor("#000000"));
        }
        mHolderList.add(holder);
        switch (type){
            case 1:
                singleSelect(holder,position);
                break;
            case 2:
                multipleSelect(holder,position);
                break;
        }
    }

    /**
     * 连续多选
     * @param holder
     * @param position
     */

    private void multipleSelect(final DayViewHolder holder, final int position) {
        holder.ll_day_item.setPadding(0,10,0,0);
        if (position < 7 || TextUtils.isEmpty(holder.tv_day.getText())){
            return;
        }
        mHolderList.get(position).ll_day.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mHolderList.get(position).ll_day.getTag() == null || (boolean) mHolderList.get(position).ll_day.getTag() == false){
                    //表示没有选中
                    setBg(holder.tv_day,holder.ll_day);
                    mHolderList.get(position).ll_day.setTag(true);
                    /**
                     * 处理两个选中状态
                     */
                    setSelectStutas();
                }else {
                    clearcolor();
                }
            }
        });
    }

    //点击起始日期与终点日期中间自动选择
    private int a = -1, b = -1;
    private void setSelectStutas() {
        for (int n = 0; n < mHolderList.size(); n++){
            if (mHolderList.get(n).ll_day.getTag() != null && (boolean)(mHolderList.get(n).ll_day.getTag()) == true){
                if (a == -1){
                    /**
                     * a == -1时表示一个都没有选
                     */
                    a = n;
                    return;
                }else if (b == -1 && n != a){
                    /**
                     * b = -1表示右边没选，则需要分两种情况
                     * 在这里a始终表示小的数 b表示大的数
                     */
                    if (n < a){
                        b = a;
                        a = n;
                    }else {
                        b = n;
                    }
                    /**
                     * 设置选中范围的背景
                     */
                    setSelectBg();
                    return;
                }else if (a != -1 && b != -1){
                    /**
                     * 这种状态表示a和b都已经选择了
                     */
                    if (n < a){
                        mHolderList.get(a).ll_day.setTag(false);
                        a = n;
                    }else if (n > b){
                        mHolderList.get(b).ll_day.setTag(false);
                        b = n;
                    }else if (n > a && n < b){
                        mHolderList.get(b).ll_day.setTag(false);
                        b = n;
                    }
                    setSelectBg();
                    return;
                }
            }
        }
    }

    /**
     * 设置选中范围的背景
     */
    private void setSelectBg() {
        for (int i = 0; i < mHolderList.size();i++){
            if (i == a){
                mHolderList.get(a).ll_day.setTag(true);
                mHolderList.get(a).ll_day.setBackgroundResource(R.drawable.button_radius_bule_left);
                mHolderList.get(a).tv_day.setTextColor(Color.parseColor("#ffffff"));
                uploadShape(mHolderList.get(a).ll_day);
            }else if (i == b){
                mHolderList.get(b).ll_day.setTag(true);
                mHolderList.get(b).ll_day.setBackgroundResource(R.drawable.button_radius_bule_right);
                mHolderList.get(b).tv_day.setTextColor(Color.parseColor("#ffffff"));
                uploadShape(mHolderList.get(b).ll_day);
            }else if (i > a && i < b){
                mHolderList.get(i).ll_day.setBackgroundResource(R.drawable.button_bule);
                mHolderList.get(i).tv_day.setTextColor(Color.parseColor("#ffffff"));
                uploadShape(mHolderList.get(i).ll_day);
            }else {
                resetBg(mHolderList.get(i).tv_day,mHolderList.get(i).ll_day);
            }
        }
        if (mOnCalendarDoubleSelectListener != null){
            mOnCalendarDoubleSelectListener.onCalendarDoubleSelect(mList.get(a),mList.get(b));
        }
    }

    /**
     * 在多选的状态下重置
     */
    public void clearcolor() {
        a = -1;
        b = -1;
        for (int i = 0; i < mHolderList.size(); i++) {
            mHolderList.get(i).ll_day.setTag(false);
            resetBg(mHolderList.get(i).tv_day,mHolderList.get(i).ll_day);
        }
    }


    /**
     * 单选
     * @param holder
     * @param position
     */
    private int index = -1;
    private void singleSelect(final DayViewHolder holder, final int position) {
        if (position < 7 || TextUtils.isEmpty(holder.tv_date.getText())){
            return;
        }
        holder.ll_day.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnCalendarSingleSelectListener != null){
                    mOnCalendarSingleSelectListener.onCalendarSingleSelect(holder.tv_day.getText().toString().trim());
                }
                if (position == index) {
                    index = -1;
                    holder.tv_date.setTag(false);
                    resetBg(holder.tv_day,holder.ll_day);
                } else {
                    index = position;
                    holder.tv_date.setTag(true);
                    setBg(holder.tv_day,holder.ll_day);
                }
                mHolderList.clear();
                notifyDataSetChanged();
            }
        });
    }

    /**
     * 重置背景
     * @param tv_day
     * @param ll_day
     */
    private void resetBg(TextView tv_day, LinearLayout ll_day) {
        if ("今天".equals(tv_day.getText().toString().trim())){
            tv_day.setTextColor(Color.parseColor("#3DBFB0"));
        }else {
            tv_day.setTextColor(Color.parseColor("#333333"));
        }
        ll_day.setBackgroundResource(R.drawable.button_radius_white);
    }

    /**
     * 设置背景
     */
    private void setBg(TextView tv_date, LinearLayout ll_day) {
        ll_day.setBackgroundResource(R.drawable.button_radius_bule);
        tv_date.setTextColor(Color.parseColor("#ffffff"));
        uploadShape(ll_day);
    }

    private void uploadShape(LinearLayout view){
        GradientDrawable mGroupDrawable= (GradientDrawable) view.getBackground();
        /*设置整体背景颜色*/
        mGroupDrawable.setColor(Color.parseColor(Constant.DAYITEM));
    }

    /**
     * 获取选中的数据
     */
    public List<String> getList(){
        List<String> list = new ArrayList<>();
        for (int i = 0; i < mHolderList.size(); i++){
            if (mHolderList.get(i).ll_day.getTag() != null && (boolean)mHolderList.get(i).ll_day.getTag()){
                list.add(mHolderList.get(i).tv_day.getText().toString().trim());
            }
        }
        if (type == 2){
            if (mList.size() == 2){
                int start = Integer.parseInt(list.get(0).split("月")[1]);
                int end = Integer.parseInt(list.get(1).split("月")[1]);
                for (int i = start; i <= end;i++){
                    list.add(mHolderList.get(i).tv_day.getText().toString().trim());
                }
            }
        }
        return list;
    }

    /**
     * 在连续多选的模式下，设置一进来默认选中的数据
     * @param data
     */
    public void setDefaultData(List<String> data){
        if (type == 1){
            return;
        }
        for (int i = 0;i < data.size();i++){
            for (int j = 0;j < mHolderList.size();j++){
                if (mHolderList.get(j).tv_day.getText().toString().trim().equals(data.get(i))){
                    if (mHolderList.get(j).ll_day.getTag() == null || (boolean) mHolderList.get(j).ll_day.getTag() == false){
                        /**
                         * 表示没有选中
                         */
                        setBg(mHolderList.get(j).tv_day,mHolderList.get(i).ll_day);
                        mHolderList.get(j).ll_day.setTag(true);
                        if (type == 3){
                            setSelectStutas();
                        }
                    }
                }
            }
        }
    }

    @Override
    public void onViewAttachedToWindow(@NonNull DayViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        int width = MethodUtil.getScreenWidth(mContext);
        ViewGroup.LayoutParams layoutParams = holder.ll_day_item.getLayoutParams();
        layoutParams.height = width / 7;
        holder.ll_day_item.setLayoutParams(layoutParams);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    static class DayViewHolder extends RecyclerView.ViewHolder{

        private LinearLayout ll_day_item;
        private LinearLayout ll_day;
        private TextView tv_day;
        private TextView tv_date;

        public DayViewHolder(@NonNull View itemView) {
            super(itemView);
            ll_day_item = itemView.findViewById(R.id.ll_day_item);
            ll_day = itemView.findViewById(R.id.ll_day);
            tv_day = itemView.findViewById(R.id.tv_day);
            tv_date = itemView.findViewById(R.id.tv_date);
        }
    }
}
