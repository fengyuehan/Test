package com.example.buildsrc;

public class Config {

    public static final String OPTIMIZE_WEBP_CONVERT = "ConvertWebp";
    public static final String OPTIMIZE_COMPRESS_PICTURE = "Compress";

    public float maxSize = 1024 * 1024;
    public boolean isCheckSize = true; 
    public String optimizeType = OPTIMIZE_WEBP_CONVERT;
    public boolean enableWhenDebug = true;
    public boolean isCheckPixels = true;
    public int maxWidth = 1000;
    public int maxHeight = 1000;
    public String[] whiteList = new String[]{};
    public String mctoolsDir = "";
    public boolean isSupportAlphaWebp = false;
    public boolean multiThread = true;
    public String[] bigImageWhiteList = new String[]{};

    public void maxSize(float maxSize) {
        this.maxSize = maxSize;
    }

    public void isCheckSize(boolean check) {
        isCheckSize = check;
    }

    public void optimizeType(String optimizeType) {
        this.optimizeType = optimizeType;
    }

    public void isSupportAlphaWebp(boolean isSupportAlphaWebp) {
        this.isSupportAlphaWebp = isSupportAlphaWebp;
    }

    public void enableWhenDebug(boolean enableWhenDebug) {
        this.enableWhenDebug = enableWhenDebug;
    }

    public void isCheckPixels(boolean checkSize) {
        isCheckPixels = checkSize;
    }

    public void maxWidth(int maxWidth) {
        this.maxWidth = maxWidth;
    }

    public void maxHeight(int maxHeight) {
        this.maxHeight = maxHeight;
    }

    public void whiteList(String[] whiteList) {
        this.whiteList = whiteList;
    }

    public void mctoolsDir(String mctoolsDir) {
        this.mctoolsDir = mctoolsDir;
    }

    public void maxStroageSize(float maxSize) {
        this.maxSize = maxSize;
    }

    public void multiThread(boolean multiThread) {
        this.multiThread = multiThread;
    }

    public void bigImageWhiteList(String[] bigImageWhiteList) {
        this.bigImageWhiteList = bigImageWhiteList;
    }

    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("<<<<<<<<<<<<<<McConfig>>>>>>>>>>>>" + "\n");
        result.append("maxSize :" + maxSize + "\n"
                + "isCheckSize: " + isCheckSize + "\n"
                + "optimizeType: " + optimizeType + "\n"
                + "enableWhenDebug: " + enableWhenDebug + "\n"
                + "isCheckPixels: " + isCheckPixels + "\n"
                + "maxWidth: " + maxWidth + ", maxHeight: "  + maxHeight + "\n"
                + "mctoolsDir: " + mctoolsDir + "\n"
                + "isSupportAlphaWebp: " + isSupportAlphaWebp + "\n"
                + "multiThread: " + multiThread + "\n"
                + "whiteList : \n");
        for(String file : whiteList) {
            result.append("     -> : " + file + "\n");
        }
        result.append("bigImageWhiteList: \n");
        for(String file: bigImageWhiteList) {
            result.append("     -> : " + file + "\n");
        }
        result.append("<<<<<<<<<<<<<<<<<>>>>>>>>>>>>>>>>>");
        return result.toString();
    }
}
