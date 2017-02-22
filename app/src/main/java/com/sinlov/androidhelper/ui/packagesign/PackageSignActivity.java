package com.sinlov.androidhelper.ui.packagesign;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.sinlov.androidhelper.R;
import com.sinlov.androidhelper.ui.SupperAppCompatActivity;
import com.sinlov.androidhelper.utils.APKSignUtils;
import com.sinlov.androidhelper.utils.InputMethodUtils;

public class PackageSignActivity extends SupperAppCompatActivity {

    private TextView tvSelfResult;
    private TextView tvResult;
    private EditText etPackageNameInput;
    private StringBuffer sb = new StringBuffer();
    private static final String TAG = "Sign";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_package_sign);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tvSelfResult = (TextView) findViewById(R.id.tv_act_package_sign_self_result);
        tvResult = (TextView) findViewById(R.id.tv_act_package_sign_result);
        etPackageNameInput = (EditText) findViewById(R.id.et_act_package_sign_input);
        String self = "This apk self faceBookSignature:\n" + getSelfSign();
        Log.w(TAG, self);
        tvSelfResult.setText(self);
    }

    private String getSelfSign() {
        return APKSignUtils.faceBookSignature(this.getApplication(), getPackageName());
    }

    public void onActPackageSignShowInput(View view) {
        String inputPackage = etPackageNameInput.getText().toString().trim();
        sb.setLength(0);
        if (TextUtils.isEmpty(inputPackage)) {
            sb.append("You input is empty!");
        } else {
            String installPackageSignature = APKSignUtils.faceBookSignature(this.getApplication(), inputPackage);
            if (TextUtils.isEmpty(installPackageSignature)) {
                sb.append("check your apk is install\nPackageName is:\n").append(inputPackage);
            } else {
                sb.append("Your check package is:\n").append(inputPackage);
                sb.append("package signature is:\n").append(installPackageSignature);
                InputMethodUtils.closeInputPan(this);
            }
        }
        Log.w(TAG, sb.toString());
        tvResult.setText(sb.toString());
    }
}
