package com.example.otcchatkeyboard;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by pocketEos on 2017/11/23.
 * 高精度加減乘除
 * 数据处理工具类
 */

public class DataProcess {
    public static final Map<String, String> CURRENCY_NAME_CODE_PAIRS = new HashMap<>(22);
    private static DecimalFormat mFourDecimalFormat = new DecimalFormat("0.0000");
    private static DecimalFormat mTwoDecimalFormat = new DecimalFormat("0.00");

    static {
        CURRENCY_NAME_CODE_PAIRS.put("澳大利亚元", "AUD");
        CURRENCY_NAME_CODE_PAIRS.put("巴西里亚尔", "BRL");
        CURRENCY_NAME_CODE_PAIRS.put("加拿大元", "CAD");
        CURRENCY_NAME_CODE_PAIRS.put("瑞士法郎", "CHF");
        CURRENCY_NAME_CODE_PAIRS.put("丹麦克朗", "DKK");
        CURRENCY_NAME_CODE_PAIRS.put("欧元", "EUR");
        CURRENCY_NAME_CODE_PAIRS.put("英镑", "GBP");
        CURRENCY_NAME_CODE_PAIRS.put("港币", "HKD");
        CURRENCY_NAME_CODE_PAIRS.put("印尼卢比", "IDR");
        CURRENCY_NAME_CODE_PAIRS.put("日元", "JPY");
        CURRENCY_NAME_CODE_PAIRS.put("韩国元", "KRW");
        CURRENCY_NAME_CODE_PAIRS.put("澳门元", "MOP");
        CURRENCY_NAME_CODE_PAIRS.put("林吉特", "MYR");
        CURRENCY_NAME_CODE_PAIRS.put("挪威克朗", "NOK");
        CURRENCY_NAME_CODE_PAIRS.put("新西兰元", "NZD");
        CURRENCY_NAME_CODE_PAIRS.put("菲律宾比索", "PHP");
        CURRENCY_NAME_CODE_PAIRS.put("卢布", "RUB");
        CURRENCY_NAME_CODE_PAIRS.put("瑞典克朗", "SEK");
        CURRENCY_NAME_CODE_PAIRS.put("新加坡元", "SGD");
        CURRENCY_NAME_CODE_PAIRS.put("泰国铢", "THB");
        CURRENCY_NAME_CODE_PAIRS.put("新台币", "TWD");
        CURRENCY_NAME_CODE_PAIRS.put("美元", "USD");
    }

    /**
     * 判断valA是否大于valB，如果valA大于valB，那么返回true，否则返回false
     */
    public static boolean greaterThan(BigDecimal valA, BigDecimal valB) {
        return (valA.compareTo(valB) > 0);
    }

    /**
     * 判断valA和valB的值是否相等，如果valA和valB的值是否相等，那么返回true，否则返回false
     */
    public static boolean equals(BigDecimal valA, BigDecimal valB) {
        return (valA.compareTo(valB) == 0);
    }

    /**
     * 用于货币计算的加法，返回结果默认精确到小数点后4位，舍入模式：四舍五入
     *
     * @return （valA + valB）的结果
     */
    public static BigDecimal add(BigDecimal valA, BigDecimal valB) {
        return valA.add(valB).setScale(4, RoundingMode.HALF_UP);
    }

    /**
     * 用于货币计算的加法，返回结果默认精确到小数点后4位，舍入模式：不四舍五入
     *
     * @return （valA + valB）的结果
     */
    public static BigDecimal add1(BigDecimal valA, BigDecimal valB) {
        return valA.add(valB).setScale(4, RoundingMode.HALF_UP);
    }

    /**
     * 用于货币计算的加法，返回结果的舍入模式：四舍五入
     *
     * @param scale 返回结果的精确度，设置返回结果精确到小数点后几位
     * @return （valA + valB）的结果
     */
    public static BigDecimal add(BigDecimal valA, BigDecimal valB, int scale) {
        return valA.add(valB).setScale(scale, RoundingMode.HALF_UP);
    }

    /**
     * 用于货币计算的减法，返回结果默认精确到小数点后4位
     *
     * @return （valA - valB）的结果
     */
    public static BigDecimal minus(BigDecimal valA, BigDecimal valB) {
        return valA.add(valB.negate()).setScale(4, RoundingMode.HALF_UP);
    }

    /**
     * 用于货币计算的减法，返回结果的舍入模式：四舍五入
     *
     * @param scale 返回结果的精确度，设置返回结果精确到小数点后几位
     * @return （valA - valB）的结果
     */
    public static BigDecimal minus(BigDecimal valA, BigDecimal valB, int scale) {
        return valA.add(valB.negate()).setScale(scale, RoundingMode.HALF_UP);
    }

    /**
     * 用于货币计算的乘法，返回结果默认精确到小数点后4位
     *
     * @return （valA * valB）的结果
     */
    public static BigDecimal multiply(BigDecimal valA, BigDecimal valB) {
        return valA.multiply(valB).setScale(4, RoundingMode.HALF_UP);
    }

    /**
     * 用于货币计算的乘法，返回结果的舍入模式：四舍五入
     *
     * @param scale 返回结果的精确度，设置返回结果精确到小数点后几位
     * @return （valA * valB）的结果
     */
    public static BigDecimal multiply(BigDecimal valA, BigDecimal valB, int scale) {
        return valA.multiply(valB).setScale(scale, RoundingMode.HALF_UP);
    }

    /**
     * 用于货币计算的除法，返回结果默认精确到小数点后4位
     *
     * @param valA 被除数
     * @param valB 除数
     * @return （valA / valB）的结果
     */
    public static BigDecimal divide(BigDecimal valA, BigDecimal valB) {
        if (BigDecimal.ZERO.compareTo(valB) == 0) {
            throw new ArithmeticException("除数不能为0");
        }
        return valA.divide(valB, 4, RoundingMode.HALF_UP);
    }

    /**
     * 用于货币计算的除法，返回结果的舍入模式：四舍五入
     *
     * @param valA  被除数
     * @param valB  除数
     * @param scale 返回结果的精确度，设置返回结果精确到小数点后几位
     * @return （valA / valB）的结果
     */
    public static BigDecimal divide(BigDecimal valA, BigDecimal valB, int scale) {
        if (BigDecimal.ZERO.compareTo(valB) == 0) {
            throw new ArithmeticException("除数不能为0");
        }
        return valA.divide(valB, scale, RoundingMode.HALF_UP);
    }

    /**
     * 将指定的值转换为BigDecimal对象，如果val为null或者为空，那么默认返回0
     */
    public static BigDecimal toBigDecimal(String val) {
        if (val == null || "".equals(val.trim())) {
            return BigDecimal.ZERO;
        } else {
            String[] split = val.split(".");
            if (split.length == 2) {
                return new BigDecimal(val);
            } else {
                return new BigDecimal("0" + val);
            }

        }
    }

    /**
     * 保留四位小数
     */
    public static String keepFourDecimals(double var) {
        return mFourDecimalFormat.format(var);
    }

    /**
     * 保留两位小数
     */
    public static String keepTwoDecimals(double var) {
        return mTwoDecimalFormat.format(var);
    }

    /**
     * 小数点后四位不足补0
     */
    public static String keepFourDecimals(String str) {
        DecimalFormat decimalFormat;
        if (str.split("\\.")[0].equals("0")) {
            decimalFormat = new DecimalFormat("0.0000");
        } else
            decimalFormat = new DecimalFormat("###.0000");
        return decimalFormat.format(Double.parseDouble(str));
    }

    /**
     * 去除小数点后面的0
     *
     * @param str 待处理的字符串
     * @return 处理后的字符串
     */
    public static String deleteZeros(String str) {
        DecimalFormat decimalFormat;
        StringBuilder pattern = new StringBuilder("###.");
        if (str.split("\\.")[0].equals("0")) {
            decimalFormat = new DecimalFormat("0");
        } else if (str.split("\\.")[1].length() <= 4) {
            for (int i = 0; i < str.split("\\.")[1].length(); i++) {
                pattern.append("0");
            }
            decimalFormat = new DecimalFormat(pattern.toString());
        } else {
            decimalFormat = new DecimalFormat("###.0000");
        }
        return RegexUtil.subZeroAndDot(decimalFormat.format(Double.parseDouble(str)));
    }

    /**
     * 忽略大小写比较两个字符串是否相等
     */
    public static boolean ignoreCaseEquals(String str1, String str2) {
        return str1 == null ? str2 == null : str1.equalsIgnoreCase(str2);
    }

    /**
     * 将每三个数字加上逗号处理（通常使用金额方面的编辑）
     * 小数点后面保留八位,如果是0就直接取0
     * 如果小数点后面不够八位 根据长度动态保留位数
     * 直接筛掉小数点后面的0
     */
    public static String formatMoney(String str) {
        DecimalFormat decimalFormat;
        StringBuilder pattern = new StringBuilder(",###.");
        if (str.contains(".")) {
            if (str.split("\\.")[0].equals("0") && str.split("\\.")[1].length() == 0) {
                decimalFormat = new DecimalFormat("0");
            } else if (str.split("\\.")[0].equals("0") && str.split("\\.")[1].length() != 0) {
                decimalFormat = new DecimalFormat("0.0000000000");
            } else if (!str.split("\\.")[0].equals("0") && str.split("\\.")[1].length() <= 4) {
                for (int i = 0; i < str.split("\\.")[1].length(); i++) {
                    pattern.append("0");
                }
                decimalFormat = new DecimalFormat(pattern.toString());
            } else {
                decimalFormat = new DecimalFormat(",###.0000000000");
            }
        } else {
            decimalFormat = new DecimalFormat(",###");
        }
        return RegexUtil.subZeroAndDot(decimalFormat.format(Double.parseDouble(str)));
    }
}
