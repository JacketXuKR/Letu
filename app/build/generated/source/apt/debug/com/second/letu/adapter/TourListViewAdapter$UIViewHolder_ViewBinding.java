// Generated code from Butter Knife. Do not modify!
package com.second.letu.adapter;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.second.letu.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class TourListViewAdapter$UIViewHolder_ViewBinding implements Unbinder {
  private TourListViewAdapter.UIViewHolder target;

  @UiThread
  public TourListViewAdapter$UIViewHolder_ViewBinding(TourListViewAdapter.UIViewHolder target,
      View source) {
    this.target = target;

    target.mTvTitle = Utils.findRequiredViewAsType(source, R.id.tv_title, "field 'mTvTitle'", TextView.class);
    target.mTvDistance = Utils.findRequiredViewAsType(source, R.id.tv_distance, "field 'mTvDistance'", TextView.class);
    target.mTvSnippet = Utils.findRequiredViewAsType(source, R.id.tv_snippet, "field 'mTvSnippet'", TextView.class);
    target.mLlItem = Utils.findRequiredViewAsType(source, R.id.ll_item, "field 'mLlItem'", LinearLayout.class);
    target.mIvPhoto1 = Utils.findRequiredViewAsType(source, R.id.iv_photo1, "field 'mIvPhoto1'", ImageView.class);
    target.mIvPhoto2 = Utils.findRequiredViewAsType(source, R.id.iv_photo2, "field 'mIvPhoto2'", ImageView.class);
    target.mIvPhoto3 = Utils.findRequiredViewAsType(source, R.id.iv_photo3, "field 'mIvPhoto3'", ImageView.class);
    target.mLlThreePhone = Utils.findRequiredViewAsType(source, R.id.ll_three_phone, "field 'mLlThreePhone'", LinearLayout.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    TourListViewAdapter.UIViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mTvTitle = null;
    target.mTvDistance = null;
    target.mTvSnippet = null;
    target.mLlItem = null;
    target.mIvPhoto1 = null;
    target.mIvPhoto2 = null;
    target.mIvPhoto3 = null;
    target.mLlThreePhone = null;
  }
}
