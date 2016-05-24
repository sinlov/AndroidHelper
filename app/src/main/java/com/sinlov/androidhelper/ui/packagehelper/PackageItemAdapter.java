package com.sinlov.androidhelper.ui.packagehelper;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;

import com.sinlov.androidhelper.R;
import com.sinlov.androidhelper.module.PackageItem;

import cn.bingoogolapple.androidcommon.adapter.BGARecyclerViewAdapter;
import cn.bingoogolapple.androidcommon.adapter.BGAViewHolderHelper;

/**
 * package info item adapter
 * Created by "sinlov" on 15/11/30.
 */
public class PackageItemAdapter extends BGARecyclerViewAdapter<PackageItem> {

    private final PackageManager pm;

    public PackageItemAdapter(RecyclerView recyclerView, PackageManager pm) {
        super(recyclerView, R.layout.item_recycle_view_package_hepler);
        this.pm = pm;
    }

    @Override
    protected void fillData(BGAViewHolderHelper bgaViewHolderHelper, int item, PackageItem packageItem) {
        String packageName = packageItem.getPackageName();
        bgaViewHolderHelper.setText(R.id.tv_item_rv_package_helper_title, packageItem.getAppName());
        bgaViewHolderHelper.setText(R.id.tv_item_rv_package_helper_package_name, packageName);
        bgaViewHolderHelper.setText(R.id.tv_item_rv_package_helper_version_code, packageItem.getVc() + "");
        bgaViewHolderHelper.setText(R.id.tv_item_rv_package_helper_version_name, packageItem.getVersionName());
        Drawable appIcon = getAppIcon(packageName);
        if (null != appIcon) {
            bgaViewHolderHelper.setImageDrawable(R.id.img_item_rv_package_helper_icon, appIcon);
        }
        bgaViewHolderHelper.setItemChildClickListener(R.id.btn_item_rv_package_helper_open);
    }

    private Drawable getAppIcon(String packageName) {
        try {
            ApplicationInfo info = pm.getApplicationInfo(packageName, 0);
            return info.loadIcon(pm);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
