package com.example.customview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;


/**
 * author : zhangzf
 * date   : 2020/12/5
 * desc   :
 */
public class TravelPeopleView extends RelativeLayout {
    private TextView tvPeopleType,tvAmount,tvAge,tvRange;
    private ImageView ivSub,ivAdd;

    private int mAmount = 1;
    private Context mContext;
    private OnTravelPeopleAmountListener mOnTravelPeopleAmountListener;

    public void setOnTravelPeopleAmountListener(OnTravelPeopleAmountListener mOnTravelPeopleAmountListener) {
        this.mOnTravelPeopleAmountListener = mOnTravelPeopleAmountListener;
    }

    public TravelPeopleView(Context context) {
        this(context, null);
    }

    public TravelPeopleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        initView();
    }

    @SuppressLint("SetTextI18n")
    private void initView() {
        View inflateView = View.inflate(mContext, R.layout.view_travel_people, this);
        tvPeopleType = inflateView.findViewById(R.id.tv_people_type);
        tvAmount = inflateView.findViewById(R.id.tv_amount);
        tvAge = inflateView.findViewById(R.id.tv_age);
        tvRange = inflateView.findViewById(R.id.tv_range);
        ivAdd = inflateView.findViewById(R.id.iv_add);
        ivSub = inflateView.findViewById(R.id.iv_sub);
        tvAmount.setText(mAmount + "");
        ivSub.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if ("0".equals(tvAmount.getText().toString().trim())){
                    //ToastUtils.showShort(mContext.getString(R.string.tips_select_amount));
                }else {
                    mAmount--;
                }
                tvAmount.setText(mAmount + "");
                if (mOnTravelPeopleAmountListener != null){
                    mOnTravelPeopleAmountListener.onTravelPeopleAmount(mAmount);
                }
            }
        });
        ivAdd.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mAmount++;
                tvAmount.setText(mAmount + "");
                if (mOnTravelPeopleAmountListener != null){
                    mOnTravelPeopleAmountListener.onTravelPeopleAmount(mAmount);
                }
            }
        });
    }

    public void setPeopleType(String type){
        if (!TextUtils.isEmpty(type)){
            tvPeopleType.setText(type);
        }else {
            //ToastUtils.showLong(mContext.getString(R.string.tips_people_type));
        }
    }

    public void setAgeRange(String ageRange){
        if (!TextUtils.isEmpty(ageRange)){
            tvAge.setVisibility(VISIBLE);
            tvAge.setText(ageRange);
        }else {
            tvAge.setVisibility(GONE);
        }
    }

    public void setHeightRange(String heightRange){
        if (!TextUtils.isEmpty(heightRange)){
            tvRange.setVisibility(VISIBLE);
            tvRange.setText(heightRange);
        }else {
            tvRange.setVisibility(GONE);
        }
    }

    /**
     * 获取出行人数
     * @return
     */
    public String getTravelPeople(){
        return tvAmount.getText().toString().trim();
    }
}
