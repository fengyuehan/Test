package com.example.lettersidebar;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;


/**
 * @author zzf
 * @date 2019/7/29/029
 * 描述：
 */
public class ContactWithSearchAdapter extends BaseQuickAdapter<ConstactBean,BaseViewHolder> {

    public ContactWithSearchAdapter(@Nullable List<ConstactBean> data) {
        super(R.layout.item_contact, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ConstactBean item) {
        helper.setText(R.id.tv_memo, item.memo)
                .setText(R.id.tv_account_name, item.account_name);
        ((StrAvatarImageView) helper.getView(R.id.siv)).setText(item.memo.substring(0, 1));
    }
}
