package com.example.dialogfragment;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import androidx.core.app.DialogFragment;
import androidx.appcompat.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

public class EditNameDialogFragment extends DialogFragment {
    //使用DialogFragment可以不受屏幕旋转的影响
    //因为DialogFragment和Fragment基本一致的生命周期，当屏幕旋转导致Activity的生命周期会重新调用，
    // 此时AlertDialog会消失，如果处理不当很可能引发异常，而DialogFragment对话框会随之自动调整对话框方向，DialogFragment的出现完美的解决了横竖屏幕切换Dialog消失的问题，同时也有恢复数据的功能。

    private EditText userName;
    private EditText passWord;
    /*@Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        return inflater.inflate(R.layout.dialog_edit,container);
    }*/

    public interface LoginInputListener{
        void onLoginInputComplete(String mUserName,String mPassWord);
    }
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater mLayoutInflater = getActivity().getLayoutInflater();
        View view = mLayoutInflater.inflate(R.layout.dialog_login, null);
        userName = view.findViewById(R.id.id_txt_username);
        passWord = view.findViewById(R.id.id_txt_password);
        builder.setView(view)
                .setPositiveButton("登录", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        LoginInputListener loginInputListener = (LoginInputListener) getActivity();
                        if ( loginInputListener != null){
                            loginInputListener.onLoginInputComplete(userName.getText().toString(),passWord.getText().toString());
                        }

                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        userName.setText("");
                        passWord.setText("");
                        LoginInputListener loginInputListener = (LoginInputListener) getActivity();
                        if ( loginInputListener != null){
                            loginInputListener.onLoginInputComplete(userName.getText().toString(),passWord.getText().toString());
                        }
                    }
        });
        return builder.create();
    }
}
