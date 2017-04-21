package com.second.letu.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.second.letu.ui.view.LoadPage;
import com.second.letu.util.UIUtil;

/**
 * Created by Jacket_Xu on 2017/3/6.
 */
public abstract class BaseFragment extends Fragment {

    private LoadPage mLoadPage;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mLoadPage = new LoadPage(UIUtil.getContext()) {
            @Override
            public View getSuccessView() {
                return BaseFragment.this.getSuccessView();
            }

            @Override
            public ResultState loadDataFromFragment() {
                return BaseFragment.this.loadDataFromFragment();
            }
        };
        return mLoadPage;
    }

    /**
     * 调用加载数据
     */
    public void loadData() {
        mLoadPage.loadData();
    }

    /**
     * 子布局实现的加载成功的布局
     *
     * @return 布局
     */
    public abstract View getSuccessView();

    /**
     * 子布局加载数据
     *
     * @return 状态
     */
    public abstract LoadPage.ResultState loadDataFromFragment();

}
