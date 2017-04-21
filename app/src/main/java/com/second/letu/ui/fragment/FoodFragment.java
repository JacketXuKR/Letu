package com.second.letu.ui.fragment;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.poisearch.Photo;
import com.baoyz.widget.PullRefreshLayout;
import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.second.letu.ConstantValue.ConstantValues;
import com.second.letu.R;
import com.second.letu.adapter.FoodListViewAdapter;
import com.second.letu.presenter.BasePresenter;
import com.second.letu.presenter.PresenterImpl;
import com.second.letu.ui.activity.NavigateActivity;
import com.second.letu.ui.view.LoadPage;
import com.second.letu.util.UIUtil;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Jacket_Xu on 2017/3/6.
 */

public class FoodFragment extends BaseFragment implements BaseSliderView.OnSliderClickListener {
    private ListView mLv;
    private PullRefreshLayout mPullRefreshLayout;
    //presenter的引用
    private BasePresenter mBasePresenter;
    //获取到的数据
    private ArrayList<PoiItem> mDataFromModel;
    private static final String keyWord = "美食";//关键词
    private static final String ctgr = "餐饮服务";//领域
    private int currentPage = 0;//当前页数
    private FoodListViewAdapter mMyListViewAdapter;
    //轮播框控件
    private SliderLayout sliderLayout;
    //轮播框的item
    private TextSliderView mTextSliderView;

    public FoodFragment() {
        //实例化presenter
        mBasePresenter = new PresenterImpl(this);
    }

    //在主线程
    @Override
    public View getSuccessView() {
        //布局文件
        View foodView = UIUtil.getView(R.layout.fragment_listview);
        mLv = (ListView) foodView.findViewById(R.id.lv_fragment);
        mPullRefreshLayout = (PullRefreshLayout) foodView.findViewById(R.id.refreshlayout);
        //初始化头布局
        initHeaderView();
        mMyListViewAdapter = new FoodListViewAdapter(mDataFromModel, FoodFragment.this);
        mLv.setAdapter(mMyListViewAdapter);
        initListener();
        return foodView;
    }

    /**
     * 初始化头布局
     */
    private void initHeaderView() {
        //定义头布局
        sliderLayout = new SliderLayout(getContext());
        sliderLayout.setLayoutParams(new ListView.LayoutParams(ListView.LayoutParams.MATCH_PARENT, UIUtil.dip2px(180f)));
        //初始化轮播框的数据
        initSliderData();
        sliderLayout.setPresetTransformer(SliderLayout.Transformer.Accordion);
        sliderLayout.setPresetIndicator(SliderLayout.PresetIndicators.Right_Bottom);
        sliderLayout.setCustomAnimation(new DescriptionAnimation());
        sliderLayout.setCurrentPosition(0);
        //sliderLayout.startAutoCycle(4000, 4000, true);
        mLv.addHeaderView(sliderLayout);
    }

    /**
     * 初始化轮播框的数据
     */
    public void initSliderData() {
        //初始化数据
        for (int i = 0; i < mDataFromModel.size(); i++) {
            PoiItem item = mDataFromModel.get(i);
            float rating = Float.parseFloat(item.getPoiExtension().getmRating());
            List<Photo> photos = item.getPhotos();
            //如果评分为5星且有图片
            if (rating > 4.5f && photos.size() > 0) {
                System.out.println("item:" + i);
                //定义item布局
                mTextSliderView = new TextSliderView(getContext());
                mTextSliderView
                        .description(item.getTitle())
                        .image(photos.get(0).getUrl())
                        .setScaleType(BaseSliderView.ScaleType.Fit)
                        .setOnSliderClickListener(this);
                //将view相关的数据存储在bundle中
                mTextSliderView.bundle(new Bundle());
                mTextSliderView.getBundle().putParcelable("poiItem", item);
                sliderLayout.addSlider(mTextSliderView);
            }
        }
    }
    /**
     * 初始化listener
     */
    private void initListener() {
        //设置item的点击事件
        mLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            //onItemClick的position包含头布局的轮播条
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                PoiItem poiItem = mDataFromModel.get(position-1);
                //点击后的操作
                LatLonPoint latLonPoint = poiItem.getLatLonPoint();
                Intent intent = new Intent(getContext(), NavigateActivity.class);
                //标志位
                intent.putExtra(ConstantValues.ISPOILITEM,true);
                //名称
                intent.putExtra(ConstantValues.POILITEM_NAME,poiItem.getTitle());
                //地点
                intent.putExtra(ConstantValues.POILITEM,latLonPoint);
                startActivity(intent);
                getActivity().finish();
            }
        });
        //设置加载更多
        mLv.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                //如果滑动到了最后一项,加了头布局后不用-1
                if (scrollState == SCROLL_STATE_IDLE && mLv.getLastVisiblePosition() == mDataFromModel.size()) {
                    new RefreshTask().execute();
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });

        //设置下拉刷新
        mPullRefreshLayout.setOnRefreshListener(new PullRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //开始刷新，在主线程
                refreshView();//刷新listView
                mPullRefreshLayout.setRefreshing(false);
            }
        });
    }

    private boolean isPushToRefresh = false;//下拉刷新状态量

    /**
     * 下拉刷新界面
     */
    private void refreshView() {
        //设置下拉刷新状态量
        isPushToRefresh = true;
        currentPage = -1;
        new RefreshTask().execute();
    }

    /**
     * 异步任务栈，请求数据
     */
    class RefreshTask extends AsyncTask<Void, Void, ArrayList<PoiItem>> {
        @Override
        protected void onPreExecute() {
            currentPage++;
        }

        @Override
        protected ArrayList<PoiItem> doInBackground(Void... params) {
            ArrayList<PoiItem> moreData = mBasePresenter.loadMore(keyWord, ctgr, currentPage, false);
            return moreData;
        }

        @Override
        protected void onPostExecute(ArrayList<PoiItem> poiItems) {
            if (poiItems == null) {
                //访问数据异常
                currentPage--;
                //设置下拉刷新状态量
                isPushToRefresh = false;
                Toast.makeText(UIUtil.getContext(), "网络稍慢，搜索异常", Toast.LENGTH_SHORT).show();
                return;
            } else if (poiItems.size() <= 0) {
                //没有数据
                currentPage--;
                //设置下拉刷新状态量
                isPushToRefresh = false;
                Toast.makeText(UIUtil.getContext(), "已经全部加载啦", Toast.LENGTH_SHORT).show();
                return;
            } else {
                if (isPushToRefresh) {
                    //设置下拉刷新状态量
                    isPushToRefresh = false;
                    mDataFromModel.removeAll(mDataFromModel);
                    Toast.makeText(UIUtil.getContext(), "刷新成功", Toast.LENGTH_SHORT).show();
                }
                mDataFromModel.addAll(poiItems);
                mMyListViewAdapter.notifyDataSetChanged();
            }
        }
    }

    //在子线程
    @Override
    public LoadPage.ResultState loadDataFromFragment() {
        mDataFromModel = mBasePresenter.getDataFromModel(keyWord, ctgr, currentPage, false);
        if (mDataFromModel != null) {
            if (mDataFromModel.size() > 0) {
                //有数据
                Log.d("loadDataFromFragment:", "获取到结果");
                Log.d("数据的数量", mDataFromModel.size() + "");
                for (int i = 0; i < mDataFromModel.size(); i++) {
                    Log.d("poiItems" + i, mDataFromModel.get(i).getTitle());
                }
                return LoadPage.ResultState.LOAD_SUCCESS;
            } else {
                //结果为空
                Log.d("loadDataFromFragment:", "获取结果为空");
                return LoadPage.ResultState.LOAD_EMPTY;
            }
        }
        Log.d("loadDataFromFragment:", "获取结果出现问题，返回结果为null");
        return LoadPage.ResultState.LOAD_ERROR;
    }


//////////////////////////////////////////////////////////////

    @Override
    public void onStop() {
        super.onStop();
        //停止自动滚动
        if(sliderLayout != null) {
            sliderLayout.stopAutoCycle();
        }
    }

    //在点击轮播条的item后调用，参数slider为点击的item
    @Override
    public void onSliderClick(BaseSliderView slider) {
        //Toast.makeText(getContext(), slider.getBundle().get("poiItem") + "", Toast.LENGTH_SHORT).show();
        PoiItem poiItem = (PoiItem) slider.getBundle().get("poiItem");
        System.out.println("poiItem");
        //点击后的操作
        LatLonPoint latLonPoint = poiItem.getLatLonPoint();
        Intent intent = new Intent(getContext(), NavigateActivity.class);
        //标志位
        intent.putExtra(ConstantValues.ISPOILITEM,true);
        //名称
        intent.putExtra(ConstantValues.POILITEM_NAME,poiItem.getTitle());
        //地点
        intent.putExtra(ConstantValues.POILITEM,latLonPoint);
        startActivity(intent);
        getActivity().finish();
    }
}
