/*
 * Copyright (c) 2012, Incito Corporation, All Rights Reserved
 */
package com.sinlov.androidhelper.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

/**
 * Package Installer
 * <li>Full android API used pass</li>
 * <br> has method {@link #installApk(Context, String)} {@link #uninstallApk(Context, String)}
 * to install apk or uninstall apk
 * Created by "sinlov" on 2015/11/29.
 */
public class DefaultPackageInstaller {
	
	private DefaultPackageInstaller() {
	}
	
	/**
     * install apk
	 * <br>安装apk
     * <li>This method will use {@link Intent#FLAG_ACTIVITY_NEW_TASK} so in some ROM will WARNING,
     * but it still work on it.</li>
	 * @param context context
	 * @param filePath apk file full path
	 */
    public static void installApk(Context context, String filePath) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setDataAndType(Uri.parse("file://" + filePath),
                "application/vnd.android.package-archive");
        context.startActivity(intent);
    }
 
    /**
     * uninstall apk
     * <br>卸载apk
     * @param context context
     * @param packageName App id or package name
     */
    public static void uninstallApk(Context context, String packageName) {
        Uri uri = Uri.parse("package:" + packageName);
        Intent intent = new Intent(Intent.ACTION_DELETE, uri);
        context.startActivity(intent);
    }
}
