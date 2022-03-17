package com.example.dell.myapplication;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.callback.NavigationCallback;
import com.alibaba.android.arouter.launcher.ARouter;
import com.example.a2lib.A2Impl;
import com.example.ainterfacelib.AInterface;
import com.example.ainterfacelib.BInterface;
import com.example.ainterfacelib.CInterface;
import com.example.alib.AImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private List<Integer> datas;
    private RecyclerView.ItemDecoration mItemDecoration;
    private MyAdapter mMyAdapter;
    private Resources resources;
    private Button btn,btn_intepter,btn1,btn_url,btn_ForResult,btn_MaterialRatingBar,btn_sure,btn_jump,btn_sure_service;
    private EditText editText;
    private HashMap<Integer,Integer> map;
    private String str = "<head></head><body>{\"status\":200,\"msg\":\"\\u4ed8\\u6b3e\\u6210\\u529f\",\"order_sn\":\"GT2100142\",\"type\":\"0\",\"refund_id\":\"59K855664M682561S\",\"amount\":\"835.00\"}<div id=\"think_page_trace\" style=\"position: fixed;bottom:0;right:0;font-size:14px;width:100%;z-index: 999999;color: #000;text-align:left;font-family:'微软雅黑';\">\n" +
            "        <div id=\"think_page_trace_tab\" style=\"display: none;background:white;margin:0;height: 250px;\">\n" +
            "            <div id=\"think_page_trace_tab_tit\" style=\"height:30px;padding: 6px 12px 0;border-bottom:1px solid #ececec;border-top:1px solid #ececec;font-size:16px\">\n" +
            "                            <span style=\"color: rgb(0, 0, 0); padding-right: 12px; height: 30px; line-height: 30px; display: inline-block; margin-right: 3px; cursor: pointer; font-weight: 700;\">基本</span>\n" +
            "                            <span style=\"color: rgb(153, 153, 153); padding-right: 12px; height: 30px; line-height: 30px; display: inline-block; margin-right: 3px; cursor: pointer; font-weight: 700;\">文件</span>\n" +
            "                            <span style=\"color: rgb(153, 153, 153); padding-right: 12px; height: 30px; line-height: 30px; display: inline-block; margin-right: 3px; cursor: pointer; font-weight: 700;\">流程</span>\n" +
            "                            <span style=\"color: rgb(153, 153, 153); padding-right: 12px; height: 30px; line-height: 30px; display: inline-block; margin-right: 3px; cursor: pointer; font-weight: 700;\">错误</span>\n" +
            "                            <span style=\"color: rgb(153, 153, 153); padding-right: 12px; height: 30px; line-height: 30px; display: inline-block; margin-right: 3px; cursor: pointer; font-weight: 700;\">SQL</span>\n" +
            "                            <span style=\"color: rgb(153, 153, 153); padding-right: 12px; height: 30px; line-height: 30px; display: inline-block; margin-right: 3px; cursor: pointer; font-weight: 700;\">调试</span>\n" +
            "                        </div>\n" +
            "            <div id=\"think_page_trace_tab_cont\" style=\"overflow:auto;height:212px;padding:0;line-height: 24px\">\n" +
            "                            <div style=\"display: block;\">\n" +
            "                    <ol style=\"padding: 0; margin:0\">\n" +
            "                        <li style=\"border-bottom:1px solid #EEE;font-size:14px;padding:0 12px\">请求信息 : 2021-09-16 17:32:01 HTTP/1.1 GET : https://kevin.bigline.cc/api/v1/notify/paypal_notify?success=true&amp;order_sn=GT2100142&amp;type=0&amp;paymentId=PAYID-MFBQ5KQ42131433NN5364608&amp;token=EC-1UD77879037602805&amp;PayerID=L8ZK3DBMSZ3L8</li><li style=\"border-bottom:1px solid #EEE;font-size:14px;padding:0 12px\">运行时间 : 6.116112s [ 吞吐率：0.16req/s ] 内存消耗：5,726.33kb 文件加载：297</li><li style=\"border-bottom:1px solid #EEE;font-size:14px;padding:0 12px\">查询信息 : 13 queries</li><li style=\"border-bottom:1px solid #EEE;font-size:14px;padding:0 12px\">缓存信息 : 1 reads,0 writes</li><li style=\"border-bottom:1px solid #EEE;font-size:14px;padding:0 12px\">会话信息 : SESSION_ID=aaacf203e5e7731e581b5e30a217639a</li>                </ol>\n" +
            "                </div>\n" +
            "                            <div style=\"display:none;\">\n" +
            "                    <ol style=\"padding: 0; margin:0\">\n" + "</div>\n" +
            "                            <div style=\"display:none;\">\n" +
            "                    <ol style=\"padding: 0; margin:0\">\n" +
            "                                        </ol>\n" +
            "                </div>\n" +
            "                            <div style=\"display:none;\">\n" +
            "                    <ol style=\"padding: 0; margin:0\">\n" +
            "                        <li style=\"border-bottom:1px solid #EEE;font-size:14px;padding:0 12px\">paypal回调参数:{\"success\":\"true\",\"order_sn\":\"GT2100142\",\"type\":\"0\",\"paymentId\":\"PAYID-MFBQ5KQ42131433NN5364608\",\"token\":\"EC-1UD77879037602805\",\"PayerID\":\"L8ZK3DBMSZ3L8\"}</li>                </ol>\n" +
            "                </div>\n" +
            "                            <div style=\"display:none;\">\n" +
            "                    <ol style=\"padding: 0; margin:0\">";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Pattern pattern = Pattern.compile("[{].*[}]");
        Matcher matcher = pattern.matcher(str);
        List<String> list = new ArrayList<>();
        while (matcher.find()){
            list.add(matcher.group());
        }
        String html = "";
        for (String str:list){
            if (str.contains("status")){
                html = str;
            }
        }
        html = html.substring(1,html.length()-1);
        String[] strs = html.split(",");
        String[] strs2 = strs[0].split(":");
        Log.e("zzf",html);
        Log.e("zzf",strs[0] + "----" + strs2[1]);
        btn_sure_service = findViewById(R.id.btn_sure_service);
        btn_sure_service.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AImpl aInterface = (AImpl) ServiceHelper.getService(AInterface.class);
                Log.e("zzf",aInterface + "");
                BInterface bInterface = (A2Impl) ServiceHelper.getService(BInterface.class);
                Log.e("zzf",aInterface.getname() +"------------" + bInterface.getname());

                List<CInterface> list = ServiceHelper.getServices(CInterface.class);
                for (CInterface cInterface:list){
                    Log.e("zzf",cInterface.getName());
                }

            }
        });
        map = new HashMap<>();
        initMap();
        initView();
        initData();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

            }
        },100);
    }

    private void initMap() {
        map.put(1,1);
        map.put(2,3);
        map.put(3,3);
        Integer value = map.put(18,2);
        Integer value1 = map.put(2,4);
        Log.e("zzf",value + "" + value1);
    }

    private void initData() {
        resources = getResources();
        datas = new ArrayList<>();
        for (int i = 0; i < 38; i++) {
            datas.add(resources.getIdentifier("ic_category_" + i, "mipmap", getPackageName()));
        }
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);
        mItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST);
        mRecyclerView.addItemDecoration(mItemDecoration);
        mMyAdapter = new MyAdapter(this);
        mMyAdapter.setDatas(datas);
        mRecyclerView.setAdapter(mMyAdapter);
        mMyAdapter.setmOnItemClickListener(new MyAdapter.OnItemClickListener() {
            @Override
            public void onItemClickListener(int position, Integer data) {
                Toast.makeText(MainActivity.this, "点击了" + position, Toast.LENGTH_LONG).show();
            }
        });
        //startActivity(new Intent(this,MainActivity.class));
    }

    private void initView() {
        mRecyclerView = findViewById(R.id.rv);
        btn = findViewById(R.id.btn);
        btn_intepter = findViewById(R.id.btn_intepter);
        btn1 = findViewById(R.id.btn1);
        btn_url = findViewById(R.id.btn_url);
        editText = findViewById(R.id.et);
        btn_MaterialRatingBar = findViewById(R.id.btn_MaterialRatingBar);
        btn_ForResult = findViewById(R.id.btn_ForResult);
        btn_sure = findViewById(R.id.btn_sure);
        btn_jump = findViewById(R.id.btn_jump);
        btn_jump.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("data","返回值成功");
                setResult(1,intent);
                finish();
            }
        });
        btn_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,OtherActivity.class));
            }
        });
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 * 不带参跳转
                 */
                ARouter.getInstance()
                        .build("/main/LogoutActivity")
                        .navigation();
            }
        });
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ARouter.getInstance()
                        .build("/main/login")
                        .withString("path","您好")
                        .navigation();
            }
        });
        btn_intepter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ARouter.getInstance()
                        .build("/main/login")
                        .withString("path","您好")
                        .navigation();
            }
        });
        btn_url.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("test://com/main/UrlLoginActivity");
                ARouter.getInstance().build(uri).navigation(MainActivity.this, new NavigationCallback() {
                    @Override
                    public void onFound(Postcard postcard) {

                    }

                    @Override
                    public void onLost(Postcard postcard) {
                        /**
                         * 在这做降级处理
                         */
                    }

                    @Override
                    public void onArrival(Postcard postcard) {
                        /**
                         * 跳转成功的回调
                         */
                    }

                    @Override
                    public void onInterrupt(Postcard postcard) {

                    }
                });
            }
        });
        btn_ForResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 * 很多博客都说requestCode必须》0
                 * 其实看源码0是可以的
                 */
                /**
                 * if (requestCode >= 0) {  // Need start for result
                 *             if (currentContext instanceof Activity) {
                 *                 ActivityCompat.startActivityForResult((Activity) currentContext, intent, requestCode, postcard.getOptionsBundle());
                 *             } else {
                 *                 logger.warning(Consts.TAG, "Must use [navigation(activity, ...)] to support [startActivityForResult]");
                 *             }
                 *         } else {
                 *             ActivityCompat.startActivity(currentContext, intent, postcard.getOptionsBundle());
                 *         }
                 */
                ARouter.getInstance().build("/main/ForResultActivity")
                        .withInt("paths",1)
                        .navigation(MainActivity.this,0);
            }
        });
        btn_MaterialRatingBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,MaterialRatingBarActivity.class));
            }
        });
    }

   /* @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add:
                //java.lang.NoClassDefFoundError: Failed resolution of: Landroid/support/v4/animation/AnimatorCompatHe
                //报这个错时将recyclerview的依赖改成：implementation 'com.android.support:recyclerview-v7:27.1.1'
                //int i = (int) (Math.random()*datas.size());
                int i = 1;
                mMyAdapter.addData(i, resources.getIdentifier("ic_category_" + i, "mipmap", getPackageName()));
                //mMyAdapter.notifyItemInserted(1);
                //mMyAdapter.notifyItemRangeInserted(2,6);
                break;
            case R.id.delete:
                //int i1 = (int) (Math.random()*datas.size());
                //int i1 = 1;
                //datas.remove(i1);
                mMyAdapter.removeData(2);
                //mMyAdapter.notifyItemRangeChanged(1,5);
                break;
            case R.id.list_view:
                mRecyclerView.setBackgroundColor(Color.TRANSPARENT);
                mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
                mMyAdapter.setType(0);
                mRecyclerView.removeItemDecoration(mItemDecoration);
                mRecyclerView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                mItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST);
                mRecyclerView.addItemDecoration(mItemDecoration);
                break;
            case R.id.horizontal_list__iew:
                mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
                mMyAdapter.setType(0);
                mRecyclerView.removeItemDecoration(mItemDecoration);
                mRecyclerView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
                mItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.HORIZONTAL_LIST);
                mRecyclerView.addItemDecoration(mItemDecoration);
                break;
            case R.id.gridview:
                mMyAdapter.setType(1);
                mRecyclerView.removeItemDecoration(mItemDecoration);
                mItemDecoration = new DividerGridItemDecoration(this);
                mRecyclerView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
                mRecyclerView.setLayoutManager(new GridLayoutManager(this, 5));
                mItemDecoration = new DividerGridItemDecoration(this);
                mRecyclerView.addItemDecoration(mItemDecoration);
                break;
            case R.id.horizontalGridView:
                mMyAdapter.setType(1);
                mRecyclerView.removeItemDecoration(mItemDecoration);
                mRecyclerView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
                mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(5, StaggeredGridLayoutManager.HORIZONTAL));
                mItemDecoration = new DividerGridItemDecoration(this);
                mRecyclerView.addItemDecoration(mItemDecoration);

                break;
            case R.id.staggeredgridview:
                mMyAdapter.setType(3);
                mRecyclerView.removeItemDecoration(mItemDecoration);
                mRecyclerView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
                mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(5, StaggeredGridLayoutManager.VERTICAL));
                mItemDecoration = new DividerGridItemDecoration(this);
                mRecyclerView.addItemDecoration(mItemDecoration);
                break;
            default:
                break;
        }
        return true;
    }*/

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0){
            if (resultCode == 1){
                btn_ForResult.setText(data.getIntExtra("name",0)+"");
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("zzf","main----onResume");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("zzf","main----onDestroy");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.e("zzf","main----onRestart");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e("zzf","main----onStop");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e("zzf","main----onStart");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e("zzf","main----onPause");
    }
}