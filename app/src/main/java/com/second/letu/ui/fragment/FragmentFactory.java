package com.second.letu.ui.fragment;

import android.util.Log;

import java.util.ArrayList;

/**
 * Fragment工厂
 * Created by Jacket_Xu on 2017/3/6.
 */

public class FragmentFactory {
    /**
     * 根据position获取Fragment
     *
     * @param position 位置
     * @return
     */
    private static ArrayList<BaseFragment> fragmentList = new ArrayList<BaseFragment>();
    private static ArrayList<Integer> positions = new ArrayList<Integer>();

    public static BaseFragment getFragment(int position) {
        //如果存在该点，则返回之前加载好的Fragment
        if (positions.contains(position)) {
            return fragmentList.get(position);
        }
        BaseFragment baseFragment = null;
        switch (position) {
            case 0://美食
                baseFragment = new FoodFragment();
                break;
            case 1://酒店
                baseFragment = new HotelFragment();
                break;
            case 2://旅游
                baseFragment = new TourFragment();
                break;
            case 3://安全
                baseFragment = new BusFragment();
                break;
            default:
                Log.d("position出错", position + "");
                break;

        }
        positions.add(position);
        fragmentList.add(position, baseFragment);
        return baseFragment;
    }
}
