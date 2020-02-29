package com.example.gaode;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.provider.Settings;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.amap.api.location.AMapLocation;
import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.LocationSource;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.UiSettings;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.Marker;
import com.amap.api.maps2d.model.MyLocationStyle;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LocationSource{

    private MapView mapView;
    private AMap aMap;
    private LocationUtil locationUtil;
    private LocationSource.OnLocationChangedListener mOnLocationChangedListener = null;
    protected String[] needPermissions = {
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.READ_PHONE_STATE
    };
    private MyLocationStyle myLocationStyle;
    private EditText editText;
    private Button button;
    //
    private Marker locationMarker;



   @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = findViewById(R.id.et_search);
        button = findViewById(R.id.btn);
        mapView = findViewById(R.id.mv);
        mapView.onCreate(savedInstanceState);

        init();
    }

    private void init() {
       if (aMap == null){
           aMap = mapView.getMap();
       }
       UiSettings settings =aMap.getUiSettings();
       settings.setMyLocationButtonEnabled(true);
                //设置定位监听
       aMap.setLocationSource((LocationSource) this);
        //设置缩放级别
       aMap.moveCamera(CameraUpdateFactory.zoomTo(15));
        //蓝点初始化
       myLocationStyle = new MyLocationStyle();
       myLocationStyle.interval(2000);
       aMap.setMyLocationStyle(myLocationStyle);
        //显示定位层并可触发，默认false
       aMap.setMyLocationEnabled(true);
       locationUtil = LocationUtil.getInstance();
       locationUtil.setLocationCallBack(new LocationUtil.ILocationCallBack() {
            @Override
            public void callBack(String str, double lat, double lgt, AMapLocation aMapLocation) {
                //根据获取的经纬度，将地图移动到定位位置
                aMap.moveCamera(CameraUpdateFactory.changeLatLng(new LatLng(lat,lgt)));
                mOnLocationChangedListener.onLocationChanged(aMapLocation);

                //添加定位图标
                if (locationMarker == null){
                    locationMarker = aMap.addMarker(locationUtil.getMarkerOption(str,lat,lgt));
                    locationMarker.showInfoWindow();
                    //固定标签在屏幕中央
                    locationMarker.setPositionByPixels(mapView.getWidth()/2 , mapView.getHeight()/2);
                }else {
                    locationMarker.setPosition(new LatLng(lat,lgt));
                }
                aMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat,lgt),15));

            }
        });




       button.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               checkPermissions();
           }
       });




    }

    @Override
    public void activate(OnLocationChangedListener onLocationChangedListener) {
        mOnLocationChangedListener = onLocationChangedListener;
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    public void deactivate() {
        mOnLocationChangedListener = null;
    }


    //获取权限集中需要申请的权限列表

    private void checkPermissions(){
       List<String> needRequestPermissonList = findDeniedPermissions(needPermissions);
       if (null != needRequestPermissonList && needRequestPermissonList.size() > 0){
           ActivityCompat.requestPermissions(this,needRequestPermissonList.toArray(new String[needRequestPermissonList.size()]),1);
       }else {
           locationUtil.startMap();
       }
    }


    //检查权限
    private List<String> findDeniedPermissions(String[] permissions){
        List<String> needRequestPermissonList = new ArrayList<String>();
        for (String str : permissions){
            if (ContextCompat.checkSelfPermission(this,str) != PackageManager.PERMISSION_GRANTED || ActivityCompat.shouldShowRequestPermissionRationale(this,str)){
                needRequestPermissonList.add(str);
            }
        }
        return needRequestPermissonList;
    }


    //检测是否说有的权限都已经授权
    private boolean verifyPermissions(int[] grantResults){
        for (int result : grantResults){
            if (result != PackageManager.PERMISSION_GRANTED){
                return false;
            }
        }
        return true;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 1:
                if (!verifyPermissions(grantResults)){
                    showMissingPermissionDialog();
                }else {
                    locationUtil.startMap();
                }
        }
    }

    private void showMissingPermissionDialog() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("当前应用缺少必要权限");
        builder.setMessage("当前应用需要打开定位功能。\\n\\n请点击\\\"设置\\\"-\\\"定位服务\\\"-打开定位功能。");
        builder.setNegativeButton("取消",new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startAppSettings();
            }
        });
        builder.setCancelable(false);
        builder.show();
    }

    private void startAppSettings() {
        /*Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.parse("package:" + getPackageName()));*/
        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        startActivity(intent);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
       if (keyCode == KeyEvent.KEYCODE_BACK){
           finish();
           return true;
       }
       return super.onKeyDown(keyCode, event);
    }
}
