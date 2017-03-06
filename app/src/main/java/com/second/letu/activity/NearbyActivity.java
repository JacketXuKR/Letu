package com.second.letu.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Window;

import com.second.letu.R;
import com.second.letu.util.UIUtil;
import com.second.letu.view.PagerTab;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * 必须继承FragmentActivity
 * 附件的界面
 */
public class NearbyActivity extends FragmentActivity {
    @BindView(R.id.pt_indicator)
    PagerTab mPtIndicator;
    @BindView(R.id.vp_fragment)
    ViewPager mVpFragment;
    private MyViewPagerAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_nearby);
        ButterKnife.bind(this);
        initView();

    }


    /**
     * 初始化view
     */
    private void initView() {
        mAdapter = new MyViewPagerAdapter(getSupportFragmentManager());
        mVpFragment.setAdapter(mAdapter);
        mPtIndicator.setViewPager(mVpFragment);
    }

    /**
     * Fragment的adapter
     */
    class MyViewPagerAdapter extends FragmentPagerAdapter {
        private String[] lifeName = new String[4];
        //显示title的名称
        @Override
        public CharSequence getPageTitle(int position) {
            return lifeName[position];
        }

        public MyViewPagerAdapter(FragmentManager fm) {
            super(fm);
            //初始化名称
            lifeName = UIUtil.getStringArray(R.array.life);
        }

        @Override
        public Fragment getItem(int position) {

            return null;
        }

        @Override
        public int getCount() {
            return lifeName.length;
        }
    }


}
