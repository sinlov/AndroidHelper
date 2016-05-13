package com.sinlov.androidhelper.codewidget;

/*
 * Copyright (C) 2014 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * This class is from the v7 samples of the Android SDK. It's not by me!
 * <p/>
 * See the license above for details.
 */
public class DividerItemDecoration extends RecyclerView.ItemDecoration {

    private static final int[] ATTRS = new int[]{android.R.attr.listDivider};

    public static final int HORIZONTAL = LinearLayoutManager.HORIZONTAL;

    public static final int VERTICAL = LinearLayoutManager.VERTICAL;

    private static final int COLOR_DEFAULT = 0xFF777777;

    private Paint paint;

    private Drawable divider;

    private int orientation;

    private int color = COLOR_DEFAULT;

    private int size = 1;

    private int paddingStart = 0;

    private int paddingEnd = 0;

    private boolean isShowFirstItemDecoration = true;
    private boolean isShowSecondItemDecoration = true;
    private boolean isShowLastItemDecoration = false;

    public DividerItemDecoration(Context context, int orientation) {
        final TypedArray array = context.obtainStyledAttributes(ATTRS);
        divider = array.getDrawable(0);
        array.recycle();

        paint = new Paint();
        setOrientation(orientation);
    }

    public void setOrientation(int orientation) {
        if (orientation != HORIZONTAL && orientation != VERTICAL) {
            throw new IllegalArgumentException("invalid orientation");
        }
        this.orientation = orientation;
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        if (orientation == VERTICAL) {
            drawVertical(c, parent);
        } else {
            drawHorizontal(c, parent);
        }
    }

    /**
     * draw vertical list divider line
     *
     * @param c      canvas
     * @param parent RecyclerView
     */
    protected void drawVertical(Canvas c, RecyclerView parent) {
        final int left = parent.getPaddingLeft() + paddingStart;
        final int right = parent.getWidth() - parent.getPaddingRight() - paddingEnd;

        final int childCount = parent.getChildCount();
        LinearLayoutManager layoutManager = getLinearLayoutManager(parent);
        if (null == layoutManager) {
            return;
        }
        int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);
//            android.support.v7.widget.RecyclerView v = new android.support.v7.widget.RecyclerView(
//                    parent.getContext());
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                    .getLayoutParams();
            final int top = child.getBottom() + params.bottomMargin;
            final int bottom = top + divider.getIntrinsicHeight();
            divider.setBounds(left, top, right, bottom);
            if (0 == i && 0 == firstVisibleItemPosition && !isShowFirstItemDecoration) {
                divider.setBounds(0, 0, 0, 0);
            } else if (1 == i && 0 == firstVisibleItemPosition && !isShowSecondItemDecoration) {
                divider.setBounds(0, 0, 0, 0);
            } else if (0 == i && 0 == firstVisibleItemPosition && !isShowSecondItemDecoration) {
                divider.setBounds(0, 0, 0, 0);
            } else if (childCount - 1 == i && !isShowLastItemDecoration) {
                divider.setBounds(0, 0, 0, 0);
            } else {
                c.drawRect(left, top - size / 2, right, bottom + size / 2, paint);
            }
            divider.draw(c);
        }
    }

    /**
     * draw vertical list divider line
     *
     * @param c      canvas
     * @param parent RecyclerView
     */
    protected void drawHorizontal(Canvas c, RecyclerView parent) {
        final int top = parent.getPaddingTop();
        final int bottom = parent.getHeight() - parent.getPaddingBottom();

        LinearLayoutManager layoutManager = getLinearLayoutManager(parent);
        if (null == layoutManager) {
            return;
        }
        int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();
        final int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                    .getLayoutParams();
            final int left = child.getRight() + params.rightMargin;
            final int right = left + divider.getIntrinsicHeight();
            divider.setBounds(left, top, right, bottom);
            if (0 == i && 0 == firstVisibleItemPosition && !isShowFirstItemDecoration) {
                divider.setBounds(0, 0, 0, 0);
            } else if (1 == i && 0 == firstVisibleItemPosition && !isShowSecondItemDecoration) {
                divider.setBounds(0, 0, 0, 0);
            } else if (0 == i && 0 == firstVisibleItemPosition && !isShowSecondItemDecoration) {
                divider.setBounds(0, 0, 0, 0);
            } else if (childCount - 1 == i && !isShowLastItemDecoration) {
                divider.setBounds(0, 0, 0, 0);
            } else {
                c.drawRect(left - size / 2, top, right + size / 2, bottom, paint);
            }
            divider.draw(c);
        }
    }

    private LinearLayoutManager getLinearLayoutManager(RecyclerView recyclerView) {
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager instanceof LinearLayoutManager) {
            return (LinearLayoutManager) layoutManager;
        }
        return null;
    }

    public void setColor(int color) {
        this.color = color;
        paint.setColor(this.color);
    }

    public void setPaddingEnd(int paddingEnd) {
        this.paddingEnd = paddingEnd;
    }

    public void setPaddingStart(int paddingStart) {
        this.paddingStart = paddingStart;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public void setIsShowFirstItemDecoration(boolean isShowFirstItemDecoration) {
        this.isShowFirstItemDecoration = isShowFirstItemDecoration;
    }

    public boolean isShowFirstItemDecoration() {
        return isShowFirstItemDecoration;
    }

    public boolean isShowSecondItemDecoration() {
        return isShowSecondItemDecoration;
    }

    public void setIsShowSecondItemDecoration(boolean isShowSecondItemDecoration) {
        this.isShowSecondItemDecoration = isShowSecondItemDecoration;
    }

    public boolean isShowLastItemDecoration() {
        return isShowLastItemDecoration;
    }

    public void setIsShowLastItemDecoration(boolean isShowLastItemDecoration) {
        this.isShowLastItemDecoration = isShowLastItemDecoration;
    }
}
