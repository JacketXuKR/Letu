package com.second.letu.util;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.view.View;
import android.widget.Button;

import com.flyco.animation.BaseAnimatorSet;
import com.flyco.dialog.widget.NormalDialog;

/**
 * 传感器的工具类
 * Created by Jacket_Xu on 2017/4/13.
 */

public class SensorUtils {

    private SensorManager mSensorManager;
    private SensorEventListener mListener;
    private Sensor mSensor;
    private BaseAnimatorSet mBasIn;
    private BaseAnimatorSet mBasOut;
    private NormalDialog mDialog;
    private Button mButton;
    private int count = 0;
    private float[] gravity = new float[3];
    private SensorUtils() {
    }
    public static final SensorUtils SENSOR_UTIL = new SensorUtils();
    public static SensorUtils getInstance() {
        return SENSOR_UTIL;
    }
    public void initSensor(Button btn) {
        mButton = btn;
        mSensorManager = (SensorManager) UIUtil.getContext().getSystemService(Context.SENSOR_SERVICE);

        //mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                switch(event.sensor.getType()) {
                    case Sensor.TYPE_ACCELEROMETER:
                        final float alpha = (float) 0.8;
                        gravity[0] = alpha * gravity[0] + (1 - alpha) * event.values[0];
                        gravity[1] = alpha * gravity[1] + (1 - alpha) * event.values[1];
                        gravity[2] = alpha * gravity[2] + (1 - alpha) * event.values[2];
                        int x = (int)(event.values[0] - gravity[0] + 0.5f);
                        int y = (int)(event.values[1] - gravity[1] + 0.5f);
                        int z = (int)(event.values[2] - gravity[2] + 0.5f);
                        String accelerometer = "x:"
                                + x + "\n" + "y:"
                                + y + "\n" + "z:"
                                + z;
                        if(x>5 || y>5 ||z>5) {
                            count++;
                            if(count > 5) {
                                count = 0;
                                System.out.println(accelerometer);
                                trafficJamDialog();
                            }
                        }
                        break;
                    case Sensor.TYPE_GRAVITY:
                        gravity[0] = event.values[0];//单位m/s^2
                        gravity[1] = event.values[1];
                        gravity[2] = event.values[2];
                        break;
                }
                /*float valueX = event.values[0];
                float valueY = event.values[1];
                float valueZ = event.values[2];
                Log.d("valueX",valueX+"");
                Log.d("valueY",valueY+"");
                Log.d("valueZ",valueZ+"");
                //判断条件
                if(Math.abs(valueX) > 10) {
                    trafficJamDialog();
                }*/
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        };
        //mSensorManager.registerListener(mListener, mSensor,SensorManager.SENSOR_DELAY_NORMAL);
        //注册加速度传感器
        mSensorManager.registerListener(mListener,
                mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),//传感器TYPE类型
                SensorManager.SENSOR_DELAY_UI);//采集频率
        //注册重力传感器
        mSensorManager.registerListener(mListener,
                mSensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY),
                SensorManager.SENSOR_DELAY_FASTEST);
    }

    public void unRegisterSensor() {
        mSensorManager.unregisterListener(mListener);
    }

    /**
     * 弹出拥挤对话框
     */
    private void trafficJamDialog() {
        //Toast.makeText(UIUtil.getContext(), "您好像处于拥挤地区，点击上传告诉我吧", Toast.LENGTH_SHORT).show();
        mButton.setVisibility(View.VISIBLE);
        /*if(mDialog == null) {
            System.out.println("null");
            mDialog = new NormalDialog(UIUtil.getContext());
            mBasIn = new BounceTopEnter();
            mBasOut = new SlideBottomExit();
            mDialog.content("为保证咖啡豆的新鲜度和咖啡的香味，并配以特有的传统烘焙和手工冲。")//
                    .style(NormalDialog.STYLE_TWO)//
                    .titleTextSize(23)//
                    .showAnim(mBasIn)//
                    .dismissAnim(mBasOut)//
                    .show();

            mDialog.setOnBtnClickL(
                    new OnBtnClickL() {
                        @Override
                        public void onBtnClick() {
                            Toast.makeText(UIUtil.getContext(), "left", Toast.LENGTH_SHORT).show();
                            mDialog.dismiss();
                        }
                    },
                    new OnBtnClickL() {
                        @Override
                        public void onBtnClick() {
                            Toast.makeText(UIUtil.getContext(), "right", Toast.LENGTH_SHORT).show();
                            mDialog.dismiss();
                        }
                    });
        } else {
            System.out.println("nonull");
            mDialog.show();
        }*/

    }
}
