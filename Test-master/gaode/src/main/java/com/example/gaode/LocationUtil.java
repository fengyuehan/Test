package com.example.gaode;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.MarkerOptions;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class LocationUtil implements AMapLocationListener {

    public static LocationUtil instance;
    public static LocationUtil getInstance(){
        if (instance == null){
            synchronized (LocationUtil.class){
                if (instance == null){
                    instance = new LocationUtil();
                }
            }
        }
        return instance;
    }

    private AMapLocationClient aMapLocationClient;
    private AMapLocationClientOption aMapLocationClientOption;
    private ILocationCallBack callBack;

    public void initMap(Context context) {
        aMapLocationClient = new AMapLocationClient(context);
        aMapLocationClient.setLocationListener(this);

        aMapLocationClientOption = new AMapLocationClientOption();
        // 设置定位模式:
        // AMapLocationMode.Hight_Accuracy，高精度模式:会同时使用网络定位和GPS定位，优先返回最高精度的定位结果，以及对应的地址描述信息
        // AMapLocationMode.Battery_Saving，低功耗模式:不会使用GPS和其他传感器，只会使用网络定位（Wi-Fi和基站定位）
        // AMapLocationMode.Device_Sensors，仅设备模式:不需要连接网络，只使用GPS进行定位，这种模式下不支持室内环境的定位
        aMapLocationClientOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        // 获取一次定位结果：
        // 该方法默认为false表示使用默认的连续定位策略。
        aMapLocationClientOption.setOnceLocation(true);
        // 获取最近3s内精度最高的一次定位结果：
        // 设置setOnceLocationLatest(boolean b)
        // 接口为true，启动定位时SDK会返回最近3s内精度最高的一次定位结果。
        // 如果设置其为true，setOnceLocation(boolean b)接口也会被设置为true，反之不会，默认为false。
        // false表示使用默认的连续定位策略
        aMapLocationClientOption.setOnceLocationLatest(true);
        // 设置定位间隔,单位毫秒,默认为2000ms，最低1000ms。
        aMapLocationClientOption.setInterval(1000);
        // 设置是否返回地址信息（默认返回地址信息）
        aMapLocationClientOption.setNeedAddress(true);
        // 设置是否强制刷新WIFI，默认为true，强制刷新。
        // mLocationOption.setWifiActiveScan(false);

        // 设置是否允许模拟位置,默认为false，不允许模拟位置
        // mLocationOption.setMockEnable(false);
        // 设置定位请求超时时间,单位是毫秒，默认30000毫秒，建议超时时间不要低于8000毫秒。
        aMapLocationClientOption.setHttpTimeOut(20000);
        // 设置是否开启定位缓存机制,缓存机制默认开启。可以关闭缓存机制
        aMapLocationClientOption.setLocationCacheEnable(false);
        // 启动定位,给定位客户端对象设置定位参数
        aMapLocationClient.setLocationOption(aMapLocationClientOption);


    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {

        if (aMapLocation != null){
            if (aMapLocation.getErrorCode() == 0){
                getMapLocation(aMapLocation);
            }
        }else {
            //定位失败时，可通过ErrCode（错误码）信息来确定失败的原因。
            Log.e("AmapError", "location Error, ErrCode:" + aMapLocation.getErrorCode() + ", errInfo:" + aMapLocation
                    .getErrorInfo());
        }
    }

    private void getMapLocation(AMapLocation aMapLocation) {
        String country = aMapLocation.getCountry();
        String province = aMapLocation.getProvince();
        String city = aMapLocation.getCity();
        String district = aMapLocation.getDistrict();
        String street = aMapLocation.getStreet();

        double latitude = aMapLocation.getLatitude();
        double longitude = aMapLocation.getLongitude();
        Log.e("country",country);
        Log.e("province",province);
        Log.e("city",city);
        Log.e("district",district);
        Log.e("street",street);
        Log.e("latitude", String.valueOf(latitude));
        Log.e("longitude", String.valueOf(longitude));
        //获取定位时间
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date date = new Date(aMapLocation.getTime());
        Log.e("GaodeMap", "" + df.format(date));
        callBack.callBack(country + province + city + district + street,latitude,longitude,aMapLocation);
    }

    public void startMap(){
        if (aMapLocationClient != null && aMapLocationClient.isStarted()){
            aMapLocationClient.stopLocation();
        }

        if (aMapLocationClient != null){
            aMapLocationClient.startLocation();
        }
    }

    public void stopMap(){
        if (aMapLocationClient != null){
            aMapLocationClient.stopLocation();
        }
    }

    public void destroyMap(){
        if (aMapLocationClient != null){
            aMapLocationClient.onDestroy();
        }
    }

    //自定义图标
    public MarkerOptions getMarkerOption(String str,double latitude,double longitude){
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.position));
        markerOptions.position(new LatLng(latitude,longitude));
        markerOptions.title(str);
        markerOptions.snippet("纬度:" + latitude + "   经度:" + longitude);
        markerOptions.period(100);
        return markerOptions;
    }

    public interface ILocationCallBack{
        void callBack(String str,double lat,double lgt,AMapLocation aMapLocation);
    }

    public void setLocationCallBack(ILocationCallBack callBack){
        this.callBack = callBack;
    }
}
