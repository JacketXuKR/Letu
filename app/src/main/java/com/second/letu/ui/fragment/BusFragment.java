package com.second.letu.ui.fragment;

import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.baoyz.widget.PullRefreshLayout;
import com.second.letu.ConstantValue.ConstantValues;
import com.second.letu.R;
import com.second.letu.adapter.BusListViewAdapter;
import com.second.letu.presenter.BasePresenter;
import com.second.letu.presenter.PresenterImpl;
import com.second.letu.ui.activity.NavigateActivity;
import com.second.letu.ui.view.LoadPage;
import com.second.letu.util.UIUtil;

import java.util.ArrayList;

/**
 * Created by Jacket_Xu on 2017/3/6.
 */

public class BusFragment extends BaseFragment {

    private ListView mLv;
    private PullRefreshLayout mPullRefreshLayout;
    //presenter的引用
    private BasePresenter mBasePresenter;
    //获取到的数据
    private ArrayList<PoiItem> mDataFromModel;
    private static final String keyWord = "公交站";//关键词
    private static final String ctgr = "交通设施服务|道路附属设施|公共设施";//领域
    private int currentPage = 0;//当前页数
    private BusListViewAdapter mMyListViewAdapter;

    public BusFragment() {
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
        mMyListViewAdapter = new BusListViewAdapter(mDataFromModel,BusFragment.this);
        mLv.setAdapter(mMyListViewAdapter);
        initListener();

        return foodView;
    }

    /**
     * 初始化listener
     */
    private void initListener() {
        //设置item的点击事件
        mLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                PoiItem poiItem = mDataFromModel.get(position);
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
                //如果滑动到了最后一项
                if (scrollState == SCROLL_STATE_IDLE && mLv.getLastVisiblePosition() == mDataFromModel.size() - 1) {
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
                refreshView();
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
    class RefreshTask extends AsyncTask<Void,Void,ArrayList<PoiItem>> {
        @Override
        protected void onPreExecute() {
            currentPage++;
        }

        @Override
        protected ArrayList<PoiItem> doInBackground(Void... params) {
            ArrayList<PoiItem> moreData = mBasePresenter.loadMore(keyWord, ctgr, currentPage,false);
            return moreData;
        }


        @Override
        protected void onPostExecute(ArrayList<PoiItem> poiItems) {
            if(poiItems == null){
                //访问数据异常
                currentPage--;
                //设置下拉刷新状态量
                isPushToRefresh = false;
                Toast.makeText(UIUtil.getContext(),"网络稍慢，搜索异常",Toast.LENGTH_SHORT).show();
                return;
            } else if(poiItems.size() <= 0) {
                //没有数据
                currentPage--;
                //设置下拉刷新状态量
                isPushToRefresh = false;
                Toast.makeText(UIUtil.getContext(),"已经全部加载啦",Toast.LENGTH_SHORT).show();
                return;
            } else {
                if(isPushToRefresh) {
                    //设置下拉刷新状态量
                    isPushToRefresh = false;
                    mDataFromModel.removeAll(mDataFromModel);
                    Toast.makeText(UIUtil.getContext(),"刷新成功",Toast.LENGTH_SHORT).show();
                }
                mDataFromModel.addAll(poiItems);
                mMyListViewAdapter.notifyDataSetChanged();
            }
        }
    }

    //在子线程
    @Override
    public LoadPage.ResultState loadDataFromFragment() {
        mDataFromModel = mBasePresenter.getDataFromModel(keyWord, ctgr, currentPage,false);
        if (mDataFromModel != null) {
            if (mDataFromModel.size() > 0) {
                //有数据
                Log.d("loadDataFromFragment:", "获取到结果");
                Log.d("数据的数量",mDataFromModel.size()+ "");
                for (int i = 0; i< mDataFromModel.size(); i++) {
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
    }}
