// Generated code from Butter Knife. Do not modify!
package com.second.letu.adapter;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.second.letu.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class HotelListViewAdapter$UIViewHolder_ViewBinding implements Unbinder {
  private HotelListViewAdapter.UIViewHolder target;

  @UiThread
  public HotelListViewAdapter$UIViewHolder_ViewBinding(HotelListViewAdapter.UIViewHolder target,
      View source) {
    this.target = target;

    target.mTvTitle = Utils.findRequiredViewAsType(source, R.id.tv_title, "field 'mTvTitle'", TextView.class);
    target.mRbStar = Utils.findRequiredViewAsType(source, R.id.rb_star, "field 'mRbStar'", RatingBar.class);
    target.mTvDistance = Utils.findRequiredViewAsType(source, R.id.tv_distance, "field 'mTvDistance'", TextView.class);
    target.mIvPhone = Utils.findRequiredViewAsType(source, R.id.iv_phone, "field 'mIvPhone'", ImageView.class);
    target.mTvPhone = Utils.findRequiredViewAsType(source, R.id.tv_phone, "field 'mTvPhone'", TextView.class);
    target.mTvSnippet = Utils.findRequiredViewAsType(source, R.id.tv_snippet, "field 'mTvSnippet'", TextView.class);
    target.mIvPhoto = Utils.findRequiredViewAsType(source, R.id.iv_photo, "field 'mIvPhoto'", ImageView.class);
    target.mLlItem = Utils.findRequiredViewAsType(source, R.id.ll_item, "field 'mLlItem'", LinearLayout.class);
    target.mLlPhone = Utils.findRequiredViewAsType(source, R.id.ll_phone, "field 'mLlPhone'", LinearLayout.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    HotelListViewAdapter.UIViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mTvTitle = null;
    target.mRbStar = null;
    target.mTvDistance = null;
    target.mIvPhone = null;
    target.mTvPhone = null;
    target.mTvSnippet = null;
    target.mIvPhoto = null;
    target.mLlItem = null;
    target.mLlPhone = null;
  }
}
