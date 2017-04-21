package com.second.letu.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationClientOption.AMapLocationMode;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.BitmapDescriptor;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.Circle;
import com.amap.api.maps.model.CircleOptions;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;
import com.indris.material.RippleView;
import com.second.letu.ConstantValue.ConstantValues;
import com.second.letu.R;
import com.second.letu.helper.SensorEventHelper;
import com.second.letu.util.SPUtil;
import com.second.letu.util.UIUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 与地图相关的在这里
 */
public class MainActivity extends Activity implements LocationSource,
        AMapLocationListener {
    @BindView(R.id.rbtn_navigate)
    RippleView mRbtnNavigate;
    @BindView(R.id.rbtn_nearby)
    RippleView mRbtnNearby;
    @BindView(R.id.rbtn_me)
    RippleView mRbtnMe;
    @BindView(R.id.ll_btn_main)
    LinearLayout mLlBtnMain;
    @BindView(R.id.btn_message)
    Button mBtnMessage;
    //高德地图变量
    private AMap aMap;
    private MapView mapView;
    private OnLocationChangedListener mListener;
    private AMapLocationClient mlocationClient;
    private AMapLocationClientOption mLocationOption;
    private static final int STROKE_COLOR = Color.argb(180, 3, 145, 255);
    private static final int FILL_COLOR = Color.argb(80, 3, 145, 255);
    private UiSettings uiSettings;
    private SensorEventHelper mSensorHelper;
    private Marker mLocMarker;
    private boolean mFirstFix = false;
    private Circle mCircle;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient mClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);// 不显示程序的标题栏
        setContentView(R.layout.activity_main);
        mapView = (MapView) findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);// 此方法必须重写
        init();

        ButterKnife.bind(this);
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        mClient = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    /**
     * 初始化
     */
    private void init() {
        if (aMap == null) {
            aMap = mapView.getMap();
            setUpMap();//设置一些amap的属性
        }
        //设置方向传感器
        mSensorHelper = new SensorEventHelper(this);
        if (mSensorHelper != null) {
            mSensorHelper.registerSensorListener();
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
        //设置地图默认的指南针显示
        //uiSettings.setCompassEnabled(true);
        //设置地图默认的比例尺显示
        uiSettings.setScaleControlsEnabled(true);
        //设置地图不能旋转
        uiSettings.setRotateGesturesEnabled(false);

        // 设置自定义定位蓝点
        //setupLocationStyle();
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
     * 方法必须重写
     */
    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
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

        mapView.onPause();
        deactivate();
        mFirstFix = false;//
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
        if (null != mlocationClient) {
            mlocationClient.onDestroy();
        }
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
                //addCircle(location, aLocation.getAccuracy());//添加定位精度圆
                addMarker(location);//添加定位图标
                mSensorHelper.setCurrentMarker(mLocMarker);//定位图标旋转
                aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 18));
            } else {
//				mCircle.setCenter(location);
//				mCircle.setRadius(aLocation.getAccuracy());
                mLocMarker.setPosition(location);
            }
            //存储当前的位置
            SPUtil.putFloat(ConstantValues.LATITUDE, (float) location.latitude);
            SPUtil.putFloat(ConstantValues.LONGITUDE, (float) location.longitude);
            SPUtil.putString(ConstantValues.CITY, aLocation.getCity());
            String s = SPUtil.getString(ConstantValues.CITY, "没有");
            Log.d("经纬度已经存储:", "SPUtil");
            //Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
            //aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 18));
        }
    }


    /**
     * 添加Circle
     *
     * @param latlng 坐标
     * @param radius 半径
     */
    private void addCircle(LatLng latlng, double radius) {
        CircleOptions options = new CircleOptions();
        options.strokeWidth(1f);
        options.fillColor(FILL_COLOR);
        options.strokeColor(STROKE_COLOR);
        options.center(latlng);
        options.radius(radius);
        mCircle = aMap.addCircle(options);
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

//		BitmapDescriptor des = BitmapDescriptorFactory.fromResource(R.drawable.navi_map_gps_locked);
        MarkerOptions options = new MarkerOptions();
        options.icon(des);
        options.anchor(0.5f, 0.5f);
        options.position(latlng);
        mLocMarker = aMap.addMarker(options);
        //设置地点的title
        //mLocMarker.setTitle(mLocMarker.getPosition().toString());
    }


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
            mLocationOption.setLocationMode(AMapLocationMode.Hight_Accuracy);
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

    //底部三个按键
    @OnClick({R.id.rbtn_navigate, R.id.rbtn_nearby, R.id.rbtn_me})
    public void onClick(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.rbtn_navigate:
                Log.d("点击了附近", "跳转到NavigateActivity");
                intent = new Intent(UIUtil.getContext(), NavigateActivity.class);
                intent.putExtra(ConstantValues.ISPOILITEM,false);
                startActivity(intent);
                break;
            case R.id.rbtn_nearby:
                Log.d("点击了附近", "跳转到NearbyActivity");
                intent = new Intent(UIUtil.getContext(), NearbyActivity.class);
                startActivity(intent);
                break;
            case R.id.rbtn_me:
                Log.d("点击了附近", "跳转到MeActivity");
                intent = new Intent(UIUtil.getContext(), MeActivity.class);
                startActivity(intent);
                break;
        }
    }


    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Main Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();
        mClient.connect();
        AppIndex.AppIndexApi.start(mClient, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();
        AppIndex.AppIndexApi.end(mClient, getIndexApiAction());
        mClient.disconnect();
    }

    @OnClick(R.id.btn_message)
    public void onClick() {
        Intent intent = new Intent(this,WebViewActivity.class);
        intent.putExtra(ConstantValues.JAM,"MainActivity");
        startActivity(intent);
    }
}
