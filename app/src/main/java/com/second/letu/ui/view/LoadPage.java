package com.second.letu.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.second.letu.R;
import com.second.letu.util.UIUtil;

/**
 * 自定义加载布局
 * Created by Jacket_Xu on 2017/3/12.
 */

public abstract class LoadPage extends FrameLayout {
    private static final int STATE_UNDO = 0;//默认
    private static final int STATE_LOADING = 1;//加载中
    private static final int STATE_ERROR = 2;//加载失败
    private static final int STATE_EMPTY = 3;//加载为空
    private static final int STATE_SUCCESS = 4;//加载成功
    private int mCurrentState = STATE_UNDO;//当前状态
    private View mErrorView;//加载出错的布局
    private View mLoadingView;//加载中的布局
    private View mEmptyView;//加载为空的布局
    private View mSuccessView;//加载成功的布局
    private Button mBtn_retry;

    public LoadPage(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView();
    }

    public LoadPage(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public LoadPage(Context context) {
        super(context);
        initView();
    }

    private void initView() {
        mLoadingView = UIUtil.getView(R.layout.load_loading);//加载中的布局
        mErrorView = UIUtil.getView(R.layout.load_error);//加载错误布局
        mEmptyView = UIUtil.getView(R.layout.load_empty);//加载为空的布局
        //根据状态显示正确的布局
        showRightView();
    }

    /**
     * 根据状态显示正确的布局
     */
    private void showRightView() {
        removeAllViews();
        //加载中
        if (mLoadingView != null) {
            mLoadingView.setVisibility((mCurrentState == STATE_UNDO || mCurrentState == STATE_LOADING) ? View.VISIBLE : View.GONE);
            addView(mLoadingView);
        }

        //加载错误
        if (mErrorView != null) {
            mErrorView.setVisibility(mCurrentState == STATE_ERROR ? View.VISIBLE : View.GONE);
            addView(mErrorView);
            mBtn_retry = (Button) mErrorView.findViewById(R.id.btn_retry);
            mBtn_retry.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    //加载数据
                    loadData();
                }
            });
        }
        //加载为空
        if (mEmptyView != null) {
            mEmptyView.setVisibility(mCurrentState == STATE_EMPTY ? View.VISIBLE : View.GONE);
            addView(mEmptyView);
        }
        //加载成功
        if (mSuccessView != null) {
            mSuccessView.setVisibility(mCurrentState == STATE_SUCCESS ? View.VISIBLE : View.GONE);
            addView(mSuccessView);
        }
    }

    /**
     * 加载数据（外部调用）可以RxJava
     */
    public void loadData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                //加载子布局的数据
                ResultState resultState = loadDataFromFragment();//必须在子线程中
                //如果状态为空，则退出
                if (resultState == null) {
                    return;
                }
                //更新状态
                mCurrentState = resultState.state;
                UIUtil.runOnUIThread(new Runnable() {
                    @Override
                    public void run() {
                        if (mCurrentState == STATE_SUCCESS) {
                            //获取加载成功的布局
                            mSuccessView = getSuccessView();
                        }
                        //显示正确的布局
                        showRightView();//必须在主线程中
                    }
                });
            }
        }).start();
    }


    /**
     * 获取加载成功的布局
     *
     * @return 布局
     */
    public abstract View getSuccessView();

    /**
     * 子布局加载数据
     *
     * @return 状态
     */
    public abstract ResultState loadDataFromFragment();


    //结果的枚举
    public enum ResultState {
        LOAD_ERROR(STATE_ERROR), LOAD_EMPTY(STATE_EMPTY), LOAD_SUCCESS(STATE_SUCCESS);

        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
        }

        private int state;

        ResultState(int state) {
            this.state = state;
        }

    }
}
