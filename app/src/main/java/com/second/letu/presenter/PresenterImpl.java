package com.second.letu.presenter;

import com.amap.api.services.core.PoiItem;
import com.second.letu.model.NearbyModel;
import com.second.letu.model.NearbyModelImp;
import com.second.letu.ui.fragment.BaseFragment;

import java.util.ArrayList;

/**
 * 附近模块的presenter
 * Created by Jacket_Xu on 2017/3/15.
 */

public class PresenterImpl implements BasePresenter {
    private NearbyModel mNearbyModel;//数据
    private BaseFragment mBaseFragment;//界面

    public PresenterImpl(BaseFragment baseFragment) {
        mBaseFragment = baseFragment;
        //获取model对象
        mNearbyModel = new NearbyModelImp();
    }

    /**
     * 获取数据
     * @param keyWord 关键词
     * @param ctgr 行业
     * @param currentPage 当前页数
     * @param isNearby 是否按照距离排序
     * @return poi数据
     */
    @Override
    public ArrayList<PoiItem> getDataFromModel(String keyWord, String ctgr, int currentPage,boolean isNearby) {
        ArrayList<PoiItem> poiItems = mNearbyModel.getData(keyWord, ctgr, currentPage,isNearby);
        return poiItems;
    }

    @Override
    public ArrayList<PoiItem> loadMore(String keyWord, String ctgr, int currentPage,boolean isNearby) {
        ArrayList<PoiItem> poiItems = mNearbyModel.getData(keyWord, ctgr, currentPage,isNearby);
        return poiItems;
    }
}
