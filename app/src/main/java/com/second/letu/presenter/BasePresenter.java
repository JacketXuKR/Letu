package com.second.letu.presenter;

import com.amap.api.services.core.PoiItem;

import java.util.ArrayList;

/**
 * Created by Jacket_Xu on 2017/3/15.
 */

public interface BasePresenter {
    /**
     * 从model获取附近数据
     * @param keyWord 关键词
     * @param ctgr 行业
     * @param currentPage 当前页数
     * @param isNearby 是否按照距离排序
     * @return poi的集合
     */
    public ArrayList<PoiItem> getDataFromModel(String keyWord, String ctgr, int currentPage,boolean isNearby);


    /**
     * 上拉加载更多
     * @param keyWord 关键词
     * @param ctgr 行业
     * @param currentPage 当前页数
     * @return poi的集合
     */
    public ArrayList<PoiItem> loadMore(String keyWord, String ctgr, int currentPage,boolean isNearby);
}
