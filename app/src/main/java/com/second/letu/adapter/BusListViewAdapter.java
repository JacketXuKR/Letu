package com.second.letu.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.poisearch.IndoorData;
import com.amap.api.services.poisearch.Photo;
import com.amap.api.services.poisearch.PoiItemExtension;
import com.second.letu.R;
import com.second.letu.ui.fragment.BaseFragment;
import com.second.letu.util.UIUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 自定义listView的adapter
 * Created by Jacket_Xu on 2017/3/17.
 */

public class BusListViewAdapter extends BaseAdapter {
    //读取的数据
    private ArrayList<PoiItem> data;
    private BaseFragment mBaseFragment;
    public BusListViewAdapter(ArrayList<PoiItem> data, BaseFragment baseFragment) {
        this.data = data;
        mBaseFragment = baseFragment;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        UIViewHolder uiViewHolder = null;
        if (convertView == null) {
            convertView = UIUtil.getView(R.layout.busitem_listview);
            uiViewHolder = new UIViewHolder(convertView);
            convertView.setTag(uiViewHolder);
        } else {
            uiViewHolder = (UIViewHolder) convertView.getTag();
        }

        //获取数据
        PoiItem item = data.get(position);
        ViewHolder viewHolder = new ViewHolder();
        viewHolder.poiId = item.getPoiId();
        viewHolder.distance = item.getDistance();
        viewHolder.latLonPoint = item.getLatLonPoint();
        viewHolder.photos = item.getPhotos();
        viewHolder.poiExtension = item.getPoiExtension();
        viewHolder.snippet = item.getSnippet();//返回POI的地址
        viewHolder.tel = item.getTel();
        viewHolder.title = item.getTitle();
        viewHolder.enter = item.getEnter();
        viewHolder.indoorData = item.getIndoorData();
        viewHolder.indoorMap = item.isIndoorMap();

        System.out.println(viewHolder.toString());

        String rating = viewHolder.poiExtension.getmRating();
        String openTime = viewHolder.poiExtension.getOpentime();
        System.out.println(viewHolder.toString() + ",rating:" + rating + "openTime:" + openTime);

        uiViewHolder.mTvTitle.setText(viewHolder.title);
        uiViewHolder.mTvDistance.setText(viewHolder.distance + "米");
        uiViewHolder.mTvSnippet.setText(viewHolder.snippet);
        //uiViewHolder.mRbStar.setRating(TextUtils.isEmpty(rating) ? 0f : Float.parseFloat(rating));
        /*if (viewHolder.photos.size() > 0) {
            Photo photo = viewHolder.photos.get(0);
            String photoUrl = photo.getUrl();
            System.out.println("photoUrl:" + photoUrl);
            Glide.with(mBaseFragment.getActivity())
                    .load(photoUrl)
                    .into(uiViewHolder.mIvPhoto);
        }*/
        return convertView;
    }

    static class ViewHolder {
        String poiId;
        int distance;//距离
        LatLonPoint latLonPoint;
        List<Photo> photos;
        PoiItemExtension poiExtension;
        String snippet;//返回POI的地址
        String tel;
        String title;
        LatLonPoint enter;
        IndoorData indoorData;
        boolean indoorMap;

        @Override
        public String toString() {
            return "ViewHolder{" +
                    "poiId='" + poiId + '\'' +
                    ", distance=" + distance +
                    ", latLonPoint=" + latLonPoint +
                    ", photos=" + photos +
                    ", poiExtension=" + poiExtension +
                    ", snippet='" + snippet + '\'' +
                    ", tel='" + tel + '\'' +
                    ", title='" + title + '\'' +
                    ", enter=" + enter +
                    ", indoorData=" + indoorData +
                    ", indoorMap=" + indoorMap +
                    '}';
        }
    }

    /**
     * item界面的参数
     */
    class UIViewHolder {
        @BindView(R.id.iv_photo)
        ImageView mIvPhoto;
        @BindView(R.id.tv_title)
        TextView mTvTitle;
        @BindView(R.id.tv_distance)
        TextView mTvDistance;
        @BindView(R.id.tv_snippet)
        TextView mTvSnippet;
        @BindView(R.id.iv_enter)
        ImageView mIvEnter;
        @BindView(R.id.ll_item)
        LinearLayout mLlItem;

        UIViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
