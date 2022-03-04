package com.example.innerclass;

import android.content.Context;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() {
        new Person2();
    }


    public static class Person2 {
        public static int value1 = 100;
        public static final int value2 = 200;

        public static Person2 p = new Person2();
        public int value4 = 400;

        static{
            value1 = 101;
            System.out.println("1");
        }

        {
            value1 = 102;
            System.out.println("2");
        }

        public Person2(){
            value1 = 103;
            System.out.println("3");
        }
    }

}
