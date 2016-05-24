package com.sinlov.androidhelper;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.sinlov.androidhelper.ui.SupperAppCompatActivity;
import com.sinlov.androidhelper.ui.packagehelper.PackageHelperActivity;
import com.sinlov.androidhelper.ui.widget.WidgetActivity;
import com.sinlov.androidhelper.utils.AppConfiguration;
import com.sinlov.androidhelper.utils.PackageListenByBroadcast;

public class MainActivity extends SupperAppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AppConfiguration.lockOrientation(this);
        HelperInstance.getInstance().setPackageListenByBroadcast(this);
        HelperInstance.getInstance().startListenerPackage();
        initData();
        initView();
    }

    private void initData() {
        HelperInstance.getInstance().setOnPackageListener(new PackageListenByBroadcast.OnPackageListener() {
            @Override
            public void onPackageAdded(String packageName) {
                Log.d("onPackageAdded", packageName);
            }

            @Override
            public void onPackageFirstLaunch(String packageName) {

            }

            @Override
            public void onPackageRestarted(String packageName) {

            }

            @Override
            public void onPackageReplaced(String packageName) {
                Log.d("onPackageReplaced", packageName);
            }

            @Override
            public void onPackageRemoved(String packageName) {
                Log.d("onPackageRemoved", packageName);
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        HelperInstance.getInstance().stopListenerPackage();
    }

    private void initView() {
        initToolbarView();
        initContentView();
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_main);
        if (null != fab) {
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Snackbar.make(view, getString(R.string.fbtn_main_base_info), Snackbar.LENGTH_LONG)
                            .setAction(getString(R.string.fbtn_main_action_issure), new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent = new Intent();
                                    intent.setAction(Intent.ACTION_VIEW);
                                    intent.setData(Uri.parse(getString(R.string.fbtn_main_issure_url)));
                                    startActivity(intent);
                                }
                            }).show();
                }
            });
        }
    }

    private void initContentView() {

    }

    private void initToolbarView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        if (null != drawer) {
            drawer.setDrawerListener(toggle);
        }
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        if (null != navigationView) {
            navigationView.setNavigationItemSelectedListener(this);
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (null != drawer) {
            if (drawer.isDrawerOpen(GravityCompat.START)) {
                drawer.closeDrawer(GravityCompat.START);
            } else {
                super.onBackPressed();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_main_package_helper) {
            skip2Activity(PackageHelperActivity.class);
        } else if (id == R.id.nav_main_widget) {
            skip2Activity(WidgetActivity.class);
        } else if (id == R.id.nav_main_image_tools) {

        } else if (id == R.id.nav_main_other_tools) {

        } else if (id == R.id.nav_main_share) {

        } else if (id == R.id.nav_main_contact_me) {

        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (null != drawer) {
            drawer.closeDrawer(GravityCompat.START);
        }
        return true;
    }

    private void skip2Activity(Class<?> cls) {
        startActivity(new Intent(MainActivity.this, cls));
    }


    public void onWidget(View view) {
        skip2Activity(WidgetActivity.class);
    }

    public void onPackageHelper(View view) {
        skip2Activity(PackageHelperActivity.class);
    }
}
