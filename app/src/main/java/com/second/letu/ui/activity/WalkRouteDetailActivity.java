package com.second.letu.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.amap.api.services.route.WalkPath;
import com.amap.api.services.route.WalkRouteResult;
import com.second.letu.R;
import com.second.letu.adapter.WalkSegmentListAdapter;
import com.second.letu.util.AMapUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.second.letu.R.id.lv_bus_route;

/**
 * 公交车的详细路线
 * Created by Jacket_Xu on 2017/3/27.
 */
public class WalkRouteDetailActivity extends Activity {
    @BindView(R.id.ibtn_back)
    ImageButton mIbtnBack;
    @BindView(R.id.tv_map)
    TextView mTvMap;
    @BindView(R.id.firstLine)
    TextView mFirstLine;
    @BindView(R.id.secondLine)
    TextView mSecondLine;
    @BindView(lv_bus_route)
    ListView mLvBusRoute;
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.btn_go)
    Button mBtnGo;
    private WalkPath mWalkPath;
    private WalkRouteResult mWalkRouteResult;
    private WalkSegmentListAdapter mWalkSegmentListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_bus_route_detail);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        //获取数据
        mWalkPath = (WalkPath) intent.getParcelableExtra("walk_path");
        mWalkRouteResult = (WalkRouteResult) intent.getParcelableExtra("walk_result");
        initUI();
    }

    /**
     * 初始化UI界面
     */
    private void initUI() {
        mTvTitle.setText("行走路线详情");
        mTvMap.setVisibility(View.GONE);
        String dur = AMapUtil.getFriendlyTime((int) mWalkPath.getDuration());
        String dis = AMapUtil.getFriendlyLength((int) mWalkPath.getDistance());
        mFirstLine.setText(dur + "(" + dis + ")");
        mSecondLine.setVisibility(View.GONE);
        //设置适配器
        mWalkSegmentListAdapter = new WalkSegmentListAdapter(
                this.getApplicationContext(), mWalkPath.getSteps());
        mLvBusRoute.setAdapter(mWalkSegmentListAdapter);
    }

    @OnClick({R.id.ibtn_back,R.id.btn_go})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ibtn_back:
                finish();
                break;
            case R.id.btn_go:
                Intent intentGo = new Intent(WalkRouteDetailActivity.this,GPSNaviActivity.class);
                intentGo.putExtra("mode", "walk");
                intentGo.putExtra("walk_path", mWalkPath);
                intentGo.putExtra("walk_result", mWalkRouteResult);
                startActivity(intentGo);
                finish();
                break;
        }
    }
}
