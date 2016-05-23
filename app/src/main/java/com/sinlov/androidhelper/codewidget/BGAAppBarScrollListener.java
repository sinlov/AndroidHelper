package com.sinlov.androidhelper.codewidget;

import android.support.design.widget.AppBarLayout;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

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
 * Created by sinlov on 16/5/23.
 */
public class BGAAppBarScrollListener extends RecyclerView.OnScrollListener implements AppBarLayout.OnOffsetChangedListener {

    private AppBarLayout appBarLayout;
    private BGARefreshLayout refreshLayout;
    private RecyclerView recyclerView;
    private boolean isAppBarLayoutOpen = true;
    private boolean isAppBarLayoutClose;

    public BGAAppBarScrollListener(AppBarLayout appBarLayout, BGARefreshLayout refreshLayout, RecyclerView recyclerView) {
        this.appBarLayout = appBarLayout;
        this.refreshLayout = refreshLayout;
        this.recyclerView = recyclerView;
        dispatchScrollRefresh();
    }

    private void dispatchScrollRefresh() {
        if (null != appBarLayout && null != refreshLayout && null != recyclerView) {
            this.appBarLayout.addOnOffsetChangedListener(this);
            this.recyclerView.addOnScrollListener(this);
        }
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        isAppBarLayoutOpen = SupportDesignViewUtils.isAppBarLayoutOpen(verticalOffset);
        isAppBarLayoutClose = SupportDesignViewUtils.isAppBarLayoutClose(appBarLayout, verticalOffset);
        Log.d("onOffsetChanged", "isAppBarLayoutOpen: " + isAppBarLayoutOpen + " |isAppBarLayoutClose: " + isAppBarLayoutClose);
        dispatchScroll();
    }

    private void dispatchScroll() {
        if (this.recyclerView != null && this.appBarLayout != null && this.refreshLayout != null) {
            //不可滚动
            if (!(ViewCompat.canScrollVertically(recyclerView, -1) || ViewCompat.canScrollVertically(recyclerView, 1))) {
                Log.d("dispatchScroll", "isAppBarLayoutOpen: " + isAppBarLayoutOpen);
//                refreshLayout.setEnabled(isAppBarLayoutOpen);
                refreshLayout.setScrollbarFadingEnabled(isAppBarLayoutOpen);
            } else {
                //可以滚动
                if (isAppBarLayoutOpen || isAppBarLayoutClose) {
                    if (!ViewCompat.canScrollVertically(recyclerView, -1) && isAppBarLayoutOpen) {
                        Log.d("dispatchScroll", "refreshLayout true");
//                        refreshLayout.setEnabled(true);
                        refreshLayout.setScrollbarFadingEnabled(true);
                    } else if (isAppBarLayoutClose && !ViewCompat.canScrollVertically(recyclerView, 1)) {
                        Log.d("dispatchScroll", "refreshLayout true");
//                        refreshLayout.setEnabled(true);
                        refreshLayout.setScrollbarFadingEnabled(true);
                    } else {
                        Log.d("dispatchScroll", "refreshLayout false");
//                        refreshLayout.setEnabled(false);
                        refreshLayout.setScrollbarFadingEnabled(false);
                    }
                } else {
                    Log.d("dispatchScroll", "refreshLayout false");
//                    refreshLayout.setEnabled(false);
                    refreshLayout.setScrollbarFadingEnabled(false);
                }
            }
        }
    }
}
