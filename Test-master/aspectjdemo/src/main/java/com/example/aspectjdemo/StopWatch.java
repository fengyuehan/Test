package com.example.aspectjdemo;

import java.util.concurrent.TimeUnit;

public class StopWatch {
    private long startTime;
    private long endTime;
    private long elapsedTime;
    
    public StopWatch(){
        
    }
    
    public void start(){
        reset();
        startTime = System.nanoTime();
    }

    private void reset() {
        startTime = 0;
        elapsedTime = 0;
        endTime = 0;
    }

    public void stop(){
        if (startTime != 0){
            endTime = System.nanoTime();
            elapsedTime = endTime - startTime;
        }else {
            reset();
        }
    }

    public long getTotalTime(){
        return (elapsedTime != 0)? TimeUnit.NANOSECONDS.toMillis(elapsedTime):0;
    }
}
