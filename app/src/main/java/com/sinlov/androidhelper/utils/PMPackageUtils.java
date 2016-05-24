package com.sinlov.androidhelper.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;

import java.util.List;

/**
 * Package utils
 * <pre>
 *     sinlov
 *
 *     /\__/\
 *    /`    '\
 *  ≈≈≈ 0  0 ≈≈≈ Hello world!
 *    \  --  /
 *   /        \
 *  /          \
 * |            |
 *  \  ||  ||  /
 *   \_oo__oo_/≡≡≡≡≡≡≡≡o
 *
 * </pre>
 * Created by sinlov on 16/5/24.
 */
public class PMPackageUtils {

    private static PMPackageUtils instance;

    public static PMPackageUtils getInstance() {
        if (null != instance) {
            instance = new PMPackageUtils();
        }
        return instance;
    }

    public static List<PackageInfo> getFullPackageInfo(PackageManager packageManager) {
        return packageManager.getInstalledPackages(PackageManager.GET_PERMISSIONS);
    }

    public static String getAppName(PackageManager packageManager, String packageName) throws PackageManager.NameNotFoundException {
        ApplicationInfo info = packageManager.getApplicationInfo(packageName, 0);
        return info.loadLabel(packageManager).toString();
    }

    public static Drawable getAppIcon(PackageManager packageManager, String packageName) {
        try {
            ApplicationInfo info = packageManager.getApplicationInfo(packageName, 0);
            return info.loadIcon(packageManager);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void skipAppByPackageName(Context context, String packageName) {
        context.startActivity(context.getPackageManager().getLaunchIntentForPackage(
                packageName));
    }

    private PMPackageUtils() {
    }
}
