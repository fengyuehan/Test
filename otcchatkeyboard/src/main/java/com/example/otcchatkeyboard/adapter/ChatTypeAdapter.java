package com.example.otcchatkeyboard.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.otcchatkeyboard.ChrysanthemumView;
import com.example.otcchatkeyboard.R;
import com.example.otcchatkeyboard.bean.MsgBean;

import java.util.List;

public class ChatTypeAdapter extends BaseMultiItemQuickAdapter<MsgBean, BaseViewHolder> {
    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */

    private List<MsgBean> data;
    private Context mContext;

    public ChatTypeAdapter(List<MsgBean> data, Context mContext) {
        super(data);
        this.data = data;
        this.mContext = mContext;
        addItemType(MsgBean.CHAT_MSGTYPE_TEXT, R.layout.chat_text_left);
        addItemType(MsgBean.CHAT_MSGTYPE_IMG, R.layout.chat_image_left);
        addItemType(MsgBean.CHAT_SENDER_TEXT, R.layout.chat_text_right);
        addItemType(MsgBean.CHAT_SENDER_IMG, R.layout.chat_image_left);
    }

    @Override
    protected void convert(BaseViewHolder helper, MsgBean item) {
        switch (helper.getItemViewType()) {
            case MsgBean.CHAT_MSGTYPE_TEXT:
                helper.setText(R.id.tv_content, item.getContent())
                        .setImageResource(R.id.iv_avatar, R.mipmap.icon_avatar);
                ChrysanthemumView chrysanthemumView = helper.getView(R.id.iv_tips);
                chrysanthemumView.startAnimation();
                break;
            case MsgBean.CHAT_MSGTYPE_IMG:
                helper.setImageResource(R.id.iv_avatar, R.mipmap.icon_avatar);
                Glide.with(mContext)
                        .load(item.getContent())
                        .into((ImageView) helper.getView(R.id.iv_image));
                break;
            case MsgBean.CHAT_SENDER_TEXT:
                helper.setText(R.id.tv_content, item.getContent())
                        .setImageResource(R.id.iv_avatar, R.mipmap.icon_avatar);
                break;
            case MsgBean.CHAT_SENDER_IMG:
                helper.setImageResource(R.id.iv_avatar, R.mipmap.icon_avatar);
                Glide.with(mContext)
                        .load(item.getContent())
                        .into((ImageView) helper.getView(R.id.iv_image));
                break;
        }
    }

    public void addData(MsgBean msgBean) {
        data.add(msgBean);
        notifyItemChanged(data.size() - 1);
    }
}
