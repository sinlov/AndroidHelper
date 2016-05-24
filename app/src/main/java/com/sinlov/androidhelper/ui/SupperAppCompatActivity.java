package com.sinlov.androidhelper.ui;

import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

/**
 * Supper Activity for full project
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
 * Created by sinlov on 16/5/24.
 */
public class SupperAppCompatActivity extends AppCompatActivity {

    /**
     * find view by id which in content view
     *
     * @param id   view id
     * @param <CV> extends {@link View}
     * @return extends view
     */
    @SuppressWarnings("unchecked")
    protected <CV extends View> CV getViewById(@IdRes int id) {
        return (CV) findViewById(id);
    }

    protected void showToast(String msg) {
        Toast.makeText(this.getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }
}
