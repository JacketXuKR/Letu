package com.second.letu.util;

import android.util.Log;

import com.amap.api.services.core.AMapException;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.second.letu.ConstantValue.ConstantValues;

import java.util.ArrayList;

/**
 * 高德地图功能工具类(单例类)
 * Created by Jacket_Xu on 2017/3/11.
 */

public class MapUtil implements PoiSearch.OnPoiSearchListener {
    private static final int NEARBY_METER = 5000;//附近的距离（米）
    //private int currentPage = 0;// 当前页面，从0开始计数
    //private PoiSearch.Query query;// Poi查询条件类
    //private LatLonPoint lp;
    //private PoiSearch poiSearch;
    private PoiResult poiResult;


    private ArrayList<PoiItem> poiItems;

    //private static MapUtil mMapUtil;

    /*private MapUtil() {
    }*/

    /**
     * 获取MapUtil实例
     *
     * @return
     */
    /*public static MapUtil getInstance() {
        if (mMapUtil == null) {
            synchronized (MapUtil.class) {
                if (mMapUtil == null) {
                    mMapUtil = new MapUtil();
                }
            }
        }
        return mMapUtil;
    }*/

    /**
     * 获取当前地点周边的数据
     * @param keyWord 要搜索的字符串
     * @param ctgr    //POI搜索类型共分为以下20种：汽车服务|汽车销售|
     *                //汽车维修|摩托车服务|餐饮服务|购物服务|生活服务|体育休闲服务|医疗保健服务|
     *                //住宿服务|风景名胜|商务住宅|政府机构及社会团体|科教文化服务|交通设施服务|
     *                //金融保险服务|公司企业|道路附属设施|地名地址信息|公共设施
     *                city    表示POI搜索区域，可以是城市编码也可以是城市名称，也可以传空字符串，空字符串代表全国在全国范围内进行搜索
     */
    public void doSearchQuery(String keyWord, String ctgr, int currentPage,boolean isNearby) {
        //获取当前城市的id
        String city = SPUtil.getString(ConstantValues.CITY, "广州");
        PoiSearch.Query query = new PoiSearch.Query(keyWord, ctgr, city);// 第一个参数表示搜索字符串，第二个参数表示poi搜索类型，第三个参数表示poi搜索区域（空字符串代表全国）
        query.setPageSize(20);// 设置每页最多返回多少条poiitem
        query.setPageNum(currentPage);// 设置查第一页
        //获取当前的经纬度
        float latitude = SPUtil.getFloat(ConstantValues.LATITUDE, 0f);
        float longitude = SPUtil.getFloat(ConstantValues.LONGITUDE, 0f);
        LatLonPoint lp = new LatLonPoint(latitude, longitude);
        if (lp != null) {
            PoiSearch poiSearch = new PoiSearch(UIUtil.getContext(), query);
            poiSearch.setOnPoiSearchListener(this);
            poiSearch.setBound(new PoiSearch.SearchBound(lp, NEARBY_METER, isNearby));//第三个参数设置true按照距离排序
            // 设置搜索区域为以lp点为圆心，其周围5000米范围
            poiSearch.searchPOIAsyn();// 异步搜索
        }
    }

    /**
     * 获取读取到的poi（必须在子线程中调用）
     *
     * @return 有数据的结果、结果为空或者null(处理出现问题，找不到结果)
     */
    public ArrayList<PoiItem> getPoiItems() {
        System.out.println("getPoiItems:" + UIUtil.isRunOnUIThread());
        //等待处理结果
        while (hasResult == 0) { //hasResult在onPoiSearched改变值
        }
        hasResult = 0;
        return poiItems;
    }

    private int hasResult = 0;
    ////////////////////PoiSearch.OnPoiSearchListener///////////////////////////////

    /**
     * doSearchQuery搜索结果（自动调用，跳转到了主线程）
     *
     * @param result 结果
     * @param rcode  编码
     */
    @Override
    public void onPoiSearched(PoiResult result, int rcode) {
        if (rcode == AMapException.CODE_AMAP_SUCCESS) {
            if (result != null) {// 搜索poi的结果
                ArrayList<PoiItem> poiItemArr = result.getPois();// 取得第一页的poiitem数据，页数从数字0开始
                if (poiItemArr != null && poiItemArr.size() > 0) {
                    poiResult = result;
                    poiItems = poiItemArr;
                    hasResult = 2;//有结果
                } else {
                    poiResult = null;
                    poiItems = new ArrayList<PoiItem>();//空集合
                    hasResult = 3;//结果为空
                    Log.d("MapUtil:onPoiSearched","已经全部加载");
                }
            } else {
                poiResult = null;
                poiItems = null;
                hasResult = 1;//没结果
                Log.d("MapUtil:onPoiSearched","搜索无结果");
            }
        } else {
            poiResult = null;
            poiItems = null;
            hasResult = 1;//没结果
            Log.d("MapUtil:onPoiSearched", "rcode出错："+ rcode);
        }
    }

    @Override
    public void onPoiItemSearched(PoiItem poiItem, int errorCode) {

    }

}
