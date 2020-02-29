package com.example.eventdispatch;

import android.view.MotionEvent;

public class Util {
    public static String getAction(MotionEvent event){
        String action = "";

        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            action = "ACTION_DOWN";
        } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
            action = "ACTION_MOVE";
        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            action = "ACTION_UP";
        }

        return action;
    }
}
