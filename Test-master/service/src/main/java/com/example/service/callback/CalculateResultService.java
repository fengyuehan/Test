package com.example.service.callback;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

import androidx.annotation.Nullable;

import com.example.service.Logger;

import java.math.BigDecimal;

/**
 * author : zhangzf
 * date   : 2021/1/19
 * desc   :
 */
public class CalculateResultService extends Service {
    public static final String SERVICE_NAME = CalculateResultService.class.getSimpleName();
    private CalculateResultBinderImpl iCalculateResultBinder;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        iCalculateResultBinder = new CalculateResultBinderImpl();
        return iCalculateResultBinder;
    }


    private class CalculateResultBinderImpl extends ICalculateResultBinder.Stub{


        @Override
        public void operatorAdd(double num1, double num2, ICalculateResultCallBack iCalculateResultCallBack) throws RemoteException {
            BigDecimal bigDecimal1 = BigDecimal.valueOf(num1);
            BigDecimal bigDecimal2 = BigDecimal.valueOf(num2);
            BigDecimal result = bigDecimal1.add(bigDecimal2);
            Logger.i(SERVICE_NAME + " num1: " + num1 + " num2: " + num2 + " operatorAdd() result: " + result);
            if (iCalculateResultCallBack != null)
                iCalculateResultCallBack.result(result.doubleValue());
        }

        @Override
        public void operatorSubtract(double num1, double num2, ICalculateResultCallBack iCalculateResultCallBack) throws RemoteException {
            BigDecimal bigDecimal1 = BigDecimal.valueOf(num1);
            BigDecimal bigDecimal2 = BigDecimal.valueOf(num2);
            BigDecimal result = bigDecimal1.subtract(bigDecimal2);
            Logger.i(SERVICE_NAME + " num1: " + num1 + " num2: " + num2 + " operatorSubtract() result: " + result);
            if (iCalculateResultCallBack != null)
                iCalculateResultCallBack.result(result.doubleValue());
        }

        @Override
        public void operatorMultiply(double num1, double num2, ICalculateResultCallBack iCalculateResultCallBack) throws RemoteException {
            BigDecimal bigDecimal1 = BigDecimal.valueOf(num1);
            BigDecimal bigDecimal2 = BigDecimal.valueOf(num2);
            BigDecimal result = bigDecimal1.multiply(bigDecimal2);
            Logger.i(SERVICE_NAME + " num1: " + num1 + " num2: " + num2 + " operatorMultiply() result: " + result);
            if (iCalculateResultCallBack != null)
                iCalculateResultCallBack.result(result.doubleValue());
        }

        @Override
        public void operatorDivide(double num1, double num2, ICalculateResultCallBack iCalculateResultCallBack) throws RemoteException {
            if (num2 == 0) {
                Logger.i(SERVICE_NAME + " num1: " + num1 + " num2: " + num2 + " operatorDivide() Division by zero");
                throw new ArithmeticException("Division by zero");
            }
            BigDecimal bigDecimal1 = BigDecimal.valueOf(num1);
            BigDecimal bigDecimal2 = BigDecimal.valueOf(num2);
            BigDecimal result = bigDecimal1.divide(bigDecimal2, 2, BigDecimal.ROUND_HALF_UP);
            Logger.i(SERVICE_NAME + " num1: " + num1 + " num2: " + num2 + " operatorDivide() result: " + result );
            if (iCalculateResultCallBack != null)
                iCalculateResultCallBack.result(result.doubleValue());
        }
    }
}
