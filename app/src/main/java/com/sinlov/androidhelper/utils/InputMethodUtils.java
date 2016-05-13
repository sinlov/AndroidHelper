/**
 * Copyright (c) 2015, sinlov Corporation, All Rights Reserved
 */
package com.sinlov.androidhelper.utils;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 * Input method tools
 * <p>Created by "sinlov" on 2015/11/29.
 */
public class InputMethodUtils {

    /**
     * 获取输入法打开状态
     *
     * @param context context
     * @return boolean
     */
    public static boolean getInputPanState(Context context) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        return imm.isActive();
    }

    /**
     * 关闭输入法
     *
     * @param activity {@link Activity}
     */
    @SuppressWarnings("ConstantConditions")
    public static void closeInputPan(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    /**
     * 通过一个View，来关闭输入法
     * 接受软键盘输入的编辑文本或其它视图
     *
     * @param activity {@link Activity}
     * @param v        {@link View}
     */
    @SuppressWarnings("ConstantConditions")
    public static boolean closeInputPanByView(Activity activity, View v) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        return inputMethodManager
                .showSoftInput(v, InputMethodManager.SHOW_FORCED);
    }

    /**
     * 输入法打开则关闭，如果没打开则打开,适用于外部回调
     *
     * @param context context
     */
    public static boolean closeOrOpenInputPan(Context context) {
        boolean flag;
        flag = getInputPanState(context);
        InputMethodManager m = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        m.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
        return flag;
    }

    /**
     * 打开输入法框
     *
     * @param context context
     */
    public static void openInputPan(Context context) {
        InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.showInputMethodPicker();
    }

    /**
     * 打开输入法
     * 接受软键盘输入的编辑文本或其它视图
     *
     * @param context context
     * @param v       {@link View}
     */
    public static void openInputPanByView(Context context, View v) {
        InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.showSoftInput(v, InputMethodManager.SHOW_FORCED);
    }

    private InputMethodUtils() {
    }
}
