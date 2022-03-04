package com.example.otcchatkeyboard.adapter;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.otcchatkeyboard.ImageBase;
import com.example.otcchatkeyboard.ImageLoadUtils;
import com.example.otcchatkeyboard.R;
import com.example.otcchatkeyboard.bean.MsgBean;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class ChatAdapter extends RecyclerView.Adapter {

    private final int VIEW_TYPE_LEFT_TEXT = 0;
    private final int VIEW_TYPE_LEFT_IMAGE = 1;

    private Context mContext;
    private List<MsgBean> datas;

    public ChatAdapter(Context mContext, List<MsgBean> datas) {
        this.mContext = mContext;
        this.datas = datas;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        RecyclerView.ViewHolder viewHolder = null;
        int type = getItemViewType(position);
        switch (type) {
            case VIEW_TYPE_LEFT_TEXT:
                viewHolder = new ChatTextViewHolder(View.inflate(mContext, R.layout.chat_text_left, null));
                break;
            case VIEW_TYPE_LEFT_IMAGE:
                viewHolder = new ChatTextViewHolder(View.inflate(mContext, R.layout.chat_image_left, null));
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        if (viewHolder instanceof ChatTextViewHolder) {
            ChatTextViewHolder chatTextViewHolder = (ChatTextViewHolder) viewHolder;
            if (datas.get(position) != null&& chatTextViewHolder.tv_content != null){
                chatTextViewHolder.tv_content.setText(datas.get(position).getContent());
            }
        } else if (viewHolder instanceof ChatImageViewHolder) {
            ChatImageViewHolder chatImageViewHolder = (ChatImageViewHolder) viewHolder;
            disPlayLeftImageView(position, chatImageViewHolder, datas);
        }
    }

    private void disPlayLeftImageView(int position, ChatImageViewHolder chatImageViewHolder, List<MsgBean> datas) {
        try {
            if (ImageBase.Scheme.FILE == ImageBase.Scheme.ofUri(datas.get(position).getImage())) {
                String filePath = ImageBase.Scheme.FILE.crop(datas.get(position).getImage());
                Glide.with(chatImageViewHolder.iv_image.getContext())
                        .load(filePath)
                        .into(chatImageViewHolder.iv_image);
            } else {
                ImageLoadUtils.getInstance(mContext).displayImage(datas.get(position).getImage(), chatImageViewHolder.iv_image);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public int getItemCount() {
        return datas.size() == 0 ? 0 : datas.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (datas.get(position) == null) {
            return -1;
        }
        return datas.get(position).getMsgType() == MsgBean.CHAT_MSGTYPE_TEXT ? VIEW_TYPE_LEFT_TEXT : VIEW_TYPE_LEFT_IMAGE;
    }

    class ChatTextViewHolder extends RecyclerView.ViewHolder {

        private ImageView iv_avatar;
        private TextView tv_content;

        public ChatTextViewHolder(View itemView) {
            super(itemView);
            iv_avatar = itemView.findViewById(R.id.iv_avatar);
            tv_content = itemView.findViewById(R.id.tv_content);
        }
    }

    class ChatImageViewHolder extends RecyclerView.ViewHolder {
        private ImageView iv_avatar, iv_image;

        public ChatImageViewHolder(View itemView) {
            super(itemView);
            iv_avatar = itemView.findViewById(R.id.iv_avatar);
            iv_image = itemView.findViewById(R.id.iv_image);
        }
    }

    public void addData(List<MsgBean> data) {
        if (data == null || data.size() == 0) {
            return;
        }
        if (datas == null) {
            datas = new ArrayList<>();
        }
        for (MsgBean bean : data) {
            addData(bean, false, false);
        }
        notifyDataSetChanged();
    }

    public void addData(MsgBean bean, boolean isNotifyDataSetChanged, boolean isFromHead) {
        if (bean == null) {
            return;
        }

        if (datas == null) {
            datas = new ArrayList<>();
        }

        if (bean.getMsgType() <= 0) {
            String content = bean.getContent();
            if (content != null) {
                if (content.indexOf("[img]") >= 0) {
                    bean.setImage(content.replace("[img]", ""));
                    bean.setMsgType(MsgBean.CHAT_MSGTYPE_IMG);
                } else {
                    bean.setMsgType(MsgBean.CHAT_MSGTYPE_TEXT);
                }
            }
        }

        if (isFromHead) {
            datas.add(0, bean);
        } else {
            datas.add(bean);
        }

        if (isNotifyDataSetChanged) {
            this.notifyDataSetChanged();
        }
    }
}
