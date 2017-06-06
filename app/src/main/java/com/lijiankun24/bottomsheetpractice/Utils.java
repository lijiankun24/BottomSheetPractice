package com.lijiankun24.bottomsheetpractice;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Utils.java
 * <p>
 * Created by lijiankun on 17/6/5.
 */

public class Utils {

    public static int getHeight(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);

        return metrics.heightPixels;
    }

    public static void toggleSoftInput(Context context, final EditText editText) {
        if (context == null || editText == null) {
            return;
        }
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                InputMethodManager inputManager = (InputMethodManager) editText
                        .getContext()
                        .getSystemService(Context.INPUT_METHOD_SERVICE);

                inputManager.toggleSoftInput(0, InputMethodManager.SHOW_FORCED);
            }
        }, 250);
    }
}
