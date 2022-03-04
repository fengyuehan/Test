package com.example.algorithm.niukewang;

public class ReplaceSpace {

    /**
     * 请实现一个函数，将一个字符串中的每个空格替换成“%20”
     * @param str
     * @return
     */
    public static String replaceSpace(String str){
        StringBuilder sb = new StringBuilder();
        char[] chars = str.toCharArray();
        for (int i = 0; i < chars.length;i++){
            if (chars[i] == ' '){
                sb.append("%20");
            }else {
                sb.append(chars[i]);
            }
        }
        return sb.toString();
    }
}
