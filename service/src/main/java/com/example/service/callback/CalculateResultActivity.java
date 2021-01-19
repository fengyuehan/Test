package com.example.service.callback;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.service.Logger;
import com.example.service.ProgressUtils;
import com.example.service.R;
import com.example.service.StringUtils;
import com.example.service.ToastUtils;

/**
 * author : zhangzf
 * date   : 2021/1/19
 * desc   :
 */
public class CalculateResultActivity extends AppCompatActivity {
    private Button btCallbackBind;
    private EditText etNum1;
    private TextView tvOperator;
    private EditText etNum2;
    private TextView tvResult;
    private Button btOperatorAdd;
    private Button btOperatorSubtract;
    private Button btOperatorMultiply;
    private Button btOperatorDivide;
    private Button btCallbackUnbind;

    private ICalculateResultBinder iCalculateResultBinder;
    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            iCalculateResultBinder = ICalculateResultBinder.Stub.asInterface(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            iCalculateResultBinder = null;
        }
    };
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calculate_result_activity);
        init();
        initListener();
    }

    private void initListener() {
        // 绑定服务
        btCallbackBind.setOnClickListener(v -> {
            Logger.i("Click Binder Service " + currentProgressAndThread());
            Intent intent = new Intent();
            intent.setAction("com.renj.remote.callback");
            intent.setPackage("com.example.service");
            bindService(intent, connection, Service.BIND_AUTO_CREATE);
        });

        // 加法运算
        btOperatorAdd.setOnClickListener(v -> {
            if (check()) return;

            tvOperator.setText("+");
            double num1 = getDoubleFromEditText(etNum1);
            double num2 = getDoubleFromEditText(etNum2);
            try {
                iCalculateResultBinder.operatorAdd(num1, num2, new ICalculateResultCallBack.Stub() {
                    @Override
                    public void result(double result) throws RemoteException {
                        Logger.i("收到计算结果 加法: " + num1 + " + " + num2 + " = " + result + currentProgressAndThread());
                        tvResult.setText(result + "");
                    }
                });
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        });

        // 减法运算
        btOperatorSubtract.setOnClickListener(v -> {
            if (check()) return;

            tvOperator.setText("-");
            double num1 = getDoubleFromEditText(etNum1);
            double num2 = getDoubleFromEditText(etNum2);
            try {
                iCalculateResultBinder.operatorSubtract(num1, num2, new ICalculateResultCallBack.Stub() {
                    @Override
                    public void result(double result) throws RemoteException {
                        Logger.i("收到计算结果 减法: " + num1 + " - " + num2 + " = " + result + currentProgressAndThread());
                        tvResult.setText(result + "");
                    }
                });
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        });

        // 乘法运算
        btOperatorMultiply.setOnClickListener(v -> {
            if (check()) return;

            tvOperator.setText("*");
            double num1 = getDoubleFromEditText(etNum1);
            double num2 = getDoubleFromEditText(etNum2);
            try {
                iCalculateResultBinder.operatorMultiply(num1, num2, new ICalculateResultCallBack.Stub() {
                    @Override
                    public void result(double result) throws RemoteException {
                        Logger.i("收到计算结果 乘法: " + num1 + " * " + num2 + " = " + result + currentProgressAndThread());
                        tvResult.setText(result + "");
                    }
                });
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        });

        // 除法运算
        btOperatorDivide.setOnClickListener(v -> {
            if (check()) return;

            tvOperator.setText("/");
            double num1 = getDoubleFromEditText(etNum1);
            double num2 = getDoubleFromEditText(etNum2);
            if (num2 == 0) {
                ToastUtils.showToast("被除数不能为0");
                return;
            }
            try {
                iCalculateResultBinder.operatorDivide(num1, num2, new ICalculateResultCallBack.Stub() {
                    @Override
                    public void result(double result) throws RemoteException {
                        Logger.i("收到计算结果 除法: " + num1 + " / " + num2 + " = " + result + currentProgressAndThread());
                        tvResult.setText(result + "");
                    }
                });
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        });

        // 解绑服务
        btCallbackUnbind.setOnClickListener(v -> {
            if (check()) return;

            Logger.i("Click UnBinder Service ");
            Intent intent = new Intent();
            intent.setAction("com.renj.remote.callback");
            intent.setPackage("com.renj.service");
            unbindService(connection);
            iCalculateResultBinder = null;
        });
    }

    private void init() {
        btCallbackBind = findViewById(R.id.bt_callback_bind);
        etNum1 = findViewById(R.id.et_num1);
        tvOperator = findViewById(R.id.tv_operator);
        etNum2 = findViewById(R.id.et_num2);
        tvResult = findViewById(R.id.tv_result);
        btOperatorAdd = findViewById(R.id.bt_operator_add);
        btOperatorSubtract = findViewById(R.id.bt_operator_subtract);
        btOperatorMultiply = findViewById(R.id.bt_operator_multiply);
        btOperatorDivide = findViewById(R.id.bt_operator_divide);
        btCallbackUnbind = findViewById(R.id.bt_callback_unbind);
    }

    private boolean check() {
        if (iCalculateResultBinder == null) {
            ToastUtils.showToast("服务未绑定或已解绑");
            return true;
        }
        if (StringUtils.isEmpty(etNum1.getText().toString().trim())
                || StringUtils.isEmpty(etNum2.getText().toString().trim())) {
            ToastUtils.showToast("请输入内容");
            return true;
        }
        return false;
    }

    private String currentProgressAndThread() {
        return " ,Progress Name: " + ProgressUtils.getProcessName(this) + " ,Thread Name： " + Thread.currentThread().getName();
    }

    private double getDoubleFromEditText(EditText editText) {
        String value = editText.getText().toString().trim();
        try {
            return Double.parseDouble(value);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return 0;
        }
    }
}
