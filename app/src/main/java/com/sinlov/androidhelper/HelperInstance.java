package com.sinlov.androidhelper;

import android.content.Context;

import com.sinlov.androidhelper.utils.PackageListenByBroadcast;

/**
 * Helper Instance for single settings
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
 * Created by sinlov on 16/5/16.
 */
public class HelperInstance {

    private static HelperInstance helperInstance;

    private static PackageListenByBroadcast packageListenByBroadcast;
    private static boolean isStartBroadcast = false;

    public static HelperInstance getInstance() {
        if (null == helperInstance) {
            return new HelperInstance();
        } else {
            return helperInstance;
        }
    }

    public void setPackageListenByBroadcast(Context context) {
        if (null == packageListenByBroadcast) {
            HelperInstance.packageListenByBroadcast = new PackageListenByBroadcast(context.getApplicationContext());
        } else {
            throw new RuntimeException("Error setting package listener by broadcast by twice!");
        }
    }

    public void setOnPackageListener(PackageListenByBroadcast.OnPackageListener listener) {
        if (null != packageListenByBroadcast) {
            HelperInstance.packageListenByBroadcast.setOnPackageListener(listener);
        } else {
            throw new RuntimeException("you are not setting package listener by broadcast");
        }
    }

    public void setOnAllPackageListener(PackageListenByBroadcast.OnPackageAllListener listener) {
        if (null != packageListenByBroadcast) {
            HelperInstance.packageListenByBroadcast.setPackageAllListener(listener);
        } else {
            throw new RuntimeException("you are not setting package listener by broadcast");
        }
    }

    public void startListenerPackage() {
        if (null != packageListenByBroadcast) {
            if (isStartBroadcast) {
                new RuntimeException("you has start listener by broadcast").printStackTrace();
            } else {
                packageListenByBroadcast.start();
                isStartBroadcast = true;
            }
        } else {
            new RuntimeException("you are not setting package listener by broadcast").printStackTrace();
        }
    }

    public void stopListenerPackage() {
        if (null != packageListenByBroadcast) {
            packageListenByBroadcast.stop();
        } else {
            new RuntimeException("you are not setting package listener by broadcast").printStackTrace();
        }
    }

    private HelperInstance() {
    }
}
