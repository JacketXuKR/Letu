package com.second.letu.ui.activity;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.LocalBroadcastManager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.BitmapDescriptor;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.services.core.AMapException;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeQuery;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.amap.api.services.help.Inputtips;
import com.amap.api.services.help.InputtipsQuery;
import com.amap.api.services.help.Tip;
import com.amap.api.services.route.BusPath;
import com.amap.api.services.route.BusRouteResult;
import com.amap.api.services.route.DrivePath;
import com.amap.api.services.route.DriveRouteResult;
import com.amap.api.services.route.RideRouteResult;
import com.amap.api.services.route.RouteSearch;
import com.amap.api.services.route.WalkPath;
import com.amap.api.services.route.WalkRouteResult;
import com.second.letu.ConstantValue.ConstantValues;
import com.second.letu.R;
import com.second.letu.adapter.BusResultListAdapter;
import com.second.letu.helper.SensorEventHelper;
import com.second.letu.ui.overlay.BusRouteOverlay;
import com.second.letu.ui.overlay.DrivingRouteOverlay;
import com.second.letu.ui.overlay.WalkRouteOverlay;
import com.second.letu.util.AMapUtil;
import com.second.letu.util.SPUtil;
import com.second.letu.util.SQLiteUtil;
import com.second.letu.util.SensorUtil;
import com.second.letu.util.UIUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 导航界面
 * Created by Jacket_Xu on 2017/3/24.
 */

public class NavigateActivity extends FragmentActivity implements RouteSearch.OnRouteSearchListener, AMap.OnMapClickListener, AMap.OnMarkerClickListener, AMap.OnInfoWindowClickListener, AMap.InfoWindowAdapter, TextWatcher, Inputtips.InputtipsListener, LocationSource, AMapLocationListener, GeocodeSearch.OnGeocodeSearchListener {
    @BindView(R.id.et_start)
    AutoCompleteTextView mEtStart;
    @BindView(R.id.ll_start)
    LinearLayout mLlStart;
    @BindView(R.id.et_end)
    AutoCompleteTextView mEtEnd;
    @BindView(R.id.ll_end)
    LinearLayout mLlEnd;
    @BindView(R.id.tv_search)
    TextView mTvSearch;
    @BindView(R.id.ll_fromto)
    LinearLayout mLlFromto;
    @BindView(R.id.rl_route_drive_normal)
    RelativeLayout mRlRouteDriveNormal;
    @BindView(R.id.rl_route_bus_normal)
    RelativeLayout mRlRouteBusNormal;
    @BindView(R.id.rl_route_walk_normal)
    RelativeLayout mRlRouteWalkNormal;
    @BindView(R.id.ll_third_way)
    LinearLayout mLlThirdWay;
    @BindView(R.id.lv_route)
    ListView mLvRoute;
    @BindView(R.id.route_map)
    MapView mRouteMap;
    @BindView(R.id.firstline)
    TextView mFirstline;
    @BindView(R.id.secondline)
    TextView mSecondline;
    @BindView(R.id.detail)
    LinearLayout mDetail;
    @BindView(R.id.bottom_layout)
    RelativeLayout mBottomLayout;
    @BindView(R.id.iv_drive)
    ImageView mIvDrive;
    @BindView(R.id.iv_bus)
    ImageView mIvBus;
    @BindView(R.id.iv_walk)
    ImageView mIvWalk;
    @BindView(R.id.lv_search)
    ListView mLvSearch;
    @BindView(R.id.btn_jam)
    Button mBtnJam;
    private AMap aMap;
    private RouteSearch mRouteSearch;
    private static final int ROUTE_TYPE_BUS = 1;//公交
    private static final int ROUTE_TYPE_DRIVE = 2;//轿车
    private static final int ROUTE_TYPE_WALK = 3;//走路
    private static final int STROKE_COLOR = Color.argb(180, 3, 145, 255);
    private static final int FILL_COLOR = Color.argb(80, 3, 145, 255);
    private ProgressDialog progDialog;
    private LatLonPoint mStartPoint;//起点
    private LatLonPoint mEndPoint;//终点
    private String mCity;
    private ArrayList<HashMap<String, String>> mListSearchResult;
    private SimpleAdapter mSimpleAdapter;
    private UiSettings uiSettings;
    private OnLocationChangedListener mListener;
    private AMapLocationClient mlocationClient;
    private AMapLocationClientOption mLocationOption;
    private boolean mFirstFix = false;
    private Marker mLocMarker;
    private SensorEventHelper mSensorHelper;
    private String mNewText;
    private BusRouteResult mBusRouteResult;
    private DriveRouteResult mDriveRouteResult;
    private WalkRouteResult mWalkRouteResult;
    private IntentFilter mFilter;
    private LocalBroadcastManager mLocalBroadcastManager;
    private MyBroadcastReceiver mReceiver;
    private BusRouteOverlay mBusrouteOverlay;
    //出行方式状态
    private int currentWay = ROUTE_TYPE_BUS;
    private BusPath mBusPathFromBroadcast;
    private DrivePath mDrivePath;
    private WalkPath mWalkPath;
    private SensorUtil mSensorUtil;
    private boolean mIsPoi;//是否从附近界面过来
    private GeocodeSearch geocoderSearch;
    private String mEnd;
    private boolean mIsSecondPoi;
    private LatLonPoint mTargetPos;
    private LatLonPoint mStartPos;
    private String mStartPoiName;
    private String mEndPoiName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_navigate);
        ButterKnife.bind(this);
        mRouteMap.onCreate(savedInstanceState);// 此方法必须重写
        initIntent();
        init();
        initBroadcast();
    }

    /**
     * 初始化AMap对象
     */
    private void init() {
        //设置输入框改变监听器
        mEtStart.addTextChangedListener(this);
        mEtEnd.addTextChangedListener(this);
        //隐藏出行方式
        mLlThirdWay.setVisibility(View.GONE);
        if (aMap == null) {
            aMap = mRouteMap.getMap();
            setUpMap();//设置一些amap的属性
        }
        //设置方向传感器
        mSensorHelper = new SensorEventHelper(this);
        if (mSensorHelper != null) {
            mSensorHelper.registerSensorListener();
        }
        //地图注册监听器
        registerListener();
        mRouteSearch = new RouteSearch(this);
        mRouteSearch.setRouteSearchListener(this);
    }

    /**
     * 初始化广播
     */
    private void initBroadcast() {
        mLocalBroadcastManager = LocalBroadcastManager.getInstance(NavigateActivity.this);
        mFilter = new IntentFilter();
        //添加接收的action
        mFilter.addAction("com.second.letu.dismiss");
        mFilter.addAction("com.second.letu.navigate");
        mReceiver = new MyBroadcastReceiver();
        //注册广播
        mLocalBroadcastManager.registerReceiver(mReceiver, mFilter);
    }

    /**
     * 初始化获取Intent
     */
    private void initIntent() {
        Intent getIntent = getIntent();
        mIsPoi = getIntent.getBooleanExtra(ConstantValues.ISPOILITEM, false);
        //从附近界面切换过来的
        if(mIsPoi) {
            String poilitemName = (String)getIntent.getStringExtra(ConstantValues.POILITEM_NAME);
            LatLonPoint latLonPoint = (LatLonPoint)getIntent.getParcelableExtra(ConstantValues.POILITEM);
            mEtEnd.setText(poilitemName);
            mEndPoint = latLonPoint;
            currentWay = ROUTE_TYPE_WALK;
        }
    }

    /**
     * 设置一些amap的属性
     */
    private void setUpMap() {
        aMap.setLocationSource(this);// 设置定位监听
        uiSettings = aMap.getUiSettings();
        uiSettings.setMyLocationButtonEnabled(true);// 设置默认定位按钮是否显示
        aMap.setMyLocationEnabled(true);// 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false
        // 设置定位的类型为定位模式 ，可以由定位、跟随或地图根据面向方向旋转几种
        aMap.setMyLocationType(AMap.LOCATION_TYPE_LOCATE);
        //设置地图默认的比例尺显示
        uiSettings.setScaleControlsEnabled(true);
        //设置地图不能旋转
        uiSettings.setRotateGesturesEnabled(false);
    }

    /**
     * 注册监听
     */
    private void registerListener() {
        aMap.setOnMapClickListener(NavigateActivity.this);
        aMap.setOnMarkerClickListener(NavigateActivity.this);
        aMap.setOnInfoWindowClickListener(NavigateActivity.this);
        aMap.setInfoWindowAdapter(NavigateActivity.this);
    }

    private void setfromandtoMarker() {
        aMap.addMarker(new MarkerOptions()
                .position(AMapUtil.convertToLatLng(mStartPoint))
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.start)));
        aMap.addMarker(new MarkerOptions()
                .position(AMapUtil.convertToLatLng(mEndPoint))
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.end)));
    }

    @OnClick({R.id.tv_search, R.id.rl_route_drive_normal, R.id.rl_route_bus_normal, R.id.rl_route_walk_normal, R.id.bottom_layout,R.id.btn_jam})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_search:
                if (mSensorUtil != null) {
                    mSensorUtil.unRegisterSensor();
                }
                mBtnJam.setVisibility(View.GONE);
                //点击搜索
                String start = mEtStart.getText().toString();
                mEnd = mEtEnd.getText().toString();
                if (mEndPoint == null) {
                    Toast.makeText(this, "请设置具体终点", Toast.LENGTH_SHORT).show();
                } else {
                    if (TextUtils.isEmpty(start.trim())) {
                        //没有输入起点
                        float latitude = SPUtil.getFloat(ConstantValues.LATITUDE, 0f);
                        float longitude = SPUtil.getFloat(ConstantValues.LONGITUDE, 0f);
                        mStartPoint = new LatLonPoint(latitude, longitude);
                    }
                    //起点终点都有输入（标点、路线、导航）
                    mLlThirdWay.setVisibility(View.VISIBLE);//三个出行方式显示
                    //不是从附近界面切换过来的
                    if(!mIsPoi) {
                        //设置显示bus路线
                        searchRouteResult(ROUTE_TYPE_BUS, RouteSearch.BusDefault);
                        mIvDrive.setImageResource(R.drawable.route_drive_normal);
                        mIvBus.setImageResource(R.drawable.route_bus_select);
                        mIvWalk.setImageResource(R.drawable.route_walk_normal);
                        mRouteMap.setVisibility(View.GONE);
                        mLvRoute.setVisibility(View.VISIBLE);
                    } else {
                        //从附近界面切换过来
                        searchRouteResult(ROUTE_TYPE_WALK, RouteSearch.WalkDefault);
                        mIvDrive.setImageResource(R.drawable.route_drive_normal);
                        mIvBus.setImageResource(R.drawable.route_bus_normal);
                        mIvWalk.setImageResource(R.drawable.route_walk_select);
                        mRouteMap.setVisibility(View.VISIBLE);
                        mLvRoute.setVisibility(View.GONE);
                    }
                }
                break;
            case R.id.rl_route_drive_normal:
                if (mSensorUtil != null) {
                    mSensorUtil.unRegisterSensor();
                }
                mBtnJam.setVisibility(View.GONE);

                currentWay = ROUTE_TYPE_DRIVE;
                searchRouteResult(ROUTE_TYPE_DRIVE, RouteSearch.DrivingDefault);
                //设置显示图片的颜色
                mIvDrive.setImageResource(R.drawable.route_drive_select);
                mIvBus.setImageResource(R.drawable.route_bus_normal);
                mIvWalk.setImageResource(R.drawable.route_walk_normal);
                mRouteMap.setVisibility(View.VISIBLE);
                mLvRoute.setVisibility(View.GONE);
                break;
            case R.id.rl_route_bus_normal:
                if (mSensorUtil != null) {
                    mSensorUtil.unRegisterSensor();
                }
                mBtnJam.setVisibility(View.GONE);

                currentWay = ROUTE_TYPE_BUS;
                searchRouteResult(ROUTE_TYPE_BUS, RouteSearch.BusDefault);
                mIvDrive.setImageResource(R.drawable.route_drive_normal);
                mIvBus.setImageResource(R.drawable.route_bus_select);
                mIvWalk.setImageResource(R.drawable.route_walk_normal);
                mRouteMap.setVisibility(View.GONE);
                mLvRoute.setVisibility(View.VISIBLE);
                break;
            case R.id.rl_route_walk_normal:
                if (mSensorUtil != null) {
                    mSensorUtil.unRegisterSensor();
                }
                mBtnJam.setVisibility(View.GONE);

                currentWay = ROUTE_TYPE_WALK;
                searchRouteResult(ROUTE_TYPE_WALK, RouteSearch.WalkDefault);
                mIvDrive.setImageResource(R.drawable.route_drive_normal);
                mIvBus.setImageResource(R.drawable.route_bus_normal);
                mIvWalk.setImageResource(R.drawable.route_walk_select);
                mRouteMap.setVisibility(View.VISIBLE);
                mLvRoute.setVisibility(View.GONE);
                break;
            case R.id.bottom_layout:
                Intent intent = null;
                switch (currentWay) {
                    case ROUTE_TYPE_DRIVE:
                        //包含所有路径的信息
                        intent = new Intent(NavigateActivity.this, DriveRouteDetailActivity.class);
                        intent.putExtra("drive_path", mDrivePath);
                        intent.putExtra("drive_result", mDriveRouteResult);
                        startActivity(intent);
                        break;
                    case ROUTE_TYPE_BUS:
                        intent = new Intent(NavigateActivity.this, BusRouteDetailActivity.class);
                        //包含所有路径的信息
                        intent.putExtra("bus_path_item", mBusPathFromBroadcast);
                        intent.putExtra("mBusRouteResult", mBusRouteResult);
                        intent.putExtra("bottom", true);
                        //如果没有包含该活动的栈，则创建新的栈
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        break;
                    case ROUTE_TYPE_WALK:
                        intent = new Intent(NavigateActivity.this, WalkRouteDetailActivity.class);
                        intent.putExtra("walk_path", mWalkPath);
                        intent.putExtra("walk_result", mWalkRouteResult);
                        startActivity(intent);
                        break;
                }
                break;
            case R.id.btn_jam:
                int score = SPUtil.getInt(ConstantValues.SCORE, 1);
                SPUtil.putInt(ConstantValues.SCORE,score + 1);
                Intent intentWeb = new Intent(this,WebViewActivity.class);
                intentWeb.putExtra(ConstantValues.JAM,"NavigateActivity");
                startActivityForResult(intentWeb,1);
                mBtnJam.setVisibility(View.GONE);
                //停止速度传感器
                if (mSensorUtil != null) {
                    mSensorUtil.unRegisterSensor();
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            //点击了btn_jam
            case 1:
                if(resultCode == RESULT_OK) {
                    //重新注册速度传感器
                    if (mSensorUtil != null) {
                        mSensorUtil.registerSensor();
                    }
                }
                break;
        }
    }

    /**
     * 开始搜索路径规划方案
     *
     * @param routeType 出行类型
     * @param mode      出行模式
     */
    public void searchRouteResult(int routeType, int mode) {
        //判断起电终点是否已经实例
        if (mStartPoint == null) {
            Toast.makeText(this, "请确定具体起点", Toast.LENGTH_SHORT).show();
            return;
        }
        if (mEndPoint == null) {
            Toast.makeText(this, "请确定具体终点", Toast.LENGTH_SHORT).show();
            return;
        }
        //显示进度框
        showProgressDialog();
        final RouteSearch.FromAndTo fromAndTo = new RouteSearch.FromAndTo(
                mStartPoint, mEndPoint);
        if (routeType == ROUTE_TYPE_BUS) {// 公交路径规划
            String mCurrentCityName = SPUtil.getString(ConstantValues.CITY, "");
            RouteSearch.BusRouteQuery query = new RouteSearch.BusRouteQuery(fromAndTo, mode,
                    mCurrentCityName, 0);// 第一个参数表示路径规划的起点和终点，第二个参数表示公交查询模式，第三个参数表示公交查询城市区号，第四个参数表示是否计算夜班车，0表示不计算
            mRouteSearch.calculateBusRouteAsyn(query);// 异步路径规划公交模式查询
        } else if (routeType == ROUTE_TYPE_DRIVE) {// 驾车路径规划
            RouteSearch.DriveRouteQuery query = new RouteSearch.DriveRouteQuery(fromAndTo, mode, null,
                    null, "");// 第一个参数表示路径规划的起点和终点，第二个参数表示驾车模式，第三个参数表示途经点，第四个参数表示避让区域，第五个参数表示避让道路
            mRouteSearch.calculateDriveRouteAsyn(query);// 异步路径规划驾车模式查询
        } else if (routeType == ROUTE_TYPE_WALK) {// 步行路径规划
            RouteSearch.WalkRouteQuery query = new RouteSearch.WalkRouteQuery(fromAndTo, mode);
            mRouteSearch.calculateWalkRouteAsyn(query);// 异步路径规划步行模式查询
        }
    }


    /**
     * 显示进度框
     */
    private void showProgressDialog() {
        if (progDialog == null)
            progDialog = new ProgressDialog(this);
        progDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progDialog.setIndeterminate(false);
        progDialog.setCancelable(true);
        progDialog.setMessage("正在搜索");
        progDialog.show();
    }

    /**
     * 隐藏进度框
     */
    private void dismissProgressDialog() {
        if (progDialog != null) {
            progDialog.dismiss();
        }
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onResume() {
        super.onResume();
        mRouteMap.onResume();
        //注册传感器
        if (mSensorHelper != null) {
            mSensorHelper.registerSensorListener();
        } else {
            mSensorHelper = new SensorEventHelper(this);
            if (mSensorHelper != null) {
                mSensorHelper.registerSensorListener();

                if (mSensorHelper.getCurrentMarker() == null && mLocMarker != null) {
                    mSensorHelper.setCurrentMarker(mLocMarker);
                }
            }
        }
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onPause() {
        super.onPause();
        //注销传感器
        if (mSensorHelper != null) {
            mSensorHelper.unRegisterSensorListener();
            mSensorHelper.setCurrentMarker(null);
            mSensorHelper = null;
        }
        mRouteMap.onPause();
        deactivate();
        mFirstFix = false;//
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mRouteMap.onSaveInstanceState(outState);
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mRouteMap.onDestroy();
        if (null != mlocationClient) {
            mlocationClient.onDestroy();
        }
        //注销广播
        mLocalBroadcastManager.unregisterReceiver(mReceiver);
        if (mSensorUtil != null) {
            mSensorUtil.unRegisterSensor();
        }
    }


    ////////////////////RouteSearch.OnRouteSearchListener///////////////////////////////
    @Override
    public void onBusRouteSearched(BusRouteResult result, int errorCode) {
        dismissProgressDialog();
        mBottomLayout.setVisibility(View.GONE);
        //停止定位
        deactivate();
        mFirstFix = false;
        aMap.clear();// 清理地图上的所有覆盖物
        if (errorCode == AMapException.CODE_AMAP_SUCCESS) {
            if (result != null && result.getPaths() != null) {
                if (result.getPaths().size() > 0) {
                    mBusRouteResult = result;
                    BusResultListAdapter mBusResultListAdapter = new BusResultListAdapter(
                            NavigateActivity.this, mBusRouteResult);
                    mLvRoute.setAdapter(mBusResultListAdapter);
                } else if (result != null && result.getPaths() == null) {
                    Toast.makeText(this, "没有匹配的出行路线，请选择其他出行方式", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "没有匹配的出行路线，请选择其他出行方式", Toast.LENGTH_SHORT).show();
            }
        } else {
            Log.d("onBusRouteSearched", errorCode + "");
        }
    }

    @Override
    public void onDriveRouteSearched(DriveRouteResult result, int errorCode) {
        dismissProgressDialog();
        //停止定位
        deactivate();
        mFirstFix = false;
        aMap.clear();// 清理地图上的所有覆盖物
        mLocMarker = null;
        float latitude = SPUtil.getFloat(ConstantValues.LATITUDE, 0f);
        float longitude = SPUtil.getFloat(ConstantValues.LONGITUDE, 0f);
        LatLng latlng = new LatLng(latitude, longitude);
        addMarker(latlng);
        if (errorCode == AMapException.CODE_AMAP_SUCCESS) {
            if (result != null && result.getPaths() != null) {
                if (result.getPaths().size() > 0) {
                    mDriveRouteResult = result;
                    //获取路线(其中的一条)
                    mDrivePath = mDriveRouteResult.getPaths().get(0);
                    //驾车方式的布局
                    DrivingRouteOverlay drivingRouteOverlay = new DrivingRouteOverlay(
                            NavigateActivity.this, aMap, mDrivePath,
                            mDriveRouteResult.getStartPos(),
                            mDriveRouteResult.getTargetPos(), null);
                    drivingRouteOverlay.setNodeIconVisibility(false);//设置节点marker是否显示
                    drivingRouteOverlay.setIsColorfulline(true);//是否用颜色展示交通拥堵情况，默认true
                    drivingRouteOverlay.removeFromMap();
                    drivingRouteOverlay.addToMap();
                    drivingRouteOverlay.zoomToSpan();
                    mBottomLayout.setVisibility(View.VISIBLE);
                    int dis = (int) mDrivePath.getDistance();
                    int dur = (int) mDrivePath.getDuration();
                    String des = AMapUtil.getFriendlyTime(dur) + "("
                            + AMapUtil.getFriendlyLength(dis) + ")";
                    mFirstline.setText(des);
                    //mSecondline.setVisibility(View.VISIBLE);
                    int taxiCost = (int) mDriveRouteResult.getTaxiCost();
                    mSecondline.setVisibility(View.VISIBLE);
                    mSecondline.setText("打车约" + taxiCost + "元");
                } else if (result != null && result.getPaths() == null) {
                    Toast.makeText(this, "没有匹配的出行路线，请选择其他出行方式", Toast.LENGTH_SHORT).show();
                }

            } else {
                Toast.makeText(this, "没有匹配的出行路线，请选择其他出行方式", Toast.LENGTH_SHORT).show();
            }
        } else {
            Log.d("onDriveRouteSearched", errorCode + "");
        }
    }

    @Override
    public void onWalkRouteSearched(WalkRouteResult result, int errorCode) {
        dismissProgressDialog();
        //停止定位
        deactivate();
        mFirstFix = false;
        aMap.clear();// 清理地图上的所有覆盖物
        mLocMarker = null;
        float latitude = SPUtil.getFloat(ConstantValues.LATITUDE, 0f);
        float longitude = SPUtil.getFloat(ConstantValues.LONGITUDE, 0f);
        LatLng latlng = new LatLng(latitude, longitude);
        addMarker(latlng);
        if (errorCode == AMapException.CODE_AMAP_SUCCESS) {
            if (result != null && result.getPaths() != null) {
                if (result.getPaths().size() > 0) {
                    mWalkRouteResult = result;
                    mWalkPath = mWalkRouteResult.getPaths().get(0);
                    WalkRouteOverlay walkRouteOverlay = new WalkRouteOverlay(
                            this, aMap, mWalkPath,
                            mWalkRouteResult.getStartPos(),
                            mWalkRouteResult.getTargetPos());
                    walkRouteOverlay.removeFromMap();
                    walkRouteOverlay.addToMap();
                    walkRouteOverlay.zoomToSpan();
                    mBottomLayout.setVisibility(View.VISIBLE);
                    int dis = (int) mWalkPath.getDistance();
                    int dur = (int) mWalkPath.getDuration();
                    String des = AMapUtil.getFriendlyTime(dur) + "(" + AMapUtil.getFriendlyLength(dis) + ")";
                    mFirstline.setText(des);
                    mSecondline.setVisibility(View.GONE);
                } else if (result != null && result.getPaths() == null) {
                    Toast.makeText(this, "没有匹配的出行路线，请选择其他出行方式", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "没有匹配的出行路线，请选择其他出行方式", Toast.LENGTH_SHORT).show();
            }
        } else {
            Log.d("onWalkRouteSearched", errorCode + "");
        }
    }


    @Override
    public void onRideRouteSearched(RideRouteResult rideRouteResult, int i) {

    }

    @Override
    public void onMapClick(LatLng latLng) {

    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
    }

    @Override
    public void onInfoWindowClick(Marker marker) {

    }

    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {
        return null;
    }

    /////////////////////////输入框的方法/////////////////////////////////////////////
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        //如果输入发生改变，则将地点清空
        if (mEtStart.isFocused()) {
            mStartPoint = null;
        } else if (mEtEnd.isFocused()) {
            mEndPoint = null;
        }
        //获取输入
        mNewText = s.toString().trim();
        String currentName = "";
        mBottomLayout.setVisibility(View.GONE);
        if (mEtStart.isFocused()) {
            currentName = SPUtil.getString(ConstantValues.START_NAME, "");
        } else if (mEtEnd.isFocused()) {
            currentName = SPUtil.getString(ConstantValues.END_NAME, "");
        }
        if (TextUtils.isEmpty(mNewText) || mNewText.equals(currentName)) {
            //输入为空
            mLvSearch.setVisibility(View.GONE);//搜索结果隐藏
            mRouteMap.setVisibility(View.VISIBLE);//地图显示
        } else {
            //获取城市
            mCity = SPUtil.getString(ConstantValues.CITY, "");
            InputtipsQuery inputquery = new InputtipsQuery(mNewText, mCity);
            inputquery.setCityLimit(true);
            Inputtips inputTips = new Inputtips(NavigateActivity.this, inputquery);
            inputTips.setInputtipsListener(this);
            inputTips.requestInputtipsAsyn();
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    /////////////////输入提示的方法////////////////////////////////
    @Override
    public void onGetInputtips(final List<Tip> tipList, int rCode) {
        //当输入框已经没有内容时不需要显示列表
        if (rCode == AMapException.CODE_AMAP_SUCCESS && !TextUtils.isEmpty(mNewText)) {
            mListSearchResult = new ArrayList<HashMap<String, String>>();
            HashMap<String, String> map = null;
            final List<Tip> newTipList = new ArrayList<Tip>();//记录有具体地点的数据
            for (int i = 0; i < tipList.size(); i++) {
                if (!TextUtils.isEmpty(tipList.get(i).getAddress())) {
                    map = new HashMap<String, String>();
                    map.put("name", tipList.get(i).getName());
                    map.put("address", tipList.get(i).getAddress());
                    mListSearchResult.add(map);
                    newTipList.add(tipList.get(i));
                }
            }
            mSimpleAdapter = new SimpleAdapter(UIUtil.getContext(), mListSearchResult, R.layout.search_item_layout,
                    new String[]{"name", "address"}, new int[]{R.id.tv_search_name, R.id.tv_address});
            mLvSearch.setAdapter(mSimpleAdapter);
            mSimpleAdapter.notifyDataSetChanged();
            //设置列表的点击事件
            mLvSearch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    //显示listView
                    mLvSearch.setVisibility(View.GONE);//搜索结果显示
                    //mLlThirdWay.setVisibility(View.VISIBLE);//三个出行方式隐藏
                    mRouteMap.setVisibility(View.VISIBLE);//地图隐藏
                    Tip tip = newTipList.get(position);
                    if (mEtStart.isFocused()) {
                        //存储出发点
                        SPUtil.putString(ConstantValues.START_NAME, tip.getName());
                        mEtStart.setText(tip.getName());
                        mEtStart.setSelection(tip.getName().length());
                        mStartPoint = tip.getPoint();
                    } else if (mEtEnd.isFocused()) {
                        //存储目的地
                        SPUtil.putString(ConstantValues.END_NAME, tip.getName());
                        mEtEnd.setText(tip.getName());
                        mEtEnd.setSelection(tip.getName().length());
                        mEndPoint = tip.getPoint();
                    }
                }
            });
            //显示listView
            mLvSearch.setVisibility(View.VISIBLE);//搜索结果显示
            //mLlThirdWay.setVisibility(View.GONE);//三个出行方式隐藏
            mRouteMap.setVisibility(View.GONE);//地图隐藏
        } else {
            Log.d("onGetInputtips", "输入框获取不到数据");
        }
    }
    ///////////////////////////////////////////////////////////////

    /**
     * 激活定位
     */
    @Override
    public void activate(OnLocationChangedListener listener) {
        mListener = listener;
        if (mlocationClient == null) {
            mlocationClient = new AMapLocationClient(this);
            mLocationOption = new AMapLocationClientOption();
            //设置定位监听
            mlocationClient.setLocationListener(this);
            //设置为高精度定位模式
            mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
            //设置定位参数
            mlocationClient.setLocationOption(mLocationOption);
            // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
            // 注意设置合适的定位时间的间隔（最小间隔支持为2000ms），并且在合适时间调用stopLocation()方法来取消定位请求
            // 在定位结束后，在合适的生命周期调用onDestroy()方法
            // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
            mlocationClient.startLocation();
        }
    }

    /**
     * 停止定位
     */
    @Override
    public void deactivate() {
        mListener = null;
        if (mlocationClient != null) {
            mlocationClient.stopLocation();
            mlocationClient.onDestroy();
        }
        mlocationClient = null;
    }


    /**
     * 定位成功后回调函数
     */
    @Override
    public void onLocationChanged(AMapLocation aLocation) {
        if (mListener != null) {
            mListener.onLocationChanged(aLocation);// 显示系统小蓝点
            //设置地图的缩放比
            LatLng location = new LatLng(aLocation.getLatitude(), aLocation.getLongitude());
            if (!mFirstFix) {
                mFirstFix = true;
                setupLocationStyle();
                addMarker(location);//添加定位图标
                mSensorHelper.setCurrentMarker(mLocMarker);//定位图标旋转
                aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 18));
            } else {
                mLocMarker.setPosition(location);
            }
            //addMarker(location);//添加定位图标
            //存储当前的位置
            SPUtil.putFloat(ConstantValues.LATITUDE, (float) location.latitude);
            SPUtil.putFloat(ConstantValues.LONGITUDE, (float) location.longitude);
            SPUtil.putString(ConstantValues.CITY, aLocation.getCity());
            Log.d("经纬度已经存储:", "SPUtil");
            //aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 18));
        }
    }

    /**
     * 添加Marker
     */
    private void addMarker(LatLng latlng) {
        if (mLocMarker != null) {
            return;
        }
        Bitmap bMap = BitmapFactory.decodeResource(this.getResources(),
                R.drawable.navi_map_gps_locked);
        BitmapDescriptor des = BitmapDescriptorFactory.fromBitmap(bMap);
        MarkerOptions options = new MarkerOptions();
        options.icon(des);
        options.anchor(0.5f, 0.5f);
        options.position(latlng);
        mLocMarker = aMap.addMarker(options);
    }

    /**
     * 设置自定义定位蓝点
     */
    private void setupLocationStyle() {
        // 自定义系统定位蓝点
        MyLocationStyle myLocationStyle = new MyLocationStyle();
        // 自定义定位蓝点图标
//		myLocationStyle.myLocationIcon(BitmapDescriptorFactory
//				.fromResource(R.drawable.gps_point));
        // 自定义精度范围的圆形边框颜色
        myLocationStyle.strokeColor(STROKE_COLOR);
        //自定义精度范围的圆形边框宽度
        myLocationStyle.strokeWidth(5);
        // 设置圆形的填充颜色
        myLocationStyle.radiusFillColor(FILL_COLOR);
        // 将自定义的 myLocationStyle 对象添加到地图上
        aMap.setMyLocationStyle(myLocationStyle);
        aMap.setTrafficEnabled(true);// 显示实时交通状况
    }

    /**
     * 自定义广播接收器（接收BusRouteDetailActivity的广播）
     */
    class MyBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            System.out.println(action == null);
            System.out.println("action:" + action);
            //获取路线和起点终点
            mBusPathFromBroadcast = (BusPath) intent.getParcelableExtra("mBusPath");
            BusRouteResult busRouteResult = (BusRouteResult) intent.getParcelableExtra("mBusRouteResult");
            //显示bus的路线
            mRouteMap.setVisibility(View.VISIBLE);
            mLvRoute.setVisibility(View.GONE);
            aMap.clear();// 清理地图上的所有覆盖物
            mBusrouteOverlay = new BusRouteOverlay(NavigateActivity.this, aMap,
                    mBusPathFromBroadcast, busRouteResult.getStartPos(),
                    mBusRouteResult.getTargetPos());
            mBusrouteOverlay.removeFromMap();
            mBusrouteOverlay.addToMap();
            if ("com.second.letu.dismiss".equals(action)) {
                System.out.println("dismiss");
                mBusrouteOverlay.zoomToSpan();
                mBottomLayout.setVisibility(View.VISIBLE);
                int dis = (int) mBusPathFromBroadcast.getDistance();
                int dur = (int) mBusPathFromBroadcast.getDuration();
                String des = AMapUtil.getFriendlyTime(dur) + "("
                        + AMapUtil.getFriendlyLength(dis) + ")";
                mFirstline.setText(des);
                mSecondline.setVisibility(View.GONE);
            } else if ("com.second.letu.navigate".equals(action)) {
                System.out.println("navigate");
                saveRoute(busRouteResult);
                LatLng location = new LatLng(busRouteResult.getStartPos().getLatitude(), busRouteResult.getStartPos().getLongitude());
                aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 18));
                mBottomLayout.setVisibility(View.GONE);
                mSensorUtil = SensorUtil.getInstance();
                mSensorUtil.initSensor(mBtnJam,"bus");
            }
            //添加marker
            mLocMarker = null;
            float latitude = SPUtil.getFloat(ConstantValues.LATITUDE, 0f);
            float longitude = SPUtil.getFloat(ConstantValues.LONGITUDE, 0f);
            LatLng latlng = new LatLng(latitude, longitude);
            addMarker(latlng);
        }
    }

    /**
     * 保存路线
     * @param busRouteResult
     */
    private void saveRoute(BusRouteResult busRouteResult) {
        mStartPos = busRouteResult.getStartPos();
        mTargetPos = busRouteResult.getTargetPos();
        mIsSecondPoi = false;
        geocoderSearch = new GeocodeSearch(this);
        geocoderSearch.setOnGeocodeSearchListener(this);
        RegeocodeQuery query = new RegeocodeQuery(mStartPos, 200,
                GeocodeSearch.AMAP);// 第一个参数表示一个Latlng，第二参数表示范围多少米，第三个参数表示是火系坐标系还是GPS原生坐标系
        geocoderSearch.getFromLocationAsyn(query);// 设置异步逆地理编码请求
    }
    //反地址编码
    @Override
    public void onRegeocodeSearched(RegeocodeResult result, int rCode) {
        if (rCode == AMapException.CODE_AMAP_SUCCESS) {
            if (result != null && result.getRegeocodeAddress() != null
                    && result.getRegeocodeAddress().getFormatAddress() != null) {
                if(mIsSecondPoi) {
                    mEndPoiName = result.getRegeocodeAddress().getFormatAddress();
                    SQLiteUtil sqLiteUtil = SQLiteUtil.getInstance();
                    sqLiteUtil.insertData(mStartPoiName,mEndPoiName);
                    System.out.println("mStartPoiName:"+ mStartPoiName+",mEndPoiName:" + mEndPoiName);
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
