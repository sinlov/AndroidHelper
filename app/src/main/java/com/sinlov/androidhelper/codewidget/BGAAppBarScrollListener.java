package com.sinlov.androidhelper.codewidget;

import android.support.design.widget.AppBarLayout;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;

import cn.bingoogolapple.refreshlayout.BGARefreshLayout;
import cn.bingoogolapple.refreshlayout.BGARefreshViewHolder;

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
public class BGAAppBarScrollListener extends RecyclerView.OnScrollListener implements AppBarLayout.OnOffsetChangedListener, BGARefreshLayout.BGARefreshLayoutDelegate {

    private final OnBGAAppBarDelegateListener bgaAppBarScrollListener;
    private AppBarLayout appBarLayout;
    private BGARefreshLayout refreshLayout;
    private RecyclerView recyclerView;
    private boolean isAppBarLayoutOpen = true;
    private boolean isAppBarLayoutClose;
    private BGARefreshViewHolder refreshViewHolder;

    public BGAAppBarScrollListener(AppBarLayout appBarLayout, BGARefreshLayout refreshLayout, BGARefreshViewHolder refreshViewHolder, RecyclerView recyclerView, OnBGAAppBarDelegateListener bgaAppBarScrollListener) {
        this.appBarLayout = appBarLayout;
        this.refreshLayout = refreshLayout;
        this.refreshViewHolder = refreshViewHolder;
        this.recyclerView = recyclerView;
        this.bgaAppBarScrollListener = bgaAppBarScrollListener;
        dispatchScrollRefresh();
    }

    private void dispatchScrollRefresh() {
        if (null != appBarLayout && null != refreshLayout && null != recyclerView) {
            this.appBarLayout.addOnOffsetChangedListener(this);
            this.refreshLayout.setRefreshViewHolder(refreshViewHolder);
            this.refreshLayout.setDelegate(this);
            this.recyclerView.addOnScrollListener(this);
        }
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        isAppBarLayoutOpen = SupportDesignViewUtils.isAppBarLayoutOpen(verticalOffset);
        isAppBarLayoutClose = SupportDesignViewUtils.isAppBarLayoutClose(appBarLayout, verticalOffset);
//        Log.d("onOffsetChanged", "isAppBarLayoutOpen: " + isAppBarLayoutOpen +
//                " |isAppBarLayoutClose: " + isAppBarLayoutClose +
//                " |verticalOffset: " + verticalOffset);

        dispatchScroll(verticalOffset);
    }

    private void dispatchScroll(int verticalOffset) {
        if (this.recyclerView != null && this.appBarLayout != null && this.refreshLayout != null) {
            //不可滚动
            if (!(ViewCompat.canScrollVertically(recyclerView, -1) || ViewCompat.canScrollVertically(recyclerView, 1))) {
                refreshLayout.setEnabled(isAppBarLayoutOpen);
            } else {
                //可以滚动
                if (isAppBarLayoutOpen || isAppBarLayoutClose) {
                    if (!ViewCompat.canScrollVertically(recyclerView, -1) && isAppBarLayoutOpen) {
                        refreshLayout.setEnabled(true);
//                        refreshLayout.setScrollbarFadingEnabled(true);
                    } else {
                        refreshLayout.setEnabled(false);
//                        refreshLayout.setScrollbarFadingEnabled(false);
                    }
                } else {
                    refreshLayout.setEnabled(false);
//                    refreshLayout.setScrollbarFadingEnabled(false);
                }
            }
        }
    }

    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        appBarLayout.setEnabled(false);
        appBarLayout.setEnabled(bgaAppBarScrollListener.onBGARefreshLayoutRefreshing(refreshLayout));
    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        return bgaAppBarScrollListener.onBGARefreshLayoutLoadingMore(refreshLayout);
    }
}
