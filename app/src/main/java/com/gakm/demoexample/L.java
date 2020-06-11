package com.gakm.demoexample;

import android.util.Log;

/**
 * @author DemoExample
 * @company 广安科贸
 * @date 2020/6/11 0011 10:21
 */
public class L {
    private static final String TAG = "LogUtilsExample------";

    public static void d(String message) {
        Log.d(TAG, message);
    }

    public static void d(int message) {
        Log.d(TAG, String.valueOf(message));
    }

}
