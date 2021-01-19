package com.example.service;

public class StringUtils {
    public static boolean isEmpty(String content) {
        if (content == null || content.length() == 0 || "null".equals(content)) {
            return true;
        }
        return false;
    }

    public static boolean isNotEmpty(String content) {
        return !isEmpty(content);
    }
}
