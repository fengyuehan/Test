package com.example.fingerprintdemo;

import android.app.Activity;
import android.app.KeyguardManager;
import android.content.Context;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.CancellationSignal;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;

public class BiometricPromptManager {
    private FingerprintImpl mFingerprintImpl;
    private Activity mActivity;

    @RequiresApi(api = Build.VERSION_CODES.M)
    public static BiometricPromptManager getInstance(Activity mActivity) {
        return new BiometricPromptManager(mActivity);
    }


    public BiometricPromptManager(Activity activity) {
        mActivity = activity;
        if (isApi28()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                mFingerprintImpl = new FingerprintApi28(activity);
            }
        } else if (isApi23()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                mFingerprintImpl = new FingerprintApi23(activity);
            }
        }
    }

    private boolean isApi28() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            return true;
        }
        return false;
    }

    private boolean isApi23() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return true;
        }
        return false;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void authenticate(boolean loginFlg, @NonNull FingerprintIdentifyCallback callback) {
        mFingerprintImpl.authenticate(loginFlg, new CancellationSignal(), callback);
    }

    //判断至少是否有一个指纹
    public boolean hasEnrolledFingerprints() {
        if (isApi28()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                //TODO 这是Api23的判断方法，也许以后有针对Api28的判断方法
                final FingerprintManager manager = mActivity.getSystemService(FingerprintManager.class);
                return manager != null && manager.hasEnrolledFingerprints();
            }
        } else if (isApi23()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                return new FingerprintApi23(mActivity).getInstance().hasEnrolledFingerprints();
            }
        }
        return false;
    }

    //判断硬件是否支持
    public boolean isHardwareDetected() {
        if (isApi28()) {
            //TODO 这是Api23的判断方法，也许以后有针对Api28的判断方法
            final FingerprintManager fm;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                fm = mActivity.getSystemService(FingerprintManager.class);
                return fm != null && fm.isHardwareDetected();
            }
        } else if (isApi23()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                return new FingerprintApi23(mActivity).getInstance() .isHardwareDetected();
            }
        }
        return false;
    }

    //当前系统是否处于锁屏状态
    public boolean isKeyguardSecure() {
        KeyguardManager keyguardManager = (KeyguardManager) mActivity.getSystemService(Context.KEYGUARD_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            if (keyguardManager.isKeyguardSecure()) {
                return true;
            }
        }
        return false;
    }

    public boolean isBiometricPromptEnable() {
        return isApi23()
                && isHardwareDetected()
                && hasEnrolledFingerprints()
                && isKeyguardSecure();
    }
}
