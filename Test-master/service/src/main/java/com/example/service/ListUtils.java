package com.example.service;

import java.util.List;

public class ListUtils {
    public static <T> boolean isEmpty(List<T> list) {
        return list == null || list.isEmpty();
    }

    public static <T> boolean notEmpty(List<T> list) {
        return !isEmpty(list);
    }
}
