package com.second.letu.util;

import android.content.Context;
import android.hardware.SensorEventListener;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.List;

/**
 * Created by Jacket_Xu on 2017/4/17.
 */

public class SensorUtil {
    public static final int BUS_SPEED = 22;
    public static final int DRIVE_SPEED = 22;
    public static final int WALK_SPEED = 2;
    private LocationManager mLocationManager;
    private SensorEventListener mListener;
    private Button mButton;
    private String provider;
    private String mode;
    private long lastTimeForBus = 0L;
    private long lastTimeForDrive = 0L;
    private long lastTimeForWalk = 0L;
    private LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            System.out.println("onLocationChanged");
            updateSpeedByLocation(location);
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    };

    private SensorUtil() {
    }

    public static final SensorUtil SENSOR_UTIL = new SensorUtil();

    public static SensorUtil getInstance() {
        return SENSOR_UTIL;
    }

    public void initSensor(Button btn,String m) {
        mButton = btn;
        mode = m;///模式
        mLocationManager = (LocationManager) UIUtil.getContext().getSystemService(Context.LOCATION_SERVICE);
        //设置获取定位的方式
        List<String> providers = mLocationManager.getProviders(true);
        if(providers.contains(LocationManager.GPS_PROVIDER)) {
            //GPS
            provider = LocationManager.GPS_PROVIDER;
        } else if(providers.contains(LocationManager.NETWORK_PROVIDER)) {
            //网络
            provider = LocationManager.NETWORK_PROVIDER;
        } else {
            //其他
            Toast.makeText(UIUtil.getContext(), "请打开GPS或者连接网路", Toast.LENGTH_SHORT).show();
            return;
        }
        Location location = mLocationManager.getLastKnownLocation(provider);
        if(location != null) {
            updateSpeedByLocation(location);
        }
        registerSensor();
        //mLocationManager.requestLocationUpdates(provider,3000,1,locationListener);
    }

    public void registerSensor() {
        mLocationManager.requestLocationUpdates(provider,3000,1,locationListener);
    }

    public void unRegisterSensor() {
        mLocationManager.removeUpdates(locationListener);
        //清零
        lastTimeForBus = 0L;
        lastTimeForDrive = 0L;
        lastTimeForWalk = 0L;
    }

    /**
     * 弹出拥挤对话框
     */
    private void trafficJamDialog(int speed) {
        //根据模式设置阈值
        if("bus".equals(mode)) {
            if(Math.abs(speed) < BUS_SPEED && Math.abs(speed) > 0) {
                long currentTimeMillis = System.currentTimeMillis();
                if(lastTimeForBus == 0L) {
                    lastTimeForBus = currentTimeMillis;
                } else if(currentTimeMillis - lastTimeForBus > 20 * 1000) {
                    mButton.setVisibility(View.VISIBLE);
                    //开始新的计时
                    lastTimeForBus = 0L;
                }
            }
        } else if("drive".equals(mode)) {
            if(Math.abs(speed) < DRIVE_SPEED && Math.abs(speed) > 0) {
                long currentTimeMillis = System.currentTimeMillis();
                if(lastTimeForDrive == 0L) {
                    lastTimeForDrive = currentTimeMillis;
                } else if(currentTimeMillis - lastTimeForDrive > 15 * 1000) {
                    mButton.setVisibility(View.VISIBLE);
                    //开始新的计时
                    lastTimeForDrive = 0L;
                }
            }
        } else if("walk".equals(mode)) {
            if(Math.abs(speed) < WALK_SPEED && Math.abs(speed) > 0) {
                long currentTimeMillis = System.currentTimeMillis();
                if(lastTimeForWalk == 0L) {
                    lastTimeForWalk = currentTimeMillis;
                } else if(currentTimeMillis - lastTimeForWalk > 30 * 1000) {
                    mButton.setVisibility(View.VISIBLE);
                    //开始新的计时
                    lastTimeForWalk = 0L;
                }
            }
        }
    }


    /*private Criteria getLocationCriteria() {
        Criteria criteria = new Criteria();
        // 设置定位精确度 Criteria.ACCURACY_COARSE比较粗略，Criteria.ACCURACY_FINE则比较精细
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        criteria.setSpeedRequired(true); // 设置是否要求速度
        criteria.setCostAllowed(false); // 设置是否允许运营商收费
        criteria.setBearingRequired(false); // 设置是否需要方位信息
        criteria.setAltitudeRequired(false); // 设置是否需要海拔信息
        criteria.setPowerRequirement(Criteria.POWER_LOW); // 设置对电源的需求
        return criteria;
    }
*/

    private void updateSpeedByLocation(Location location) {
        int tempSpeed = (int) (location.getSpeed() * 3.6); // m/s --> Km/h
        //Toast.makeText(UIUtil.getContext(), tempSpeed + "", Toast.LENGTH_SHORT).show();
        trafficJamDialog(tempSpeed);
    }
}
