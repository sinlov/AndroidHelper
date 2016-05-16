/**
 * Copyright (c) 2015, Incito Corporation, All Rights Reserved
 */
package com.sinlov.androidhelper.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;

/**
 * 本类基于系统安装广播
 * <p>如无法接收系统广播，或者因为ROM定制问题导致广播消失，则无法使用本工具类
 * <p>使用说明： 
 * <p>1、初始化<p>PackageListenByBroadcast packageListen = new PackageListenByBroadcast(context); 
 * <p><li> 推荐使用APP的上下文，防止溢出
 * <p>2、设置Package监听<p>packageListen.setOnPackageListener( new OnPackageListener(){} );
 * <p>3、设置Package监听<p>在onResume方法中启动监听广播： packageListen.start();
 * <p>4、一定要停止广播，比如在onPause方法中停止监听广播： packageListen.stop();
 * <p>当然你可以使用完整监听 <p>packageListen.setPackageAllListener( new OnPackageAllListener(){} );
 * <p>Created by "sinlov" on 2015/11/29.
 */
public class PackageListenByBroadcast {

	/**
	 * default of priority set is 1000
	 */
	public static final int DEFAULT_PRIORITY = 1000;

	private static final String TAG = PackageListenByBroadcast.class.getCanonicalName();
	private static final int PACKAGE_NAME_START_INDEX = 8;
	private static final String INTENT_DATA_SCHEME = "package";

	private Context context = null;
	private IntentFilter packageIntentFilter = null;
	private OnPackageListener packageListener = null;
	private OnPackageAllListener packageAllListener = null;
	private PackageReceiver packageReceiver = null;

	public PackageListenByBroadcast(Context context) {
		this.context = context.getApplicationContext();
		packageIntentFilter = new IntentFilter();
		packageIntentFilter.addAction(TAG);
		packageIntentFilter.addAction(Intent.ACTION_PACKAGE_ADDED);
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1) {
			packageIntentFilter.addAction(Intent.ACTION_PACKAGE_FIRST_LAUNCH);
		}
		packageIntentFilter.addAction(Intent.ACTION_PACKAGE_RESTARTED);
		packageIntentFilter.addAction(Intent.ACTION_PACKAGE_REPLACED);
		packageIntentFilter.addAction(Intent.ACTION_PACKAGE_REMOVED);
		packageIntentFilter.addAction(Intent.ACTION_PACKAGE_CHANGED);
		packageIntentFilter.addAction(Intent.ACTION_PACKAGE_DATA_CLEARED);
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
			packageIntentFilter.addAction(Intent.ACTION_PACKAGE_FULLY_REMOVED);
			packageIntentFilter.addAction(Intent.ACTION_PACKAGE_NEEDS_VERIFICATION);
		}
		packageIntentFilter.addDataScheme(INTENT_DATA_SCHEME);
		packageIntentFilter.setPriority(DEFAULT_PRIORITY);
		packageReceiver = new PackageReceiver();
	}

	/**
	 * package listener
	 * 设置安装包监听
	 * @param onPackageListener {@link OnPackageListener}
	 */
	public void setOnPackageListener(
			OnPackageListener onPackageListener) {
		packageListener = onPackageListener;
	}
	
	/**
	 * package all listener
	 * 设置安装包完整监听
	 * @param packageAllListener {@link OnPackageAllListener}
	 */
	public void setPackageAllListener(OnPackageAllListener packageAllListener) {
		this.packageAllListener = packageAllListener;
	}

	/**
	 * start listen
	 * 开始监听
	 */
	public void start() {
		context.registerReceiver(packageReceiver, packageIntentFilter);
	}

	/**
	 * stop listen
	 * 停止监听
	 */
	public void stop() {
		context.unregisterReceiver(packageReceiver);
	}

	private class PackageReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			receive(context, intent);
		}
	}

	private void receive(Context context, Intent intent) {
		if(null == intent){
			return;
		}
		String packageData = intent.getDataString();
		if(null == packageData || packageData.length() <= PACKAGE_NAME_START_INDEX){
			return;
		}
		String packageName = packageData.substring(PACKAGE_NAME_START_INDEX);
		if (!packageName.equals("")) {
			String action = intent.getAction();
			if (null != packageListener) {
				if (action.equals(Intent.ACTION_PACKAGE_ADDED)) {
					packageListener.onPackageAdded(packageName);
				}
				if (action.equals(Intent.ACTION_PACKAGE_FIRST_LAUNCH)) {
					packageListener.onPackageFirstLaunch(packageName);
				}
				if (action.equals(Intent.ACTION_PACKAGE_RESTARTED)) {
					packageListener.onPackageRestarted(packageName);
				}
				if (action.equals(Intent.ACTION_PACKAGE_REPLACED)) {
					packageListener.onPackageReplaced(packageName);
				}
				if (action.equals(Intent.ACTION_PACKAGE_REMOVED)) {
					packageListener.onPackageRemoved(packageName);
				}
			}
			if (null != packageAllListener) {
				if (action.equals(Intent.ACTION_PACKAGE_ADDED)) {
					packageAllListener.onPackageAdded(packageName);
				}
				if (action.equals(Intent.ACTION_PACKAGE_FIRST_LAUNCH)) {
					packageAllListener.onPackageFirstLaunch(packageName);
				}
				if (action.equals(Intent.ACTION_PACKAGE_RESTARTED)) {
					packageAllListener.onPackageRestarted(packageName);
				}
				if (action.equals(Intent.ACTION_PACKAGE_REPLACED)) {
					packageAllListener.onPackageReplaced(packageName);
				}
				if (action.equals(Intent.ACTION_PACKAGE_REMOVED)) {
					packageAllListener.onPackageRemoved(packageName);
				}
				if (action.equals(Intent.ACTION_PACKAGE_CHANGED)) {
					packageAllListener.onPackageChanged(packageName);
				}
				if (action.equals(Intent.ACTION_PACKAGE_DATA_CLEARED)) {
					packageAllListener.onPackageDataCleared(packageName);
				}
				if (action.equals(Intent.ACTION_PACKAGE_FULLY_REMOVED)) {
					packageAllListener.onPackageFullyRemoved(packageName);
				}
				if (action.equals(Intent.ACTION_PACKAGE_NEEDS_VERIFICATION)) {
					packageAllListener.onPackageNeedsVerification(packageName);
				}
			}
		}

	}

	/**
	 * package install listener
	 * 安装包监听接口
	 */
	public interface OnPackageListener {

		void onPackageAdded(String packageName);

		void onPackageFirstLaunch(String packageName);
		
		void onPackageRestarted(String packageName);
		
		void onPackageReplaced(String packageName);
		
		void onPackageRemoved(String packageName);
		
	}
	
	/**
	 * package install full action listener
	 * 安装包监完整听接口
	 */
	public interface OnPackageAllListener {
		
		void onPackageAdded(String packageName);

		void onPackageFirstLaunch(String packageName);
		
		void onPackageRestarted(String packageName);
		
		void onPackageReplaced(String packageName);
		
		void onPackageRemoved(String packageName);
		
		void onPackageChanged(String packageName);
		
		void onPackageDataCleared(String packageName);
		
		void onPackageFullyRemoved(String packageName);
		
		void onPackageNeedsVerification(String packageName);
	}
}
