package com.sinlov.androidhelper.codewidget;

import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.RecyclerView;

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
 * Created by sinlov on 16/5/20.
 */
public class SupportDesignViewUtils {

    /**
     * AppBarLayout 完全显示 打开状态
     *
     * @param verticalOffset vertical offset
     * @return boolean
     */
    public static boolean isAppBarLayoutOpen(int verticalOffset) {
        return verticalOffset >= 0;
    }

    /**
     * AppBarLayout 关闭或折叠状态
     *
     * @param appBarLayout {@link AppBarLayout}
     * @param verticalOffset vertical offset
     * @return boolean
     */
    public static boolean isAppBarLayoutClose(AppBarLayout appBarLayout, int verticalOffset) {
        return appBarLayout.getTotalScrollRange() == Math.abs(verticalOffset);
    }

    /**
     * RecyclerView 滚动到底部 最后一条完全显示
     *
     * @param recyclerView {@link RecyclerView}
     * @return boolean
     */
    public static boolean isSlideToBottom(RecyclerView recyclerView) {
        if (recyclerView == null) return false;
        if (recyclerView.computeVerticalScrollExtent() + recyclerView.computeVerticalScrollOffset() >= recyclerView.computeVerticalScrollRange())
            return true;
        return false;
    }

    /**
     * RecyclerView 滚动到顶端
     *
     * @param recyclerView {@link RecyclerView}
     * @return boolean
     */
    public static boolean isSlideToTop(RecyclerView recyclerView) {
        return recyclerView.computeVerticalScrollOffset() <= 0;
    }
}
