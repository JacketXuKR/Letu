package com.second.letu.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.second.letu.R;
import com.second.letu.util.UIUtil;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Jacket_Xu on 2017/4/20.
 */

public class OutDataAdapter extends BaseAdapter {
    private ArrayList<HashMap<String, String>> mData;

    public OutDataAdapter(ArrayList<HashMap<String, String>> data) {
        mData = data;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = UIUtil.getView(R.layout.outdataitem);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder)convertView.getTag();
        }
        HashMap<String, String> map = mData.get(position);
        String startPoi = map.get("startPoi");
        String endPoi = map.get("endPoi");
        viewHolder.mTvStartPoi.setText(startPoi);
        viewHolder.mTvEndPoi.setText(endPoi);
        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.tv_startPoi)
        TextView mTvStartPoi;
        @BindView(R.id.tv_endPoi)
        TextView mTvEndPoi;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
