package com.second.letu.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.amap.api.services.core.AMapException;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.help.Inputtips;
import com.amap.api.services.help.InputtipsQuery;
import com.amap.api.services.help.Tip;
import com.amap.api.services.poisearch.PoiResult;
import com.bumptech.glide.Glide;
import com.second.letu.ConstantValue.ConstantValues;
import com.second.letu.R;
import com.second.letu.ui.fragment.BaseFragment;
import com.second.letu.ui.fragment.FragmentFactory;
import com.second.letu.ui.view.PagerTab;
import com.second.letu.util.SPUtil;
import com.second.letu.util.UIUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * 必须继承FragmentActivity
 * 附件的界面
 */
public class NearbyActivity extends FragmentActivity implements TextWatcher, Inputtips.InputtipsListener {
    @BindView(R.id.pt_indicator)
    PagerTab mPtIndicator;
    @BindView(R.id.vp_fragment)
    ViewPager mVpFragment;
    @BindView(R.id.ibtn_back)
    ImageButton mIbtnBack;
    @BindView(R.id.iv_voice)
    ImageView mIvVoice;
    @BindView(R.id.ibtn_search)
    ImageButton mIbtnSearch;
    @BindView(R.id.et_search)
    AutoCompleteTextView mEtSearch;
    @BindView(R.id.search_result)
    ListView mSearchResult;
    @BindView(R.id.ll_viewpager)
    LinearLayout mLlViewpager;
    private MyViewPagerAdapter mAdapter;
    private PoiResult poiResult;
    private ArrayList<PoiItem> poiItems;
    //存储已经选择过的页面index
    private ArrayList<Integer> pages;
    private String mCity;
    private List<HashMap<String, String>> mListSearchResult;//输入框搜索结果的存储集合
    private SimpleAdapter mSimpleAdapter;//输入框listView的adapter
    private ArrayList<Tip> mTipResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_nearby);
        ButterKnife.bind(this);
        initView();
    }

    /*@Override
    public void onBackPressed() {
        // 必须在UI线程中调用，清除缓存
        Glide.get(NearbyActivity.this).clearMemory();
        Toast.makeText(this, "清除", Toast.LENGTH_SHORT).show();
        new Thread(new Runnable() {
            @Override
            public void run() {
                // 必须在后台线程中调用，建议同时clearMemory()，清除磁盘内存
                Glide.get(NearbyActivity.this).clearDiskCache();
            }
        }).start();
        super.onBackPressed();
    }*/

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 必须在UI线程中调用，清除缓存
        Glide.get(NearbyActivity.this).clearMemory();
        new Thread(new Runnable() {
            @Override
            public void run() {
                // 必须在后台线程中调用，建议同时clearMemory()，清除磁盘内存
                Glide.get(NearbyActivity.this).clearDiskCache();
            }
        }).start();
    }

    /**
     * 初始化view
     */
    private void initView() {
        //设置输入框改变监听器
        mEtSearch.addTextChangedListener(this);
        //设置viewpager
        pages = new ArrayList<Integer>();
        mAdapter = new MyViewPagerAdapter(getSupportFragmentManager());
        mVpFragment.setAdapter(mAdapter);
        mPtIndicator.setViewPager(mVpFragment);
        mPtIndicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                System.out.println("onPageSelected:" + position);
                BaseFragment baseFragment = mAdapter.getItem(position);
                if (!pages.contains(position) && baseFragment != null) {
                    //Toast.makeText(NearbyActivity.this, "进来了", Toast.LENGTH_SHORT).show();
                    baseFragment.loadData();
                    pages.add(position);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mSearchResult.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Tip tip = mTipResult.get(position);
                //点击后的操作
                LatLonPoint latLonPoint = tip.getPoint();
                Intent intent = new Intent(NearbyActivity.this, NavigateActivity.class);
                //标志位
                intent.putExtra(ConstantValues.ISPOILITEM,true);
                //名称
                intent.putExtra(ConstantValues.POILITEM_NAME,tip.getName());
                //地点
                intent.putExtra(ConstantValues.POILITEM,latLonPoint);
                startActivity(intent);
                finish();
            }
        });
    }

    @OnClick({R.id.ibtn_back, R.id.iv_voice, R.id.ibtn_search})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ibtn_back:
                finish();
                break;
            case R.id.iv_voice:
                //进入聊天机器人界面

                break;
            case R.id.ibtn_search:
                Toast.makeText(UIUtil.getContext(), "搜索", Toast.LENGTH_SHORT).show();
                break;
        }
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
        public BaseFragment getItem(int position) {
            System.out.println("getItem：" + position);
            BaseFragment fragment = FragmentFactory.getFragment(position);
            return fragment;
        }

        //空实现，不删除已经加载的布局
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
        }

        @Override
        public int getCount() {
            return lifeName.length;
        }
    }

    ///////////////////////输入框的方法////////////////////////////////////////////////
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        //获取输入
        String newText = s.toString().trim();
        if (TextUtils.isEmpty(newText)) {
            //输入为空
            mSearchResult.setVisibility(View.GONE);
            mLlViewpager.setVisibility(View.VISIBLE);
        } else {
            //获取城市
            mCity = SPUtil.getString(ConstantValues.CITY, "");
            InputtipsQuery inputquery = new InputtipsQuery(newText, mCity);
            inputquery.setCityLimit(true);
            Inputtips inputTips = new Inputtips(NearbyActivity.this, inputquery);
            inputTips.setInputtipsListener(this);
            inputTips.requestInputtipsAsyn();
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    /////////////////Inputtips.InputtipsListener的方法////////////////////////////
    @Override
    public void onGetInputtips(final List<Tip> tipList, int rCode) {
        if (rCode == AMapException.CODE_AMAP_SUCCESS) {
            mListSearchResult = new ArrayList<HashMap<String, String>>();
            mTipResult = new ArrayList<Tip>();//存储提示数据
            HashMap<String, String> map = null;
            for (int i = 0; i < tipList.size(); i++) {
                map = new HashMap<String, String>();
                map.put("name", tipList.get(i).getName());
                map.put("address", tipList.get(i).getDistrict());
                mListSearchResult.add(map);
                mTipResult.add(tipList.get(i));
            }
            mSimpleAdapter = new SimpleAdapter(UIUtil.getContext(), mListSearchResult, R.layout.search_item_layout,
                    new String[]{"name", "address"}, new int[]{R.id.tv_search_name, R.id.tv_address});

            mSearchResult.setAdapter(mSimpleAdapter);
            mSimpleAdapter.notifyDataSetChanged();
            //显示listView
            mLlViewpager.setVisibility(View.GONE);
            mSearchResult.setVisibility(View.VISIBLE);
        } else {
            Log.d("onGetInputtips", "输入框获取不到数据");
        }
    }
}
