package com.example.chatdemo;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private LinearLayout ll_picture_select;
    private EditText et_chat;
    private ImageView iv, iv_image_select;
    private List<ChatBean> list;
    private RecyclerView mRecyclerView;
    private RelativeLayout rl;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initListener();
        if ((int) iv.getTag() == 1) {
            iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (ll_picture_select.getVisibility() == View.GONE) {
                        ll_picture_select.setVisibility(View.VISIBLE);
                        //软键盘打开的情况，隐藏键盘
                        hideSoftKeyBoard();
                        //ll_picture_select显示的时候，不获取焦点
                        et_chat.setFocusable(false);
                        et_chat.setFocusableInTouchMode(false);
                        et_chat.setCursorVisible(false);
                    } else {
                        ll_picture_select.setVisibility(View.GONE);
                    }

                }
            });
        }else {
            OnSendBtnClick(et_chat.getText().toString());
            et_chat.setText("");
        }
        if (iv_image_select.getVisibility() == View.VISIBLE) {
            iv_image_select.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                    } else {
                        jumpToAlbums();
                    }
                }
            });
        }
    }

    private void OnSendBtnClick(String msg) {
        if (!TextUtils.isEmpty(msg)) {
            /*ImMsgBean bean = new ImMsgBean();
            bean.setContent(msg);
            chattingListAdapter.addData(bean, true, false);*/
            scrollToBottom();
        }
    }

    //第一种方法：
    //mRecycleView.smoothScrollBy(distance,duration);
    //第二种方法：
    //mRecycleView.smoothScrollOffset(offset);
    //第三种方法：
    //mRecycleView.smoothToPosition(index);
    private void scrollToBottom(){
        mRecyclerView.requestLayout();
        mRecyclerView.post(new Runnable() {
            @Override
            public void run() {
                mRecyclerView.scrollToPosition(rl.getBottom());
            }
        });
    }

    private void jumpToAlbums() {
        Intent intent = new Intent();
        if (Build.VERSION.SDK_INT < 19) {
            intent.setAction(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");
        } else {
            intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        }
        startActivityForResult(intent, 1);
    }


    private void initListener() {
        et_chat.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                if (!TextUtils.isEmpty(s) && s != null) {
                    iv.setImageDrawable(getResources().getDrawable(R.mipmap.icon_send));
                } else {
                    iv.setImageDrawable(getResources().getDrawable(R.mipmap.icon_add));
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        et_chat.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    if (ll_picture_select.getVisibility() == View.VISIBLE ) {
                        ll_picture_select.setVisibility(View.GONE);
                    }
                }
            }
        });
        et_chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                et_chat.setFocusable(true);
                et_chat.setFocusableInTouchMode(true);
                et_chat.setCursorVisible(true);
                et_chat.requestFocus();
                showSoftKeyBoard();
            }
        });
        rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ll_picture_select.getVisibility() == View.VISIBLE ) {
                    ll_picture_select.setVisibility(View.GONE);
                }
                if (isShowing()){
                    hideSoftKeyBoard();
                    et_chat.setFocusable(false);
                    et_chat.setFocusableInTouchMode(false);
                    et_chat.setCursorVisible(false);
                }
            }
        });
    }

    private void initView() {
        list = new ArrayList<>();
        et_chat = findViewById(R.id.et_chat);
        iv = findViewById(R.id.iv);
        ll_picture_select = findViewById(R.id.ll_picture_select);
        iv_image_select = findViewById(R.id.iv_image_select);
        mRecyclerView = findViewById(R.id.rv);
        rl = findViewById(R.id.rl);
        iv.setImageDrawable(getResources().getDrawable(R.mipmap.icon_add));
        iv.setTag(1);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            Uri uri = data.getData();
            Log.e("zzf","uri = " + uri+"");
            String imagePath = UriUtils.getPicturePathFromUri(MainActivity.this, uri);
            Log.e("zzf","imagePath =" + imagePath+"");

        }
    }

    private void praseImage(final String imagePath) {
        AsyncTask myTask = new AsyncTask<String, Integer, Bitmap>() {
            @Override
            protected Bitmap doInBackground(String... params) {
                // 解析二维码/条码
                Bitmap bitmap = QRCodeDecoder.getDecodeAbleBitmap(imagePath);
                return bitmap;
            }

            @Override
            protected void onPostExecute(Bitmap bitmap) {
                super.onPostExecute(bitmap);
                ChatBean chatBean = new ChatBean();
                chatBean.setBitmap(bitmap);
                list.add(chatBean);
            }
        }.execute(imagePath);
    }

    // 隐藏输入法
    public void hideSoftKeyBoard() {
        InputMethodManager imm = (InputMethodManager) this.getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view = this.getCurrentFocus();
        if (view != null) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    //显示输入法
    public void showSoftKeyBoard() {
        InputMethodManager imm = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }


    //判断
    private boolean isShowing() {
        // 获取当前屏幕内容的高度
        int screenHeight = getWindow().getDecorView().getHeight();
        // 获取View可见区域的bottom
        Rect rect = new Rect();
        getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);

        return screenHeight > rect.bottom + getNavigatorBarHeight();
    }

    //当手机有导航栏的时候，判断软键盘是否显示，需要测量导航栏的高度
    private int getNavigatorBarHeight() {
        int resourceId = getResources().getIdentifier("navigation_bar_height", "dimen", "android");
        int height = getResources().getDimensionPixelSize(resourceId);
        return height;
    }


    //这种方法则不需要判定导航栏的高度，但这并不精确,因为它是取屏幕高度的2/3，有一种投机取巧的感觉
    private boolean isSoftShowing() {
        // 获取当前屏幕内容的高度
        int screenHeight = getWindow().getDecorView().getHeight();
        // 获取View可见区域的bottom
        Rect rect = new Rect();
        // DecorView即为activity的顶级view
        getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
        // 考虑到虚拟导航栏的情况（虚拟导航栏情况下：screenHeight = rect.bottom + 虚拟导航栏高度）
        // 选取screenHeight*2/3进行判断
        return screenHeight*2/3 > rect.bottom;
    }


}
