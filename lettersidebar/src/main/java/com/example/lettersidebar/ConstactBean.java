package com.example.lettersidebar;

import android.text.TextUtils;

import net.sourceforge.pinyin4j.PinyinHelper;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author zzf
 * @date 2019/7/25/025
 * 描述：
 */
@Entity
public class ConstactBean {
    @Id(autoincrement = true)
    public Long id;
    public String memo;//备注
    public String account_name;//账户名字
    public String memo_py;//拼音

    @Generated(hash = 833766062)
    public ConstactBean(Long id, String memo, String account_name, String memo_py) {
        this.id = id;
        this.memo = memo;
        this.account_name = account_name;
        this.memo_py = memo_py;
    }

    public void setContact(String memo, String account_name) {
        this.memo = memo.trim();
        this.account_name = account_name.trim();
        memo_py = getPinYinFirstLetter(this.memo);
    }

    @Generated(hash = 985095275)
    public ConstactBean() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getMemo() {
        return this.memo;
    }
    public void setMemo(String memo) {
        this.memo = memo;
    }
    public String getAccount_name() {
        return this.account_name;
    }
    public void setAccount_name(String account_name) {
        this.account_name = account_name;
    }
    public String getMemo_py() {
        return this.memo_py;
    }
    public void setMemo_py(String memo_py) {
        this.memo_py = memo_py;
    }

    private String getPinYinFirstLetter(String var) {
        if (TextUtils.isEmpty(var)) {
            return "Z#";
        }
        char letter = var.charAt(0);
        if ((letter >= 'a' && letter <= 'z') || (letter >= 'A' && letter <= 'Z')) {
            return String.valueOf(letter).toUpperCase();
        } else if (checkChinese(var.substring(0, 1))) {
            String[] array = PinyinHelper.toHanyuPinyinStringArray(letter);
            if (array != null) {
                return String.valueOf(array[0].charAt(0)).toUpperCase();
            } else {
                return "Z#";
            }
        } else {
            return "Z#";
        }
    }

    private boolean checkChinese(String chinese) {
        Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
        Matcher m = p.matcher(chinese);
        return m.find();
    }
}
