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

public class TourListViewAdapter$AnUIViewHolder_ViewBinding implements Unbinder {
  private TourListViewAdapter.AnUIViewHolder target;

  @UiThread
  public TourListViewAdapter$AnUIViewHolder_ViewBinding(TourListViewAdapter.AnUIViewHolder target,
      View source) {
    this.target = target;

    target.mTvTitle = Utils.findRequiredViewAsType(source, R.id.tv_title, "field 'mTvTitle'", TextView.class);
    target.mTvDistance = Utils.findRequiredViewAsType(source, R.id.tv_distance, "field 'mTvDistance'", TextView.class);
    target.mTvSnippet = Utils.findRequiredViewAsType(source, R.id.tv_snippet, "field 'mTvSnippet'", TextView.class);
    target.mLlItem = Utils.findRequiredViewAsType(source, R.id.ll_item, "field 'mLlItem'", LinearLayout.class);
    target.mIvPhoto = Utils.findRequiredViewAsType(source, R.id.iv_photo, "field 'mIvPhoto'", ImageView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    TourListViewAdapter.AnUIViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mTvTitle = null;
    target.mTvDistance = null;
    target.mTvSnippet = null;
    target.mLlItem = null;
    target.mIvPhoto = null;
  }
}
