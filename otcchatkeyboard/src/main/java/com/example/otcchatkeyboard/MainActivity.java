package com.example.otcchatkeyboard;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.otcchatkeyboard.adapter.ChatAdapter;
import com.example.otcchatkeyboard.adapter.ChatTypeAdapter;
import com.example.otcchatkeyboard.bean.MsgBean;
import com.wanglu.photoviewerlibrary.PhotoViewer;
import com.blankj.utilcode.util.Utils;


import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import static android.support.v7.widget.RecyclerView.SCROLL_STATE_DRAGGING;
import static android.support.v7.widget.RecyclerView.SCROLL_STATE_SETTLING;
import static android.widget.NumberPicker.OnScrollListener.SCROLL_STATE_IDLE;

public class MainActivity extends AppCompatActivity implements FuncLayout.OnFuncKeyBoardListener {
    private OtcKeyBoard mOtcKeyBoard;
    private RecyclerView mRecyclerView;
    private ChatTypeAdapter mChatTypeAdapter;
    private String imagePath;
    private LinearLayoutManager mLinearLayoutManager;
    private List<MsgBean> beanList;
    private ChatDialog mChatDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initOtcKeyBoard();
        initRecyclerView();
    }

    private void initRecyclerView() {
        beanList = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            MsgBean bean = new MsgBean();
            bean.setContent("Test:" + i);
            bean.setMsgType(MsgBean.CHAT_MSGTYPE_TEXT);
            beanList.add(bean);
        }
        mLinearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mChatTypeAdapter = new ChatTypeAdapter(beanList, this);
        mRecyclerView.setAdapter(mChatTypeAdapter);
        mRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                switch (newState) {
                    //静止没有滚动
                    case SCROLL_STATE_IDLE:
                        break;
                    //手指滚动
                    case SCROLL_STATE_SETTLING:
                        mOtcKeyBoard.reset();
                        break;
                    //自动滚动
                    case SCROLL_STATE_DRAGGING:
                        break;
                }
            }
        });

        mChatTypeAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (mChatTypeAdapter.getItemViewType(position) == MsgBean.CHAT_MSGTYPE_IMG) {
                    /*PhotoViewer.INSTANCE.setClickView(view)
                            .setShowImageViewInterface(new PhotoViewer.ShowImageViewInterface() {
                                @Override
                                public void show(@NotNull ImageView imageView, @NotNull String s) {
                                    Glide.with(MainActivity.this).load(imagePath).into(imageView);
                                }
                            }).start(MainActivity.this);*/
                    PhotoViewer.INSTANCE.setClickSingleImg(imagePath, view)
                            .setShowImageViewInterface(new PhotoViewer.ShowImageViewInterface() {
                                @Override
                                public void show(@NotNull ImageView imageView, @NotNull String s) {
                                    Glide.with(MainActivity.this).load(s).into(imageView);
                                }
                            })
                            .showSave(false)
                            .start(MainActivity.this);
                }
            }
        });
        mChatTypeAdapter.setOnItemLongClickListener(new BaseQuickAdapter.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(BaseQuickAdapter adapter, View view, final int position) {
                if (mChatTypeAdapter.getItemViewType(position) == MsgBean.CHAT_MSGTYPE_TEXT) {
                    copyText(beanList.get(position).getContent());
                    /*if (mChatDialog == null){
                        mChatDialog = new ChatDialog(MainActivity.this, new ChatDialog.CallBack() {
                            @Override
                            public void callBack() {

                            }
                        });
                    }
                    mChatDialog.show();*/
                }
                return true;
            }
        });
    }

    private void copyText(String content) {
        ClipboardManager cm = (ClipboardManager) Utils.getApp().getSystemService(Context.CLIPBOARD_SERVICE);
        cm.setPrimaryClip(ClipData.newPlainText("text", content));
        Toast.makeText(MainActivity.this, "复制成功", Toast.LENGTH_SHORT).show();


    }


    private void initOtcKeyBoard() {
        mOtcKeyBoard.addOnFuncKeyBoardListener(this);
        mOtcKeyBoard.addFuncView(new SimpleAppsGridView(this, this));
        mOtcKeyBoard.getEtChat().setOnSizeChangedListener(new OtcEditText.OnSizeChangedListener() {
            @Override
            public void onSizeChanged(int w, int h, int oldw, int oldh) {
                scrollToBottom();
            }
        });
        mOtcKeyBoard.setmOnEtChatListener(new OtcKeyBoard.OnEtChatListener() {
            @Override
            public void etChatListener(String message) {
                MsgBean msgBean = new MsgBean();
                msgBean.setContent(message);
                msgBean.setMsgType(MsgBean.CHAT_MSGTYPE_TEXT);
                mChatTypeAdapter.addData(msgBean);
                scrollToBottom();
            }
        });
    }

    private void scrollToBottom() {
        if (beanList == null) {
            return;
        }
        if (beanList.size() == 0) {
            mLinearLayoutManager.scrollToPositionWithOffset(beanList.size(), 0);
        } else {
            mLinearLayoutManager.scrollToPositionWithOffset(beanList.size() - 1, 0);
        }
    }

    private void initView() {
        mOtcKeyBoard = findViewById(R.id.okb);
        mRecyclerView = findViewById(R.id.rv);
    }

    @Override
    public void OnFuncPop(int height) {
        scrollToBottom();
    }

    @Override
    public void OnFuncClose() {

    }

    @Override
    protected void onPause() {
        super.onPause();
        mOtcKeyBoard.reset();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            Uri uri = data.getData();
            imagePath = UriUtils.getPicturePathFromUri(MainActivity.this, uri);
            Log.e("zzf", "imagePath =" + imagePath + "");
            MsgBean msgBean = new MsgBean();
            msgBean.setMsgType(MsgBean.CHAT_MSGTYPE_IMG);
            msgBean.setContent(imagePath);
            mChatTypeAdapter.addData(msgBean);
            scrollToBottom();
        }
    }
}
