package com.example.fingerprintdemo;

import android.hardware.biometrics.BiometricPrompt;

public interface FingerprintIdentifyCallback {

    void onUsePassword();

    void onSucceeded(android.hardware.fingerprint.FingerprintManager.AuthenticationResult result);

    void onSucceeded(BiometricPrompt.AuthenticationResult result);

    void onFailed();

    void onError(int code, String reason);

    void onCancel();
}
