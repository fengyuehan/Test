package com.example.hilt;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Qualifier;

/**
 * author : zhangzf
 * date   : 2021/3/15
 * desc   :
 */
@Qualifier
@Retention(RetentionPolicy.RUNTIME)
public @interface bindElectricEngine {
}
