package com.sinlov.androidhelper.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.sinlov.androidhelper.R;
import com.sinlov.androidhelper.module.DataTestItem;

import cn.bingoogolapple.androidcommon.adapter.BGARecyclerViewAdapter;
import cn.bingoogolapple.androidcommon.adapter.BGARecyclerViewHolder;
import cn.bingoogolapple.androidcommon.adapter.BGAViewHolderHelper;

/**
 * adapter for test data
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
public class DataTestRecycleAdapter extends BGARecyclerViewAdapter<DataTestItem> {

    private static final int TYPE_ITEM = 0;
    private static final int TYPE_FOOTER = 1;

    public DataTestRecycleAdapter(RecyclerView recyclerView) {
        super(recyclerView, R.layout.item_recycle_view_test_data);
    }

    @Override
    protected void fillData(BGAViewHolderHelper helper, int i, DataTestItem dataTestItem) {
        helper.setText(R.id.tv_item_rv_data_test_title, dataTestItem.getTitle());
        helper.setText(R.id.tv_item_rv_data_test_content, dataTestItem.getContent());
    }

    @Override
    public int getItemCount() {
        return mDatas.size() == 0 ? 0 : mDatas.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position + 1 == getItemCount()) {
            return TYPE_FOOTER;
        } else {
            return TYPE_ITEM;
        }
    }

    @Override
    public BGARecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            BGARecyclerViewHolder viewHolder = new BGARecyclerViewHolder(this.mRecyclerView, LayoutInflater.from(this.mContext).inflate(this.mItemLayoutId, parent, false), this.mOnRVItemClickListener, this.mOnRVItemLongClickListener);
            viewHolder.getViewHolderHelper().setOnItemChildClickListener(this.mOnItemChildClickListener);
            viewHolder.getViewHolderHelper().setOnItemChildLongClickListener(this.mOnItemChildLongClickListener);
            viewHolder.getViewHolderHelper().setOnItemChildCheckedChangeListener(this.mOnItemChildCheckedChangeListener);
            this.setItemChildListener(viewHolder.getViewHolderHelper());
            return viewHolder;
        } else if (viewType == TYPE_FOOTER) {
            BGARecyclerViewHolder viewHolder = new BGARecyclerViewHolder(this.mRecyclerView, LayoutInflater.from(this.mContext).inflate(R.layout.rv_refresh_footer, parent, false), this.mOnRVItemClickListener, this.mOnRVItemLongClickListener);
            return viewHolder;
        }
        return null;
    }
}
