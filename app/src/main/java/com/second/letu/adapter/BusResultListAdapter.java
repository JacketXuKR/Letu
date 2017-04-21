package com.second.letu.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amap.api.services.route.BusPath;
import com.amap.api.services.route.BusRouteResult;
import com.second.letu.R;
import com.second.letu.ui.activity.BusRouteDetailActivity;
import com.second.letu.util.AMapUtil;
import com.second.letu.util.UIUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * bus搜索结果列表
 * Created by Jacket_Xu on 2017/3/27.
 */

public class BusResultListAdapter extends BaseAdapter {
    private final List<BusPath> mPaths;
    private Context mContext;
    private BusRouteResult mBusRouteResult;
    public BusResultListAdapter(Context context, BusRouteResult busRouteResult) {
        mContext = context;
        mBusRouteResult = busRouteResult;
        mPaths = mBusRouteResult.getPaths();
    }

    @Override
    public int getCount() {
        return mPaths.size();
    }

    @Override
    public Object getItem(int position) {
        return mPaths.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if(convertView == null) {
            convertView = UIUtil.getView(R.layout.item_bus_search_result);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder)convertView.getTag();
        }
        final BusPath item = mPaths.get(position);
        viewHolder.mBusPathTitle.setText(AMapUtil.getBusPathTitle(item));
        viewHolder.mBusPathDes.setText(AMapUtil.getBusPathDes(item));
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext,BusRouteDetailActivity.class);
                //包含所有路径的信息
                intent.putExtra("bus_path_item",item);
                intent.putExtra("mBusRouteResult",mBusRouteResult);
                intent.putExtra("bottom",false);
                //如果没有包含该活动的栈，则创建新的栈
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(intent);
            }
        });
        return convertView;
    }


    static class ViewHolder {
        @BindView(R.id.bus_path_title)
        TextView mBusPathTitle;
        @BindView(R.id.bus_path_des)
        TextView mBusPathDes;
        @BindView(R.id.info)
        LinearLayout mInfo;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
