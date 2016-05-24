package com.sinlov.androidhelper.ui.packagehelper;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.sinlov.androidhelper.R;
import com.sinlov.androidhelper.codewidget.DividerItemDecoration;
import com.sinlov.androidhelper.module.PackageItem;
import com.sinlov.androidhelper.ui.SupperAppCompatActivity;
import com.sinlov.androidhelper.utils.AppConfiguration;
import com.sinlov.androidhelper.utils.ClipboardUtils;
import com.sinlov.androidhelper.utils.DefaultPackageInstaller;
import com.sinlov.androidhelper.utils.InputMethodUtils;

import java.util.ArrayList;
import java.util.List;

import cn.bingoogolapple.androidcommon.adapter.BGAOnItemChildClickListener;
import cn.bingoogolapple.androidcommon.adapter.BGAOnRVItemClickListener;
import cn.bingoogolapple.androidcommon.adapter.BGAOnRVItemLongClickListener;

public class PackageSearchActivity extends SupperAppCompatActivity implements BGAOnItemChildClickListener, BGAOnRVItemClickListener, BGAOnRVItemLongClickListener {

    private ArrayList<PackageItem> packageItems;
    private PackageManager packageManager;
    private RecyclerView recyclerView;
    private PackageItemAdapter adapter;

    @Override
    public void onItemChildClick(ViewGroup viewGroup, View view, int i) {
        PackageItem item = packageItems.get(i);
        if (null != item) {
            skipAppByPackageName(item.getPackageName());
        }
    }

    @Override
    public void onRVItemClick(ViewGroup viewGroup, View view, int i) {
        PackageItem item = packageItems.get(i);
        if (null != item) {
            String packageName = item.getPackageName();
            ClipboardUtils.copy2Clipboard(this, packageName);
            Toast.makeText(this, getString(R.string.toast_copy_to_clipboard) + packageName, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onRVItemLongClick(ViewGroup viewGroup, View view, int i) {
        PackageItem item = packageItems.get(i);
        if (null != item) {
            String packageName = item.getPackageName();
            DefaultPackageInstaller.uninstallApk(this, packageName);
            return true;
        }
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppConfiguration.lockOrientation(this);
        initBaseData();
        initView();
        initPackageShowing();
    }

    private void initBaseData() {
        packageManager = getPackageManager();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.package_search, menu);
        initSearchView(menu);
        return true;
    }

    private void initSearchView(Menu menu) {
        final MenuItem item = menu.findItem(R.id.action_search_package);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                doSearch(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                doTestChaneSearch(newText);
                return true;
            }
        });
    }

    private void doSearch(String query) {
        if (null != packageItems) {
            packageItems = filterPackage(packageItems, query);
            if (packageItems.size() > 0) {
                adapter.clear();
                adapter.addNewDatas(packageItems);
            } else {
                Toast.makeText(PackageSearchActivity.this, getString(R.string.toast_info_data_empty), Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(PackageSearchActivity.this, getString(R.string.toast_info_data_error), Toast.LENGTH_SHORT).show();
        }
    }

    private void doTestChaneSearch(String newText) {
        if (null != newText) {
            if (newText.length() > PackageHelperActivity.MIN_SEARCH_WORD_SIZE) {
                for (PackageItem pi :
                        packageItems) {
                    String packageName = pi.getPackageName();
                    String appName = pi.getAppName();
                    if (appName.contains(newText)) {
                        recyclerView.scrollToPosition(pi.getId());
                        return;
                    }
                    if (packageName.contains(newText)) {
                        recyclerView.smoothScrollToPosition(pi.getId());
                        return;
                    }
                }
            }
        } else {
            Toast.makeText(PackageSearchActivity.this, getString(R.string.toast_info_data_error), Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_search_refresh_package) {
            refreshPackageInfo();
            return true;
        }

        if (id == R.id.action_to_top) {
            if (null != recyclerView) {
                recyclerView.smoothScrollToPosition(0);
            }
        }

        return super.onOptionsItemSelected(item);
    }

    private void initView() {
        setContentView(R.layout.activity_package_search);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar supportActionBar = getSupportActionBar();
        if (null != supportActionBar) {
            supportActionBar.setDisplayHomeAsUpEnabled(true);
        }
        initSnackbar();
        this.recyclerView = (RecyclerView) findViewById(R.id.rcv_package_search);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        dividerItemDecoration.setColor(getResources().getColor(R.color.md_light_blue_200));
        dividerItemDecoration.setSize(getResources().getDimensionPixelOffset(R.dimen.mdl_divider_height));
        dividerItemDecoration.setPaddingStart(getResources().getDimensionPixelOffset(R.dimen.mdl_vertical_margin_half));
        recyclerView.addItemDecoration(dividerItemDecoration);
        adapter = new PackageItemAdapter(recyclerView, packageManager);
        adapter.setOnItemChildClickListener(this);
        adapter.setOnRVItemClickListener(this);
        adapter.setOnRVItemLongClickListener(this);
        recyclerView.setAdapter(adapter);
    }

    private void initPackageShowing() {
        String key = getIntent().getExtras().getString("search:key");
        packageItems = filterPackage(getFullPackageList(), key);
        if (null != packageItems) {
            adapter.addNewDatas(packageItems);
        }
    }

    private void initSnackbar() {
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_package_helper);
        if (null != fab) {
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Snackbar.make(view, getString(R.string.fbtn_main_package_info), Snackbar.LENGTH_LONG)
                            .setAction(getString(R.string.fbtn_main_action_down), new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if (null != recyclerView && null != packageItems) {
                                        recyclerView.smoothScrollToPosition(packageItems.size());
                                    }
                                }
                            })
                            .show();
                }
            });
        }
    }

    private void refreshPackageInfo() {
        adapter.clear();
        addFullPackageInfo();
    }

    private void addFullPackageInfo() {
        packageItems = getFullPackageList();
        if (null != packageItems) {
            adapter.addNewDatas(packageItems);
        }
    }

    private ArrayList<PackageItem> filterPackage(List<PackageItem> packageItems, CharSequence query) {
        ArrayList<PackageItem> newItems = new ArrayList<>();
        for (PackageItem pi :
                packageItems) {
            String packageName = pi.getPackageName();
            String appName = pi.getAppName();
            if (appName.contains(query)) {
                newItems.add(pi);
            }
            if (packageName.contains(query)) {
                newItems.add(pi);
            }
        }
        return newItems;
    }

    private ArrayList<PackageItem> getFullPackageList() {
        List<PackageInfo> packageList = packageManager.getInstalledPackages(PackageManager.GET_PERMISSIONS);
        if (null != packageList) {
            packageItems = new ArrayList<>();
            int i = 0;
            try {
                for (PackageInfo pm :
                        packageList) {
                    PackageItem item = new PackageItem();
                    item.setId(i);
                    item.setVc(pm.versionCode);
                    item.setPackageName(pm.packageName);
                    item.setAppName(getAppName(pm.packageName));
                    item.setVersionName(pm.versionName);
                    packageItems.add(item);
                    i++;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return packageItems;
    }

    private String getAppName(String packName) throws PackageManager.NameNotFoundException {
        ApplicationInfo info = packageManager.getApplicationInfo(packName, 0);
        return info.loadLabel(packageManager).toString();
    }

    private void adNewItem() {
        if (null != packageItems) {

        }
    }

    private void skipAppByPackageName(String packageName) {
        InputMethodUtils.closeInputPan(this);
        startActivity(getPackageManager().getLaunchIntentForPackage(
                packageName));
    }

}
