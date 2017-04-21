package com.second.letu.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.widget.ListView;

import com.second.letu.R;
import com.second.letu.adapter.OutDataAdapter;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Jacket_Xu on 2017/4/20.
 */

public class OutActivity extends Activity {
    @BindView(R.id.lv_data)
    ListView mLvData;
    private ArrayList<HashMap<String, String>> mData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_outdata);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        mData = (ArrayList<HashMap<String, String>>)intent.getSerializableExtra("outData");
        OutDataAdapter outDataAdapter = new OutDataAdapter(mData);
        mLvData.setAdapter(outDataAdapter);
    }
}
