package com.second.letu.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

import com.amap.api.navi.AMapNaviView;
import com.amap.api.navi.enums.NaviType;
import com.amap.api.navi.model.NaviLatLng;
import com.amap.api.services.core.AMapException;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeQuery;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.amap.api.services.route.DrivePath;
import com.amap.api.services.route.DriveRouteResult;
import com.amap.api.services.route.WalkPath;
import com.amap.api.services.route.WalkRouteResult;
import com.second.letu.ConstantValue.ConstantValues;
import com.second.letu.R;
import com.second.letu.util.SPUtil;
import com.second.letu.util.SQLiteUtil;
import com.second.letu.util.SensorUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * GPS导航界面
 */
public class GPSNaviActivity extends BaseActivity implements GeocodeSearch.OnGeocodeSearchListener {

    @BindView(R.id.btn_jam)
    Button mBtnJam;
    @BindView(R.id.webview)
    WebView mWebview;
    private WalkPath mWalkPath;
    private WalkRouteResult mWalkRouteResult;
    private DrivePath mDrivePath;
    private DriveRouteResult mDriveRouteResult;
    private LatLonPoint mStartPos;
    private LatLonPoint mTargetPos;
    private String mMode;
    private SensorUtil mSensorUtil;
    private boolean mIsSecondPoi;
    private GeocodeSearch geocoderSearch;
    private String mEndPoiName;
    private String mStartPoiName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic_navi);
        ButterKnife.bind(this);
        mWebview.getSettings().setJavaScriptEnabled(true);
        mWebview.getSettings().setSupportZoom(true);//设定支持缩放
        mWebview.setWebViewClient(new WebViewClient());
        mWebview.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        //获取数据
        Intent intent = getIntent();
        mMode = intent.getStringExtra("mode");
        //启动传感器
        mSensorUtil = SensorUtil.getInstance();
        mSensorUtil.initSensor(mBtnJam,mMode);
        if (mMode.equals("drive")) {
            //驾车模式
            mDrivePath = (DrivePath) intent.getParcelableExtra("drive_path");
            mDriveRouteResult = (DriveRouteResult) intent.getParcelableExtra("drive_result");
            mStartPos = mDriveRouteResult.getStartPos();
            mTargetPos = mDriveRouteResult.getTargetPos();
        } else if (mMode.equals("walk")) {
            //行走模式
            mWalkPath = (WalkPath) intent.getParcelableExtra("walk_path");
            mWalkRouteResult = (WalkRouteResult) intent.getParcelableExtra("walk_result");
            mStartPos = mWalkRouteResult.getStartPos();
            mTargetPos = mWalkRouteResult.getTargetPos();
        }

        //存储路线
        saveRoute();


        mStartLatlng = new NaviLatLng(mStartPos.getLatitude(), mStartPos.getLongitude());
        mEndLatlng = new NaviLatLng(mTargetPos.getLatitude(), mTargetPos.getLongitude());
        sList.add(mStartLatlng);
        eList.add(mEndLatlng);

        mAMapNaviView = (AMapNaviView) findViewById(R.id.navi_view);
        mAMapNaviView.onCreate(savedInstanceState);
        mAMapNaviView.setAMapNaviViewListener(this);
    }

    private void saveRoute() {
        mIsSecondPoi = false;
        geocoderSearch = new GeocodeSearch(this);
        geocoderSearch.setOnGeocodeSearchListener(GPSNaviActivity.this);
        RegeocodeQuery query = new RegeocodeQuery(mStartPos, 200,
                GeocodeSearch.AMAP);// 第一个参数表示一个Latlng，第二参数表示范围多少米，第三个参数表示是火系坐标系还是GPS原生坐标系
        geocoderSearch.getFromLocationAsyn(query);// 设置异步逆地理编码请求
    }

    @Override
    public void onInitNaviSuccess() {
        super.onInitNaviSuccess();
        /**
         * 方法: int strategy=mAMapNavi.strategyConvert(congestion, avoidhightspeed, cost, hightspeed, multipleroute); 参数:
         * @congestion 躲避拥堵
         * @avoidhightspeed 不走高速
         * @cost 避免收费
         * @hightspeed 高速优先
         * @multipleroute 多路径
         *  说明: 以上参数都是boolean类型，其中multipleroute参数表示是否多条路线，如果为true则此策略会算出多条路线。
         *  注意: 不走高速与高速优先不能同时为true 高速优先与避免收费不能同时为true
         */
        if (mMode.equals("drive")) {
            int strategy = 0;
            try {
                //再次强调，最后一个参数为true时代表多路径，否则代表单路径
                strategy = mAMapNavi.strategyConvert(true, false, false, false, false);
            } catch (Exception e) {
                e.printStackTrace();
            }
            mAMapNavi.calculateDriveRoute(sList, eList, mWayPointList, strategy);
        } else if (mMode.equals("walk")) {
            mAMapNavi.calculateWalkRoute(sList.get(0), eList.get(0));
        }
    }

    @Override
    public void onCalculateRouteSuccess() {
        super.onCalculateRouteSuccess();
        mAMapNavi.startNavi(NaviType.GPS);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mSensorUtil.unRegisterSensor();
    }

    @OnClick(R.id.btn_jam)
    public void onClick() {
        int score = SPUtil.getInt(ConstantValues.SCORE, 1);
        SPUtil.putInt(ConstantValues.SCORE,score + 1);
        mWebview.setVisibility(View.VISIBLE);
        mWebview.loadUrl("http://155.94.144.153/by/wp-admin/post-new.php");
        //停止速度传感器
        if (mSensorUtil != null) {
            mSensorUtil.unRegisterSensor();
        }
    }

    @Override
    public void onBackPressed() {
        if(mWebview.getVisibility() == View.VISIBLE) {
            mWebview.setVisibility(View.GONE);
            mBtnJam.setVisibility(View.GONE);
            //重新启动速度传感器
            if (mSensorUtil != null) {
                mSensorUtil.registerSensor();
            }
        } else {
            finish();
        }
    }

    @Override
    public void onRegeocodeSearched(RegeocodeResult result, int rCode) {
        if (rCode == AMapException.CODE_AMAP_SUCCESS) {
            if (result != null && result.getRegeocodeAddress() != null
                    && result.getRegeocodeAddress().getFormatAddress() != null) {
                if(mIsSecondPoi) {
                    mEndPoiName = result.getRegeocodeAddress().getFormatAddress();
                    SQLiteUtil sqLiteUtil = SQLiteUtil.getInstance();
                    sqLiteUtil.insertData(mStartPoiName, mEndPoiName);
                    System.out.println("mStartPoiName:"+ mStartPoiName +",mEndPoiName:" + mEndPoiName);
                    mIsSecondPoi = false;
                } else {
                    mIsSecondPoi = true;
                    mStartPoiName = result.getRegeocodeAddress().getFormatAddress();
                    RegeocodeQuery query = new RegeocodeQuery(mTargetPos, 200,
                            GeocodeSearch.AMAP);// 第一个参数表示一个Latlng，第二参数表示范围多少米，第三个参数表示是火系坐标系还是GPS原生坐标系
                    geocoderSearch.getFromLocationAsyn(query);// 设置异步逆地理编码请求

                }
            }
        }
    }

    @Override
    public void onGeocodeSearched(GeocodeResult geocodeResult, int i) {

    }
}
