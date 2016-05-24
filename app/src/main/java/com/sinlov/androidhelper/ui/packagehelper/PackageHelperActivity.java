package com.sinlov.androidhelper.ui.packagehelper;

import android.content.Intent;
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

import com.sinlov.androidhelper.HelperInstance;
import com.sinlov.androidhelper.R;
import com.sinlov.androidhelper.codewidget.DividerItemDecoration;
import com.sinlov.androidhelper.module.PackageItem;
import com.sinlov.androidhelper.ui.SupperAppCompatActivity;
import com.sinlov.androidhelper.utils.AppConfiguration;
import com.sinlov.androidhelper.utils.ClipboardUtils;
import com.sinlov.androidhelper.utils.DefaultPackageInstaller;
import com.sinlov.androidhelper.utils.InputMethodUtils;
import com.sinlov.androidhelper.utils.PMPackageUtils;
import com.sinlov.androidhelper.utils.PackageListenByBroadcast;

import java.util.ArrayList;
import java.util.List;

import cn.bingoogolapple.androidcommon.adapter.BGAOnItemChildClickListener;
import cn.bingoogolapple.androidcommon.adapter.BGAOnRVItemClickListener;
import cn.bingoogolapple.androidcommon.adapter.BGAOnRVItemLongClickListener;

public class PackageHelperActivity extends SupperAppCompatActivity implements BGAOnItemChildClickListener, BGAOnRVItemClickListener, BGAOnRVItemLongClickListener {

    public static final int MIN_SEARCH_WORD_SIZE = 2;

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
    }

    private void initBaseData() {
        packageManager = getPackageManager();
        HelperInstance.getInstance().setOnPackageListener(new PackageListenByBroadcast.OnPackageListener() {
            @Override
            public void onPackageAdded(String packageName) {

            }

            @Override
            public void onPackageFirstLaunch(String packageName) {

            }

            @Override
            public void onPackageRestarted(String packageName) {

            }

            @Override
            public void onPackageReplaced(String packageName) {

            }

            @Override
            public void onPackageRemoved(String packageName) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.package_helper, menu);
        initSearchView(menu);
        return true;
    }

    private void initSearchView(Menu menu) {
        final MenuItem item = menu.findItem(R.id.action_package_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                doSearchSkip(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                doTestChaneSearch(newText);
                return true;
            }
        });
    }

    private void doTestChaneSearch(String newText) {
        if (null != newText) {
            if (newText.length() > MIN_SEARCH_WORD_SIZE) {
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
            Toast.makeText(PackageHelperActivity.this, getString(R.string.toast_info_data_error), Toast.LENGTH_SHORT).show();
        }
    }

    private void doSearchSkip(String query) {
        if (null != packageItems) {
            for (PackageItem pi :
                    packageItems) {
                String packageName = pi.getPackageName();
                String appName = pi.getAppName();
                if (appName.contains(query)) {
                    skip2PackageSearch(query);
                    return;
                }
                if (packageName.contains(query)) {
                    skip2PackageSearch(query);
                    return;
                }
            }
        } else {
            Toast.makeText(PackageHelperActivity.this, getString(R.string.toast_info_data_error), Toast.LENGTH_SHORT).show();
        }
    }

    private void skip2PackageSearch(String query) {
        Intent intent = new Intent(PackageHelperActivity.this, PackageSearchActivity.class);
        intent.putExtra("search:key", query);
        startActivity(intent);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_refresh_package) {
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
        setContentView(R.layout.activity_package_helper);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar supportActionBar = getSupportActionBar();
        if (null != supportActionBar) {
            supportActionBar.setDisplayHomeAsUpEnabled(true);
        }
        initSnackbar();
        this.recyclerView = (RecyclerView) findViewById(R.id.rcv_package_helper);
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
        addFullPackageInfo();
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
        List<PackageInfo> packageList = PMPackageUtils.getFullPackageInfo(packageManager);
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
                    item.setAppName(PMPackageUtils.getAppName(packageManager, pm.packageName));
                    item.setVersionName(pm.versionName);
                    packageItems.add(item);
                    i++;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            adapter.addNewDatas(packageItems);
        }
    }

    private void skipAppByPackageName(String packageName) {
        InputMethodUtils.closeInputPan(this);
        PMPackageUtils.skipAppByPackageName(this, packageName);
    }
}
