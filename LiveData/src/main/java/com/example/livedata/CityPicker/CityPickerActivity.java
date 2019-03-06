package com.example.livedata.CityPicker;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.livedata.R;
import com.lljjcoder.Interface.OnCityItemClickListener;
import com.lljjcoder.bean.CityBean;
import com.lljjcoder.bean.DistrictBean;
import com.lljjcoder.bean.ProvinceBean;
import com.lljjcoder.citywheel.CityConfig;
import com.lljjcoder.style.citylist.Toast.ToastUtils;
import com.lljjcoder.style.citypickerview.CityPickerView;

public class CityPickerActivity extends AppCompatActivity {

    private Button button;
    private TextView textView1,textView2,textView3;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);
        button = findViewById(R.id.btn);
        textView1 = findViewById(R.id.tv_provice);
        textView2 = findViewById(R.id.tv_shi);
        textView3 = findViewById(R.id.tv_xian);
        final CityPickerView cityPickerView = new CityPickerView();
        cityPickerView.init(this);

        CityConfig cityConfig = new CityConfig.Builder().build();
        //设置自定义的属性配置
        cityPickerView.setConfig(cityConfig);
        cityPickerView.setOnCityItemClickListener(new OnCityItemClickListener() {
            @Override
            public void onSelected(ProvinceBean province, CityBean city, DistrictBean district) {
                super.onSelected(province, city, district);
                textView1.setText(province.toString());
                textView2.setText(city.toString());
                textView3.setText(district.toString());
            }

            @Override
            public void onCancel() {
                ToastUtils.showLongToast(CityPickerActivity.this, "已取消");
            }
        });
        cityPickerView.showCityPicker();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*CityConfig cityConfig = new CityConfig.Builder()
                        .title("选择城市")//标题
                        .titleTextSize(18)//标题文字大小
                        .titleTextColor("#585858")//标题文字颜  色
                        .titleBackgroundColor("#E9E9E9")//标题栏背景色
                        .confirTextColor("#585858")//确认按钮文字颜色
                        .confirmText("ok")//确认按钮文字
                        .confirmTextSize(16)//确认按钮文字大小
                        .cancelTextColor("#585858")//取消按钮文字颜色
                        .cancelText("cancel")//取消按钮文字
                        .cancelTextSize(16)//取消按钮文字大小
                        .setCityWheelType(CityConfig.WheelType.PRO_CITY_DIS)//显示类，只显示省份一级，显示省市两级还是显示省市区三级
                        .showBackground(true)//是否显示半透明背景
                        .visibleItemsCount(7)//显示item的数量
                        .province("浙江省")//默认显示的省份
                        .city("杭州市")//默认显示省份下面的城市
                        .district("滨江区")//默认显示省市下面的区县数据
                        .provinceCyclic(true)//省份滚轮是否可以循环滚动
                        .cityCyclic(true)//城市滚轮是否可以循环滚动
                        .districtCyclic(true)//区县滚轮是否循环滚动
                        .drawShadows(false)//滚轮不显示模糊效果
                        .setLineColor("#03a9f4")//中间横线的颜色
                        .setLineHeigh(5)//中间横线的高度
                        .setShowGAT(true)//是否显示港澳台数据，默认不显示
                        .build();*/

            }
        });
    }
}
