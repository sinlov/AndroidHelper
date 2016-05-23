package com.sinlov.androidhelper.adapter;

import android.support.v7.widget.RecyclerView;

import com.sinlov.androidhelper.R;
import com.sinlov.androidhelper.module.DataTestItem;

import cn.bingoogolapple.androidcommon.adapter.BGARecyclerViewAdapter;
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

    public DataTestRecycleAdapter(RecyclerView recyclerView) {
        super(recyclerView, R.layout.item_recycle_view_test_data);
    }

    @Override
    protected void fillData(BGAViewHolderHelper helper, int i, DataTestItem dataTestItem) {
        helper.setText(R.id.tv_item_rv_data_test_title, dataTestItem.getTitle());
        helper.setText(R.id.tv_item_rv_data_test_content, dataTestItem.getContent());
    }
}
