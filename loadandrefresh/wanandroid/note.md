1、在项目中采用百分比屏幕适配方法，具体步骤为：

   （1）以某一分辨率为基准，生成所有分辨率对应像素数列表
   （2）将生成像素数列表存放在res目录下对应的values文件下
   （3）根据UI设计师给出设计图上的尺寸，找到对应像素数的单位，然后设置给控件即可


2、今日头条适配方案

 /**
     * 头条适配方案 修改设备密度
     * 支持以宽或者高一个维度去适配，保持该维度上和设计图一致；
     * 当前设备总宽度（单位为像素）/ 设计图总宽度（单位为 dp) = density
     * density 的意思就是 1 dp 占当前设备多少像素
     * 在BaseActivity中引用即可
     */
     private static float sNoncompatDensity;
     private static float sNoncompatScaledDensity;

     public static void setCustomDensity(@NonNull Activity activity, @NonNull final Application application) {
         DisplayMetrics appDisplayMetrics = application.getResources().getDisplayMetrics();
         if (sNoncompatDensity == 0) {
             sNoncompatDensity = appDisplayMetrics.density;
             sNoncompatScaledDensity = appDisplayMetrics.scaledDensity;
             // 防止系统切换后不起作用
             application.registerComponentCallbacks(new ComponentCallbacks() {
                 @Override
                 public void onConfigurationChanged(Configuration newConfig) {
                     if (newConfig != null && newConfig.fontScale > 0) {
                         sNoncompatScaledDensity = application.getResources().getDisplayMetrics().scaledDensity;
                     }
                 }

                 @Override
                 public void onLowMemory() {

                 }
             });
         }
         float targetDensity = appDisplayMetrics.widthPixels / 360;
         // 防止字体变小
         float targetScaleDensity = targetDensity * (sNoncompatScaledDensity / sNoncompatDensity);
         int targetDensityDpi = (int) (160 * targetDensity);

         appDisplayMetrics.density = targetDensity;
         appDisplayMetrics.scaledDensity = targetScaleDensity;
         appDisplayMetrics.densityDpi = targetDensityDpi;

         final DisplayMetrics activityDisplayMetrics = activity.getResources().getDisplayMetrics();
         activityDisplayMetrics.density = targetDensity;
         activityDisplayMetrics.scaledDensity = targetScaleDensity;
         activityDisplayMetrics.densityDpi = targetDensityDpi;
     }

    然后在BaseActivity的onCreate方法中引用：
    DisplayUtil.setCustomDensity(this, MyApplication.getInstance());