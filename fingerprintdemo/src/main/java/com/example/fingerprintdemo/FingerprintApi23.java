package com.example.fingerprintdemo;

import android.app.Activity;
import android.content.Context;
import android.hardware.biometrics.BiometricPrompt;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.CancellationSignal;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.util.Base64;

@RequiresApi(api = Build.VERSION_CODES.M)
public class FingerprintApi23 implements FingerprintImpl {

    private Activity mActivity;
    private FingerprintDialog mFingerprintDialog;
    private FingerprintManager mFingerprintManager;
    private CancellationSignal mCancellationSignal;
    private FingerprintIdentifyCallback mFingerprintIdentifyCallback;
    private FingerprintManager.AuthenticationCallback mAuthenticationCallback  = new FingerprintManageCallbackImpl();
    private ACache mACache;
    private FingerprintManageCallbackImpl mFingerprintManageCallbackImpl;

    public FingerprintApi23(Activity activity) {
        mActivity = activity;
        mACache = ACache.get(MyApplication.getContext());
        mFingerprintManager = getFingerprintManager(activity);
    }

    private FingerprintManager getFingerprintManager(Context context) {
        if (mFingerprintManager == null) {
            mFingerprintManager = context.getSystemService(FingerprintManager.class);
        }
        return mFingerprintManager;
    }

    public FingerprintManageCallbackImpl getInstance(){
        if (mFingerprintManageCallbackImpl == null){
            mFingerprintManageCallbackImpl = new FingerprintManageCallbackImpl();
        }
        return mFingerprintManageCallbackImpl;
    }
    @Override
    public void authenticate(boolean loginFlg, @NonNull CancellationSignal cancel, @NonNull FingerprintIdentifyCallback callback) {
        mFingerprintIdentifyCallback = callback;
        mCancellationSignal = cancel;
        if (mCancellationSignal == null) {
            mCancellationSignal = new CancellationSignal();
        }
        mFingerprintDialog = FingerprintDialog.getInstance();
        mFingerprintDialog.setmOnDialogCallback(new OnDialogCallback() {
            @Override
            public void onSure() {
                if (mFingerprintIdentifyCallback != null){
                    mFingerprintIdentifyCallback.onCancel();
                }
            }

            @Override
            public void onCancel() {
                if (mFingerprintIdentifyCallback != null){
                    mFingerprintIdentifyCallback.onCancel();
                }
            }

            @Override
            public void onDialogDismiss() {
                if (mCancellationSignal != null && !mCancellationSignal.isCanceled()){
                    mCancellationSignal.cancel();
                }
            }
        });
        mFingerprintDialog.show(mActivity.getFragmentManager(),"BiometricPromptApi23");
        mCancellationSignal.setOnCancelListener(new CancellationSignal.OnCancelListener() {
            @Override
            public void onCancel() {
                mFingerprintDialog.dismiss();
            }
        });


        KeyGenTool mKeyGenTool = new KeyGenTool(mActivity);
        FingerprintManager.CryptoObject object;

        if (loginFlg){
            //解密
            try {
                /**
                 * 可通过服务器保存iv,然后在使用之前从服务器获取
                 */
                String ivStr = mACache.getAsString("iv");
                byte[] iv = Base64.decode(ivStr, Base64.URL_SAFE);

                object = new FingerprintManager.CryptoObject(mKeyGenTool.getDecryptCipher(iv));
                getFingerprintManager(mActivity).authenticate(object, mCancellationSignal,
                        0, mAuthenticationCallback, null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else {
            //加密
            try {
                object = new FingerprintManager.CryptoObject(mKeyGenTool.getEncryptCipher());
                getFingerprintManager(mActivity).authenticate(object, mCancellationSignal,
                        0, mAuthenticationCallback, null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    public class FingerprintManageCallbackImpl extends FingerprintManager.AuthenticationCallback{
        @Override
        public void onAuthenticationError(int errorCode, CharSequence errString) {
            super.onAuthenticationError(errorCode, errString);
            mFingerprintDialog.setState(FingerprintDialog.STATE_ERROR);
            mFingerprintIdentifyCallback.onError(errorCode, errString.toString());
        }

        @Override
        public void onAuthenticationFailed() {
            super.onAuthenticationFailed();

            mFingerprintDialog.setState(FingerprintDialog.STATE_FAILED);
            mFingerprintIdentifyCallback.onFailed();
        }

        @Override
        public void onAuthenticationHelp(int helpCode, CharSequence helpString) {
            super.onAuthenticationHelp(helpCode, helpString);
        }

        @Override
        public void onAuthenticationSucceeded(FingerprintManager.AuthenticationResult result) {
            super.onAuthenticationSucceeded(result);
            mFingerprintDialog.setState(FingerprintDialog.STATE_SUCCEED);
            mFingerprintIdentifyCallback.onSucceeded(result);
        }

        public boolean isHardwareDetected() {
            if (mFingerprintManager != null) {
                return mFingerprintManager.isHardwareDetected();
            }
            return false;
        }

        public boolean hasEnrolledFingerprints() {
            if (mFingerprintManager != null) {
                return mFingerprintManager.hasEnrolledFingerprints();
            }
            return false;
        }
    }
}
