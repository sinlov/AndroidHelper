package com.sinlov.androidhelper.codewidget;

import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

/**
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
public interface OnBGAAppBarDelegateListener {
    boolean onBGARefreshLayoutRefreshing(BGARefreshLayout refreshLayout);

    boolean onBGARefreshLayoutLoadingMore(BGARefreshLayout refreshLayout);
}
