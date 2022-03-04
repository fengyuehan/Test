package com.example.algorithm.niukewang;

/**
 * author : zhangzf
 * date   : 2021/2/18
 * desc   :
 */
public class FrogJump {
    /**
     * 一只青蛙一次可以跳1级台阶，也可以跳2级台阶。求该青蛙跳上一个级的台阶总共有多少种跳法。
     */

    public static int frogJump(int n){
        if (n == 0){
            return 1;
        } else if (n == 1){
            return 1;
        }else if (n == 2){
            return 2;
        }else {
            return frogJump(n-1) + frogJump(n-2);
        }
    }

    /**
     * 一只青蛙一次可以跳上1级台阶，也可以跳上2级，也可以跳上，……也可以跳上n级，那么青蛙跳上一个n级的台阶总共有多少种跳法？
     */

    public static int frogJump2(int n){
        if (n == 0 || n == 1){
            return 1;
        }
        return 2 * frogJump2(n-1);
    }
}
