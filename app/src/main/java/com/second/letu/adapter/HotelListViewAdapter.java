package com.second.letu.adapter;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.poisearch.IndoorData;
import com.amap.api.services.poisearch.Photo;
import com.amap.api.services.poisearch.PoiItemExtension;
import com.bumptech.glide.Glide;
import com.flyco.animation.BaseAnimatorSet;
import com.flyco.animation.BounceEnter.BounceTopEnter;
import com.flyco.animation.SlideExit.SlideBottomExit;
import com.flyco.dialog.listener.OnOperItemClickL;
import com.flyco.dialog.widget.NormalListDialog;
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

public class HotelListViewAdapter extends BaseAdapter {
    //读取的数据
    private ArrayList<PoiItem> data;
    private BaseFragment mBaseFragment;//获取的关联的fragment
    //对话框的动画
    private BaseAnimatorSet mBasIn = new BounceTopEnter();
    private BaseAnimatorSet mBasOut = new SlideBottomExit();

    /**
     * 对话框的调用
     * @param items 对话框的item
     */
    private void showNormalListDialog(final String[] items) {
        //定义对话框
        final NormalListDialog dialog = new NormalListDialog(mBaseFragment.getActivity(), items);
        //初始化属性
        dialog.title("请选择电话")//
                .titleTextSize_SP(18)//
                .titleBgColor(Color.parseColor("#409ED7"))//
                .itemPressColor(Color.parseColor("#85D3EF"))//
                .itemTextColor(Color.parseColor("#303030"))//
                .showAnim(mBasIn)//显示动画
                .dismissAnim(mBasOut)//消失动画
                .show();
        dialog.setOnOperItemClickL(new OnOperItemClickL() {
            @Override
            public void onOperItemClick(AdapterView<?> parent, View view, int position, long id) {
                //跳转到电话界面
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + items[position]));
                mBaseFragment.startActivity(intent);
                dialog.dismiss();
            }
        });
    }

    public HotelListViewAdapter(ArrayList<PoiItem> data, BaseFragment baseFragment) {
        this.data = data;
        this.mBaseFragment = baseFragment;
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
            convertView = UIUtil.getView(R.layout.hotelitem_listview);
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

        String rating = viewHolder.poiExtension.getmRating();
        String openTime = viewHolder.poiExtension.getOpentime();
        System.out.println(viewHolder.toString() + ",rating:" + rating + "openTime:" + openTime);

        uiViewHolder.mTvTitle.setText(viewHolder.title);
        uiViewHolder.mTvDistance.setText(viewHolder.distance + "米");
        uiViewHolder.mTvSnippet.setText(viewHolder.snippet);
        uiViewHolder.mRbStar.setRating(TextUtils.isEmpty(rating) ? 0f : Float.parseFloat(rating));

        if (TextUtils.isEmpty(viewHolder.tel)) {
            uiViewHolder.mTvPhone.setVisibility(View.GONE);
            uiViewHolder.mIvPhone.setVisibility(View.GONE);
        } else {
            final String[] split = viewHolder.tel.split(";");
            uiViewHolder.mTvPhone.setText(split[0]);
            uiViewHolder.mLlPhone.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //弹出对话框
                    showNormalListDialog(split);
                }
            });
        }

        if (viewHolder.photos.size() > 0) {
            Photo photo = viewHolder.photos.get(0);
            String photoUrl = photo.getUrl();
            System.out.println("photoUrl:" + photoUrl);
            Glide.with(mBaseFragment.getActivity())
                    .load(photoUrl)
                    .into(uiViewHolder.mIvPhoto);
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

    class UIViewHolder {
        @BindView(R.id.tv_title)
        TextView mTvTitle;
        @BindView(R.id.rb_star)
        RatingBar mRbStar;
        @BindView(R.id.tv_distance)
        TextView mTvDistance;
        @BindView(R.id.iv_phone)
        ImageView mIvPhone;
        @BindView(R.id.tv_phone)
        TextView mTvPhone;
        @BindView(R.id.tv_snippet)
        TextView mTvSnippet;
        @BindView(R.id.iv_photo)
        ImageView mIvPhoto;
        @BindView(R.id.ll_item)
        LinearLayout mLlItem;
        @BindView(R.id.ll_phone)
        LinearLayout mLlPhone;

        UIViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

}
