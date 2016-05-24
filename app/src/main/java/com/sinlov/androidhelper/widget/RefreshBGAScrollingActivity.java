package com.sinlov.androidhelper.widget;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.sinlov.androidhelper.R;
import com.sinlov.androidhelper.adapter.DataTestRecycleAdapter;
import com.sinlov.androidhelper.codewidget.BGAAppBarScrollListener;
import com.sinlov.androidhelper.codewidget.DividerItemDecoration;
import com.sinlov.androidhelper.codewidget.OnBGAAppBarDelegateListener;
import com.sinlov.androidhelper.module.DataTestItem;
import com.sinlov.androidhelper.utils.ThreadUtil;

import java.util.ArrayList;
import java.util.List;

import cn.bingoogolapple.androidcommon.adapter.BGAOnItemChildClickListener;
import cn.bingoogolapple.androidcommon.adapter.BGAOnRVItemClickListener;
import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

public class RefreshBGAScrollingActivity extends AppCompatActivity {

    private final static String TAG = RefreshBGAScrollingActivity.class.getCanonicalName();
    public static final int PAGE_SIZE = 10;
    public static final int PAGE_LAST = 3;
    private BGARefreshLayout refreshLayout;
    private RecyclerView dataRv;
    private DataTestRecycleAdapter adapter;
    private int pageNumber = 0;
    private List<DataTestItem> datas;
    private String title;
    private String content;
    private AppBarLayout appBar;
    private View rvFooterView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refresh_bga_scrolling);
        initView();

    }

    private void showToast(String content) {
        Toast.makeText(this.getApplicationContext(), content, Toast.LENGTH_SHORT).show();
    }

    private void initFloatingButton() {
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        if (null != fab) {
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            });
        }
    }

    private void initToolBar() {
        this.appBar = (AppBarLayout) findViewById(R.id.app_bar_bga_scrolling);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void initRefreshView() {
        refreshLayout = (BGARefreshLayout) findViewById(R.id.bga_rel_scrolling_in_nested);
        rvFooterView = findViewById(R.id.rv_refresh_scrolling_root);
        BGANormalRefreshViewHolder refreshViewHolder = new BGANormalRefreshViewHolder(this, true);
        dataRv = (RecyclerView) findViewById(R.id.rv_refresh_scrolling);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        dataRv.setLayoutManager(linearLayoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        dividerItemDecoration.setColor(getResources().getColor(R.color.md_light_blue_200));
        dividerItemDecoration.setSize(getResources().getDimensionPixelOffset(R.dimen.mdl_divider_height));
        dividerItemDecoration.setPaddingStart(getResources().getDimensionPixelOffset(R.dimen.mdl_vertical_margin_half));
        dataRv.addItemDecoration(dividerItemDecoration);
        adapter = new DataTestRecycleAdapter(dataRv);
        adapter.setOnRVItemClickListener(new OnRvItemClickListener());
        adapter.setOnItemChildClickListener(new OnRvItemChildClickListener());
        dataRv.addOnScrollListener(new BGAAppBarScrollListener(appBar, refreshLayout, refreshViewHolder, dataRv, new BGAAppBarDelegateListener()));
        dataRv.setAdapter(adapter);
        testDataInit();
    }

    private void testDataInit() {
        datas = new ArrayList<>();
        for (int i = 0; i < PAGE_SIZE; i++) {
            datas.add(new DataTestItem(title + i, content));
        }
        adapter.addNewDatas(datas);
    }

    private void addMoreData() {
        int changeNum = pageNumber * 10;
        datas.clear();
        for (int i = 0; i < PAGE_SIZE; i++) {
            datas.add(new DataTestItem(title + (i + changeNum), content));
        }
        adapter.addMoreDatas(datas);
    }

    private void refreshData() {
        pageNumber = 0;
        datas.clear();
        for (int i = 0; i < PAGE_SIZE; i++) {
            datas.add(new DataTestItem(title + i, content));
        }
        adapter.clear();
        adapter.addNewDatas(datas);
    }

    private void initView() {
        title = getString(R.string.str_test_title);
        content = getString(R.string.str_test_content);
        initToolBar();
        initRefreshView();
        initFloatingButton();
    }


    private class OnRvItemClickListener implements BGAOnRVItemClickListener {
        @Override
        public void onRVItemClick(ViewGroup viewGroup, View view, int i) {
//            Log.d(TAG, "测试自定义 onRVItemClick 被调用 id: " + i);
        }
    }

    private class OnRvItemChildClickListener implements BGAOnItemChildClickListener {
        @Override
        public void onItemChildClick(ViewGroup viewGroup, View view, int i) {
//            Log.d(TAG, "测试自定义 onItemChildClick 被调用 id: " + i);
        }
    }

    private class OnDefineScrollListener extends RecyclerView.OnScrollListener {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
//            Log.d(TAG, "测试自定义 onScrollStateChanged 被调用");
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//            Log.d(TAG, "测试自定义 onScrolled 被调用");
        }
    }

    private class BGAAppBarDelegateListener implements OnBGAAppBarDelegateListener {
        @Override
        public boolean onBGARefreshLayoutRefreshing(final BGARefreshLayout refreshLayout) {
            ThreadUtil.runInUIThread(new Runnable() {
                @Override
                public void run() {
                    refreshData();
                    refreshLayout.endRefreshing();
                }
            }, 3000l);
            return true;
        }

        @Override
        public boolean onBGARefreshLayoutLoadingMore(final BGARefreshLayout refreshLayout) {
            Log.w(TAG, "测试自定义 onBGARefreshLayout Begin LoadingMore 被调用 pageNumber: " + pageNumber);
            if (pageNumber > PAGE_LAST) {
                refreshLayout.endLoadingMore();
                showToast("没有更多数据了");
                rvFooterView.setVisibility(View.GONE);
                return false;
            } else {
                rvFooterView.setVisibility(View.VISIBLE);
                pageNumber++;
                ThreadUtil.runInUIThread(new Runnable() {
                    @Override
                    public void run() {
                        addMoreData();
                        rvFooterView.setVisibility(View.GONE);
                        refreshLayout.endLoadingMore();
                    }
                }, 3000l);
                return true;
            }
        }
    }
}
