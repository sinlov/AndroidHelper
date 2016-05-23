/*
 * Copyright (c) 2012, Sinlov Corporation, All Rights Reserved
 */
package com.sinlov.androidhelper.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;

import java.util.HashMap;
import java.util.List;

/**
 * Android Meta Data tools
 * <br> It was has
 * {@link #PACKAGE_NAME} {@link #VERSIONCODE} {@link #VERSION_NAME} {@link #APP_NAME}
 * <br>and method
 * {@link #getAppInfo(Context)} {@link #getApplicationMetaData(Context, List)}
 * {@link #getMetaValue(Context, String)}
 * <br> to get Application Meta Data.
 * Created by "sinlov" on 2015/11/29.
 */
public class AndroidMetaData {

    public static final String PACKAGE_NAME = "packageName";
    public static final String VERSIONCODE = "versionCode";
    public static final String VERSION_NAME = "versionName";
    public static final String APP_NAME = "appName";

    /**
     * Meta Value getter by Androidmanifest.xml
     * <br/>Created by "sinlov" on 2015/11/29.
     *
     * @param context Context
     * @param metaKey Meta Key
     * @return null or String
     */
    public static String getMetaValue(Context context, String metaKey) {
        Bundle metaData = null;
        String metaValue = null;
        if (null == context || null == metaKey) {
            return null;
        }
        try {
            ApplicationInfo ai = context.getPackageManager()
                    .getApplicationInfo(context.getPackageName(),
                            PackageManager.GET_META_DATA);
            if (null != ai) {
                metaData = ai.metaData;
            }
            if (null != metaData) {
                metaValue = metaData.getString(metaKey);
            }
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return metaValue;
    }

    /**
     * Get Application Meta Data by Application Context
     * <P>通过应用的上下文获取对于列表键的信息
     *
     * @param context context
     * @param keys    String keys
     * @return HashMap<\String, String> metaDataContent or Null
     */
    public static HashMap<String, String> getApplicationMetaData(Context context, List<String> keys) {
        HashMap<String, String> mdc = null;
        ApplicationInfo info;
        Bundle metaData;
        try {
            info = context.getPackageManager().getApplicationInfo(
                    context.getPackageName(), PackageManager.GET_META_DATA);
            if (null != info) {
                metaData = info.metaData;
                if (null != metaData) {
                    mdc = new HashMap<String, String>();
                    for (String string : keys) {
                        mdc.put(string, metaData.getString(string));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mdc;
    }

    /**
     * Get APP Info By Application context, you can use
     * {@link #PACKAGE_NAME} or {@link #VERSIONCODE} or {@link #VERSION_NAME} or {@link #APP_NAME}
     * <p>通过Application的上下文获取应用基础信息 可以使用本类 {@link AndroidMetaData} 的枚举来获取对应的值
     *
     * @param context ApplicationContext
     * @return HashMap<\String, String> AndroidMetaData. context or Null.
     */
    public static HashMap<String, String> getAppInfo(Context context) {
        HashMap<String, String> vs = null;
        PackageInfo pkg;
        try {
            pkg = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            if (null != pkg) {
                vs = new HashMap<String, String>();
                vs.put(PACKAGE_NAME, pkg.packageName);
                vs.put(VERSIONCODE, "" + pkg.versionCode);
                vs.put(VERSION_NAME, pkg.versionName);
                vs.put(APP_NAME, pkg.applicationInfo.loadLabel(context.getPackageManager()).toString());
            }
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return vs;
    }

    private AndroidMetaData() {
    }
}
