package com.second.letu.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.amap.api.services.route.BusPath;
import com.amap.api.services.route.BusRouteResult;
import com.second.letu.R;
import com.second.letu.adapter.BusSegmentListAdapter;
import com.second.letu.util.AMapUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 公交车的详细路线
 * Created by Jacket_Xu on 2017/3/27.
 */
public class BusRouteDetailActivity extends Activity {
    @BindView(R.id.ibtn_back)
    ImageButton mIbtnBack;
    @BindView(R.id.tv_map)
    TextView mTvMap;
    @BindView(R.id.firstLine)
    TextView mFirstLine;
    @BindView(R.id.secondLine)
    TextView mSecondLine;
    @BindView(R.id.lv_bus_route)
    ListView mLvBusRoute;
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.btn_go)
    Button mBtnGo;
    private BusPath mBusPath;
    private BusRouteResult mBusRouteResult;
    private BusSegmentListAdapter mDriveSegmentListAdapter;
    private LocalBroadcastManager mLocalBroadcastManager;
    private boolean mIsBottom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_bus_route_detail);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        //获取数据
        mBusPath = (BusPath) intent.getParcelableExtra("bus_path_item");
        mBusRouteResult = (BusRouteResult) intent.getParcelableExtra("mBusRouteResult");
        mIsBottom = intent.getBooleanExtra("bottom", false);
        initBroadcast();
        initUI();
    }

    /**
     * 初始化广播发送
     */
    private void initBroadcast() {
        mLocalBroadcastManager = LocalBroadcastManager.getInstance(BusRouteDetailActivity.this);
    }

    /**
     * 初始化UI界面
     */
    private void initUI() {
        mTvTitle.setText("公交路线详情");
        mTvMap.setVisibility(mIsBottom ? View.GONE : View.VISIBLE);
        String dur = AMapUtil.getFriendlyTime((int) mBusPath.getDuration());
        String dis = AMapUtil.getFriendlyLength((int) mBusPath.getDistance());
        mFirstLine.setText(dur + "(" + dis + ")");
        int taxiCost = (int) mBusRouteResult.getTaxiCost();
        mSecondLine.setText("打车约" + taxiCost + "元");
        //设置适配器
        mDriveSegmentListAdapter = new BusSegmentListAdapter(
                this.getApplicationContext(), mBusPath.getSteps());
        mLvBusRoute.setAdapter(mDriveSegmentListAdapter);
    }

    @OnClick({R.id.ibtn_back, R.id.tv_map,R.id.btn_go})
    public void onClick(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.ibtn_back:
                finish();
                break;
            case R.id.tv_map:
                intent = new Intent("com.second.letu.dismiss");
                intent.putExtra("mBusPath", mBusPath);
                intent.putExtra("mBusRouteResult", mBusRouteResult);
                mLocalBroadcastManager.sendBroadcast(intent);
                finish();
                break;
            case R.id.btn_go:
                intent = new Intent("com.second.letu.navigate");
                intent.putExtra("mBusPath", mBusPath);
                intent.putExtra("mBusRouteResult", mBusRouteResult);
                mLocalBroadcastManager.sendBroadcast(intent);
                finish();
                break;
        }
    }

}
