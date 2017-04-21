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

public class FoodListViewAdapter$UIViewHolder_ViewBinding implements Unbinder {
  private FoodListViewAdapter.UIViewHolder target;

  @UiThread
  public FoodListViewAdapter$UIViewHolder_ViewBinding(FoodListViewAdapter.UIViewHolder target,
      View source) {
    this.target = target;

    target.mIvPhoto = Utils.findRequiredViewAsType(source, R.id.iv_photo, "field 'mIvPhoto'", ImageView.class);
    target.mTvTitle = Utils.findRequiredViewAsType(source, R.id.tv_title, "field 'mTvTitle'", TextView.class);
    target.mRbStar = Utils.findRequiredViewAsType(source, R.id.rb_star, "field 'mRbStar'", RatingBar.class);
    target.mTvDistance = Utils.findRequiredViewAsType(source, R.id.tv_distance, "field 'mTvDistance'", TextView.class);
    target.mTvSnippet = Utils.findRequiredViewAsType(source, R.id.tv_snippet, "field 'mTvSnippet'", TextView.class);
    target.mIvEnter = Utils.findRequiredViewAsType(source, R.id.iv_enter, "field 'mIvEnter'", ImageView.class);
    target.mLlItem = Utils.findRequiredViewAsType(source, R.id.ll_item, "field 'mLlItem'", LinearLayout.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    FoodListViewAdapter.UIViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mIvPhoto = null;
    target.mTvTitle = null;
    target.mRbStar = null;
    target.mTvDistance = null;
    target.mTvSnippet = null;
    target.mIvEnter = null;
    target.mLlItem = null;
  }
}
