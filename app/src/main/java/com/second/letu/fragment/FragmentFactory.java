package com.second.letu.fragment;

import android.support.v4.app.Fragment;

/**
 * Fragment工厂
 * Created by Jacket_Xu on 2017/3/6.
 */

public class FragmentFactory {
    /**
     * 根据position获取Fragment
     * @param position 位置
     * @return
     */
    public static Fragment getFragment(int position) {
        switch (position) {
            case 0://美食
                break;
            case 1://酒店
                break;
            case 2://旅游
                break;
            case 3://安全
                break;

        }
        return null;
    }
}
