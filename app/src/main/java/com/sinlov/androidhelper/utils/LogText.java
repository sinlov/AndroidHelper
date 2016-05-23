/**
 * Copyright (c) 2015, sinlov Corporation, All Rights Reserved
 */
package com.sinlov.androidhelper.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;

/**
 * for save log text and read out by {@link SharedPreferences}
 * <p>Created by "sinlov" on 2015/11/29.
 */
public class LogText {

    private static final String KEY_LOG_TEXT = "log:full:SharedPreferences:text";

    /**
     * save log to SharedPreferences , key is {@link #KEY_LOG_TEXT}
     *
     * @param context {@link Context}
     * @param text    {@link String}
     */
    public static void saveLog(Context context, String text) {
        SharedPreferences sp = PreferenceManager
                .getDefaultSharedPreferences(context);
        Editor editor = sp.edit();
        editor.putString(KEY_LOG_TEXT, text);
        editor.commit();
    }

    /**
     * read out log text, after read clear log.
     *
     * @param context {@link Context}
     * @return {@link String}
     */
    public static String readLogClear(Context context) {
        SharedPreferences sp = PreferenceManager
                .getDefaultSharedPreferences(context);
        String log = sp.getString(KEY_LOG_TEXT, "");
        Editor edit = sp.edit();
        edit.clear();
        edit.commit();
        return log;
    }

    /**
     * read out log text.
     *
     * @param context {@link Context}
     * @return {@link String}
     */
    public static String readLog(Context context) {
        SharedPreferences sp = PreferenceManager
                .getDefaultSharedPreferences(context);
        return sp.getString(KEY_LOG_TEXT, "");
    }

    /**
     * save log to SharedPreferences by custom key
     *
     * @param context {@link Context}
     * @param key     {@link String}
     * @param text    {@link String}
     */
    public static void saveCustomLog(Context context, String key, String text) {
        SharedPreferences sp = PreferenceManager
                .getDefaultSharedPreferences(context);
        Editor editor = sp.edit();
        editor.putString(key, text);
        editor.commit();
    }

    /**
     * read log by custom key, if return "" the log is null.
     * after read clear log
     *
     * @param context {@link Context}
     * @param key     {@link String}
     * @return text {@link String}
     */
    public static String readCustomLogClear(Context context, String key) {
        SharedPreferences sp = PreferenceManager
                .getDefaultSharedPreferences(context);
        String log = sp.getString(key, "");
        Editor edit = sp.edit();
        edit.clear();
        edit.commit();
        return log;
    }

    /**
     * read log by custom key, if return "" the log is null.
     *
     * @param context {@link Context}
     * @param key     {@link String}
     * @return text {@link String}
     */
    public static String readCustomLog(Context context, String key) {
        SharedPreferences sp = PreferenceManager
                .getDefaultSharedPreferences(context);
        return sp.getString(key, "");
    }


    private LogText() {
    }
}