package com.second.letu.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.amap.api.services.route.WalkStep;
import com.second.letu.R;
import com.second.letu.util.AMapUtil;

import java.util.ArrayList;
import java.util.List;

//walk的每一步
public class WalkSegmentListAdapter extends BaseAdapter {
    private Context mContext;
    private List<WalkStep> mWalkStepList = new ArrayList<WalkStep>();

    public WalkSegmentListAdapter(Context context, List<WalkStep> list) {
        this.mContext = context;
        mWalkStepList.add(new WalkStep());
        for (WalkStep walkStep : list) {
            mWalkStepList.add(walkStep);
        }
        mWalkStepList.add(new WalkStep());
    }

    @Override
    public int getCount() {
        return mWalkStepList.size();
    }

    @Override
    public Object getItem(int position) {
        return mWalkStepList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = View.inflate(mContext, R.layout.item_bus_segment,
                    null);
            holder.lineName = (TextView) convertView
                    .findViewById(R.id.bus_line_name);
            holder.dirIcon = (ImageView) convertView
                    .findViewById(R.id.bus_dir_icon);
            holder.dirUp = (ImageView) convertView
                    .findViewById(R.id.bus_dir_icon_up);
            holder.dirDown = (ImageView) convertView
                    .findViewById(R.id.bus_dir_icon_down);
            holder.splitLine = (ImageView) convertView
                    .findViewById(R.id.bus_seg_split_line);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final WalkStep item = mWalkStepList.get(position);
        if (position == 0) {
            holder.dirIcon.setImageResource(R.drawable.dir_start);
            holder.lineName.setText("出发");
            holder.dirUp.setVisibility(View.INVISIBLE);
            holder.dirDown.setVisibility(View.VISIBLE);
            holder.splitLine.setVisibility(View.INVISIBLE);
            return convertView;
        } else if (position == mWalkStepList.size() - 1) {
            holder.dirIcon.setImageResource(R.drawable.dir_end);
            holder.lineName.setText("到达终点");
            holder.dirUp.setVisibility(View.VISIBLE);
            holder.dirDown.setVisibility(View.INVISIBLE);
            return convertView;
        } else {
            holder.splitLine.setVisibility(View.VISIBLE);
            holder.dirUp.setVisibility(View.VISIBLE);
            holder.dirDown.setVisibility(View.VISIBLE);
            String actionName = item.getAction();
            int resID = AMapUtil.getWalkActionID(actionName);
            holder.dirIcon.setImageResource(resID);
            holder.lineName.setText(item.getInstruction());
            return convertView;
        }
    }

    private class ViewHolder {
        TextView lineName;
        ImageView dirIcon;
        ImageView dirUp;
        ImageView dirDown;
        ImageView splitLine;
    }
}
