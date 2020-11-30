package com.bigkoo.pickerview.view;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.R;
import com.bigkoo.pickerview.configure.PickerOptions;
import com.bigkoo.pickerview.listener.ISelectTimeCallback;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

/**
 * author : zhangzf
 * date   : 2020/11/28
 * desc   :
 */
public class CustomTimePickerView extends BasePickerView implements View.OnClickListener {
    private WheelSelectTime wheelTime;
    private TextView tv_sure,tv_cancel,tv_title;

    public CustomTimePickerView(PickerOptions pickerOptions) {
        super(pickerOptions.context);
        mPickerOptions = pickerOptions;
        initView(pickerOptions.context);
    }

    private void initView(Context context) {
        setDialogOutSideCancelable();
        initViews();
        initAnim();

        if (mPickerOptions.customListener == null) {
            View view = LayoutInflater.from(context).inflate(R.layout.custom_pickerview_time, contentContainer);
            tv_sure = view.findViewById(R.id.tv_sure);
            tv_cancel = view.findViewById(R.id.tv_cancel);
            tv_title = view.findViewById(R.id.tv_title);

            /**
             * 设置监听事件
             */
            tv_sure.setOnClickListener(this);
            tv_cancel.setOnClickListener(this);

            //设置文字
            tv_sure.setText(TextUtils.isEmpty(mPickerOptions.textContentConfirm) ? context.getResources().getString(R.string.pickerview_submit) : mPickerOptions.textContentConfirm);
            tv_cancel.setText(TextUtils.isEmpty(mPickerOptions.textContentCancel) ? context.getResources().getString(R.string.pickerview_cancel) : mPickerOptions.textContentCancel);
            tv_title.setText(TextUtils.isEmpty(mPickerOptions.textContentTitle) ? "" : mPickerOptions.textContentTitle);//默认为空

            //设置color
            tv_sure.setTextColor(mPickerOptions.textColorConfirm);
            tv_cancel.setTextColor(mPickerOptions.textColorCancel);
            tv_title.setTextColor(mPickerOptions.textColorTitle);


            //设置文字大小
            tv_cancel.setTextSize(mPickerOptions.textSizeSubmitCancel);
            tv_sure.setTextSize(mPickerOptions.textSizeSubmitCancel);
            tv_title.setTextSize(mPickerOptions.textSizeTitle);
        } else {
            mPickerOptions.customListener.customLayout(LayoutInflater.from(context).inflate(mPickerOptions.layoutRes, contentContainer));
        }
        // 时间转轮 自定义控件
        LinearLayout timePickerView = (LinearLayout) findViewById(com.bigkoo.pickerview.R.id.timepicker);
        timePickerView.setBackgroundColor(mPickerOptions.bgColorWheel);
        initWheelTime(timePickerView);
    }

    private void initWheelTime(LinearLayout timePickerView) {
        wheelTime = new WheelSelectTime(timePickerView, mPickerOptions.type, mPickerOptions.textGravity, mPickerOptions.textSizeContent);
        if (mPickerOptions.timeSelectChangeListener != null) {
            wheelTime.setSelectChangeCallback(new ISelectTimeCallback() {
                @Override
                public void onTimeSelectChanged() {
                    try {
                        Date date = WheelTime.dateFormat.parse(wheelTime.getTime());
                        mPickerOptions.timeSelectChangeListener.onTimeSelectChanged(date);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            });
        }

        if (mPickerOptions.startYear != 0 && mPickerOptions.endYear != 0
                && mPickerOptions.startYear <= mPickerOptions.endYear) {
            setRange();
        }

        //若手动设置了时间范围限制
        if (mPickerOptions.startDate != null && mPickerOptions.endDate != null) {
            if (mPickerOptions.startDate.getTimeInMillis() > mPickerOptions.endDate.getTimeInMillis()) {
                throw new IllegalArgumentException("startDate can't be later than endDate");
            } else {
                setRangDate();
            }
        } else if (mPickerOptions.startDate != null) {
            if (mPickerOptions.startDate.get(Calendar.YEAR) < 1900) {
                throw new IllegalArgumentException("The startDate can not as early as 1900");
            } else {
                setRangDate();
            }
        } else if (mPickerOptions.endDate != null) {
            if (mPickerOptions.endDate.get(Calendar.YEAR) > 2100) {
                throw new IllegalArgumentException("The endDate should not be later than 2100");
            } else {
                setRangDate();
            }
        } else {//没有设置时间范围限制，则会使用默认范围。
            setRangDate();
        }

        setTime();

        wheelTime.setLabels(mPickerOptions.label_year, mPickerOptions.label_month, mPickerOptions.label_day
                , mPickerOptions.label_another_year, mPickerOptions.label_another_month, mPickerOptions.label_another_day);
        wheelTime.setTextXOffset(mPickerOptions.x_offset_year, mPickerOptions.x_offset_month, mPickerOptions.x_offset_day,
                mPickerOptions.x_offset_another_year, mPickerOptions.x_offset_another_month, mPickerOptions.x_offset_another_day);
        wheelTime.setItemsVisible(mPickerOptions.itemsVisibleCount);
        wheelTime.setAlphaGradient(mPickerOptions.isAlphaGradient);
        setOutSideCancelable(mPickerOptions.cancelable);
        wheelTime.setCyclic(mPickerOptions.cyclic);
        wheelTime.setDividerColor(mPickerOptions.dividerColor);
        wheelTime.setDividerType(mPickerOptions.dividerType);
        wheelTime.setLineSpacingMultiplier(mPickerOptions.lineSpacingMultiplier);
        wheelTime.setTextColorOut(mPickerOptions.textColorOut);
        wheelTime.setTextColorCenter(mPickerOptions.textColorCenter);
        wheelTime.isCenterLabel(mPickerOptions.isCenterLabel);
    }

    /**
     * 设置选中时间,默认选中当前时间
     */
    private void setTime() {
        int year, month, day, another_year, another_month, another_day;
        Calendar calendar = Calendar.getInstance();

        if (mPickerOptions.date == null) {
            calendar.setTimeInMillis(System.currentTimeMillis());
            year = calendar.get(Calendar.YEAR);
            month = calendar.get(Calendar.MONTH);
            day = calendar.get(Calendar.DAY_OF_MONTH);
            another_year = calendar.get(Calendar.HOUR_OF_DAY);
            another_month = calendar.get(Calendar.MINUTE);
            another_day = calendar.get(Calendar.SECOND);
        } else {
            year = mPickerOptions.date.get(Calendar.YEAR);
            month = mPickerOptions.date.get(Calendar.MONTH);
            day = mPickerOptions.date.get(Calendar.DAY_OF_MONTH);
            another_year = mPickerOptions.date.get(Calendar.HOUR_OF_DAY);
            another_month = mPickerOptions.date.get(Calendar.MINUTE);
            another_day = mPickerOptions.date.get(Calendar.SECOND);
        }

        wheelTime.setPicker(year, month, day, another_year, another_month, another_day);
    }

    /**
     * 设置可以选择的时间范围, 要在setTime之前调用才有效果
     */
    private void setRangDate() {
        wheelTime.setRangDate(mPickerOptions.startDate, mPickerOptions.endDate);
        initDefaultSelectedDate();
    }

    private void initDefaultSelectedDate() {
        //如果手动设置了时间范围
        if (mPickerOptions.startDate != null && mPickerOptions.endDate != null) {
            //若默认时间未设置，或者设置的默认时间越界了，则设置默认选中时间为开始时间。
            if (mPickerOptions.date == null || mPickerOptions.date.getTimeInMillis() < mPickerOptions.startDate.getTimeInMillis()
                    || mPickerOptions.date.getTimeInMillis() > mPickerOptions.endDate.getTimeInMillis()) {
                mPickerOptions.date = mPickerOptions.startDate;
            }
        } else if (mPickerOptions.startDate != null) {
            //没有设置默认选中时间,那就拿开始时间当默认时间
            mPickerOptions.date = mPickerOptions.startDate;
        } else if (mPickerOptions.endDate != null) {
            mPickerOptions.date = mPickerOptions.endDate;
        }
    }

    /**
     * 设置可以选择的时间范围, 要在setTime之前调用才有效果
     */
    private void setRange() {
        wheelTime.setStartYear(mPickerOptions.startYear);
        wheelTime.setEndYear(mPickerOptions.endYear);
    }

    /**
     * 设置默认时间
     */
    public void setDate(Calendar date) {
        mPickerOptions.date = date;
        setTime();
    }

    public void returnData() {
        if (mPickerOptions.mOnCustomTimeSelectListener != null) {
            String time = wheelTime.getTime();
            String[] times = time.split(" ");
            Log.e("zzf",time);
            mPickerOptions.mOnCustomTimeSelectListener.onCustomTimeSelect(times[0],times[1], clickView);
        }
    }

    @Override
    public boolean isDialog() {
        return mPickerOptions.isDialog;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.tv_sure) {
            returnData();
            dismiss();
        } else if (id == R.id.tv_cancel) {
            dismiss();
        }
    }
}
