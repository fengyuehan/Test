// ICalculateResultBinder.aidl
package com.example.service.callback;
import com.example.service.callback.ICalculateResultCallBack;
// Declare any non-default types here with import statements

interface ICalculateResultBinder {
    void operatorAdd(double num1, double num2, ICalculateResultCallBack iCalculateResultCallBack);

        void operatorSubtract(double num1, double num2, ICalculateResultCallBack iCalculateResultCallBack);

        void operatorMultiply(double num1, double num2, ICalculateResultCallBack iCalculateResultCallBack);

        void operatorDivide(double num1, double num2, ICalculateResultCallBack iCalculateResultCallBack);
}