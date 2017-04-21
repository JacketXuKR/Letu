package com.second.letu.adapter;

import android.text.TextUtils;
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
import com.bumptech.glide.Glide;
import com.second.letu.R;
import com.second.letu.ui.fragment.BaseFragment;
import com.second.letu.util.UIUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 自定义listView的adapter
 * Created by Jacket_Xu on 2017/3/17.
 */

public class TourListViewAdapter extends BaseAdapter {
    //读取的数据
    private ArrayList<PoiItem> data;
    private BaseFragment mBaseFragment;
    private Map<Integer, Integer> itemMap;
    //type的类型
    private static final int TYPE_SPECIAL = 0;
    private static final int TYPE_NORMAL = 1;

    public TourListViewAdapter(ArrayList<PoiItem> data, BaseFragment baseFragment) {
        this.data = data;
        mBaseFragment = baseFragment;
        itemMap = new HashMap<Integer, Integer>();
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }
    private ViewHolder viewHolder;
    @Override
    public int getItemViewType(int position) {
        PoiItem item = data.get(position);
        //获取数据
        viewHolder =  new ViewHolder();
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

        int size = viewHolder.photos.size();
        if (size == 1 || size == 2) {
            return TYPE_SPECIAL;
        } else {
            return getInnerType(size);
        }
    }

    /**
     * 获取类型
     *
     * @param position 位置
     * @return 类型的id
     */
    private int getInnerType(int position) {
        return TYPE_NORMAL;
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
        //获取图片数量
        int aSize = viewHolder.photos.size();
        //存储位置与数量
        //itemMap.put(position, aSize);
        //获取类型
        int returnType = getItemViewType(position);
        //加载布局
        switch (returnType) {
            case TYPE_SPECIAL:
                AnUIViewHolder anUiViewHolder = null;
                if (convertView == null) {
                    convertView = UIUtil.getView(R.layout.tour2item_listview);
                    anUiViewHolder = new AnUIViewHolder(convertView);
                    convertView.setTag(anUiViewHolder);
                } else {
                    anUiViewHolder = (AnUIViewHolder) convertView.getTag();
                }
                anUiViewHolder.mTvTitle.setText(viewHolder.title);
                anUiViewHolder.mTvDistance.setText(viewHolder.distance + "米");
                if (!TextUtils.isEmpty(viewHolder.snippet)) {
                    anUiViewHolder.mTvSnippet.setVisibility(View.VISIBLE);
                    anUiViewHolder.mTvSnippet.setText(viewHolder.snippet);
                } else {
                    anUiViewHolder.mTvSnippet.setVisibility(View.GONE);
                }
                String photoUrl1 = viewHolder.photos.get(0).getUrl();
                Glide.with(mBaseFragment.getActivity())
                        .load(photoUrl1)
                        .into(anUiViewHolder.mIvPhoto);
                break;
            case TYPE_NORMAL:
                UIViewHolder uiViewHolder = null;
                if (convertView == null) {
                    convertView = UIUtil.getView(R.layout.touritem_listview);
                    uiViewHolder = new UIViewHolder(convertView);
                    convertView.setTag(uiViewHolder);
                } else {
                    uiViewHolder = (UIViewHolder) convertView.getTag();
                }
                uiViewHolder.mTvTitle.setText(viewHolder.title);
                uiViewHolder.mTvDistance.setText(viewHolder.distance + "米");
                if (!TextUtils.isEmpty(viewHolder.snippet)) {
                    uiViewHolder.mTvSnippet.setVisibility(View.VISIBLE);
                    uiViewHolder.mTvSnippet.setText(viewHolder.snippet);
                } else {
                    uiViewHolder.mTvSnippet.setVisibility(View.GONE);
                }
                if (aSize >= 3) {
                    uiViewHolder.mLlThreePhone.setVisibility(View.VISIBLE);
                    for (int i = 0; i < aSize; i++) {
                        String photoUrl = viewHolder.photos.get(i).getUrl();
                        switch (i) {
                            case 0:
                                Glide.with(mBaseFragment.getActivity())
                                        .load(photoUrl)
                                        .into(uiViewHolder.mIvPhoto1);
                                break;
                            case 1:
                                Glide.with(mBaseFragment.getActivity())
                                        .load(photoUrl)
                                        .into(uiViewHolder.mIvPhoto2);
                                break;
                            case 2:
                                Glide.with(mBaseFragment.getActivity())
                                        .load(photoUrl)
                                        .into(uiViewHolder.mIvPhoto3);
                                break;
                        }
                    }
                } else {
                    uiViewHolder.mLlThreePhone.setVisibility(View.GONE);
                }
                break;
        }
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
     * 普通item界面的参数
     */
    class UIViewHolder {
        @BindView(R.id.tv_title)
        TextView mTvTitle;
        @BindView(R.id.tv_distance)
        TextView mTvDistance;
        @BindView(R.id.tv_snippet)
        TextView mTvSnippet;
        @BindView(R.id.ll_item)
        LinearLayout mLlItem;
        @BindView(R.id.iv_photo1)
        ImageView mIvPhoto1;
        @BindView(R.id.iv_photo2)
        ImageView mIvPhoto2;
        @BindView(R.id.iv_photo3)
        ImageView mIvPhoto3;
        @BindView(R.id.ll_three_phone)
        LinearLayout mLlThreePhone;

        UIViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    /**
     * 特殊item界面的参数
     */
    class AnUIViewHolder {
        @BindView(R.id.tv_title)
        TextView mTvTitle;
        @BindView(R.id.tv_distance)
        TextView mTvDistance;
        @BindView(R.id.tv_snippet)
        TextView mTvSnippet;
        @BindView(R.id.ll_item)
        LinearLayout mLlItem;
        @BindView(R.id.iv_photo)
        ImageView mIvPhoto;

        AnUIViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

}
