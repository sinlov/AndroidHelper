package com.sinlov.androidhelper.utils;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;

/**
 * TODO
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
 * Created by sinlov on 16/5/5.
 */
public class AppConfiguration {

    /**
     * lock orientation
     * @param activity {@link Activity}
     */
    public static void lockOrientation(Activity activity) {
        int orientation = activity.getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        } else {
            activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
    }

    /**
     * get orientation setting
     * @param activity {@link Activity}
     * @return {@link Configuration#ORIENTATION_UNDEFINED} or {@link Configuration#ORIENTATION_PORTRAIT}
     * or {@link Configuration#ORIENTATION_LANDSCAPE}
     */
    public static int getOrientationSetting(Activity activity){
        return activity.getResources().getConfiguration().orientation;
    }
}
