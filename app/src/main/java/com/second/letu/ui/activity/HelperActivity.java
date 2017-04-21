package com.second.letu.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

import com.second.letu.R;

/**
 * Created by Jacket_Xu on 2017/4/20.
 */

public class HelperActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_help);
    }
}
