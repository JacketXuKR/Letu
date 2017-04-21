package com.second.letu.model;

import com.amap.api.services.core.PoiItem;
import com.second.letu.util.MapUtil;

import java.util.ArrayList;

/**
 * 美食模块的数据
 * Created by Jacket_Xu on 2017/3/15.
 */

public class NearbyModelImp implements NearbyModel {
    @Override
    public ArrayList<PoiItem> getData(String keyWord, String ctgr, int currentPage,boolean isNearby) {
        MapUtil mapUtil = new MapUtil();
        mapUtil.doSearchQuery(keyWord,ctgr,currentPage,isNearby);
        ArrayList<PoiItem> poiItems = mapUtil.getPoiItems();
        return poiItems;
    }
}
