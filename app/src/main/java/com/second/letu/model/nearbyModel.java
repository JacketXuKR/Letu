package com.second.letu.model;

import com.amap.api.services.core.PoiItem;

import java.util.ArrayList;

/**
 * 附近模块的接口
 * Created by Jacket_Xu on 2017/3/15.
 */

public interface NearbyModel {
    /**
     * 根据参数获取附近的数据
     * @param keyWord 关键词
     * @param ctgr 行业
     * @param currentPage 当前页数
     * @param isNearby 是否按照距离排序
     * @return poi的集合
     */
    public ArrayList<PoiItem> getData(String keyWord, String ctgr, int currentPage,boolean isNearby);
}
