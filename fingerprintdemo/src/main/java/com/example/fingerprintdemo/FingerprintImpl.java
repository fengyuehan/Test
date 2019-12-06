package com.example.fingerprintdemo;

import android.os.CancellationSignal;

import androidx.annotation.NonNull;

public interface FingerprintImpl {
    void authenticate(boolean loginFlg, @NonNull CancellationSignal cancel,
                      @NonNull FingerprintIdentifyCallback callback);


}
