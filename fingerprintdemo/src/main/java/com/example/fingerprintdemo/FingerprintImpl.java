package com.example.fingerprintdemo;

import android.os.CancellationSignal;
import android.support.annotation.NonNull;

public interface FingerprintImpl {
    void authenticate(boolean loginFlg, @NonNull CancellationSignal cancel,
                      @NonNull FingerprintIdentifyCallback callback);


}
