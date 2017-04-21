// Generated code from Butter Knife. Do not modify!
package com.second.letu.ui.activity;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.second.letu.R;
import com.second.letu.ui.view.PagerTab;
import java.lang.IllegalStateException;
import java.lang.Override;

public class NearbyActivity_ViewBinding implements Unbinder {
  private NearbyActivity target;

  private View view2131427365;

  private View view2131427460;

  private View view2131427461;

  @UiThread
  public NearbyActivity_ViewBinding(NearbyActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public NearbyActivity_ViewBinding(final NearbyActivity target, View source) {
    this.target = target;

    View view;
    target.mPtIndicator = Utils.findRequiredViewAsType(source, R.id.pt_indicator, "field 'mPtIndicator'", PagerTab.class);
    target.mVpFragment = Utils.findRequiredViewAsType(source, R.id.vp_fragment, "field 'mVpFragment'", ViewPager.class);
    view = Utils.findRequiredView(source, R.id.ibtn_back, "field 'mIbtnBack' and method 'onClick'");
    target.mIbtnBack = Utils.castView(view, R.id.ibtn_back, "field 'mIbtnBack'", ImageButton.class);
    view2131427365 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.iv_voice, "field 'mIvVoice' and method 'onClick'");
    target.mIvVoice = Utils.castView(view, R.id.iv_voice, "field 'mIvVoice'", ImageView.class);
    view2131427460 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.ibtn_search, "field 'mIbtnSearch' and method 'onClick'");
    target.mIbtnSearch = Utils.castView(view, R.id.ibtn_search, "field 'mIbtnSearch'", ImageButton.class);
    view2131427461 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick(p0);
      }
    });
    target.mEtSearch = Utils.findRequiredViewAsType(source, R.id.et_search, "field 'mEtSearch'", AutoCompleteTextView.class);
    target.mSearchResult = Utils.findRequiredViewAsType(source, R.id.search_result, "field 'mSearchResult'", ListView.class);
    target.mLlViewpager = Utils.findRequiredViewAsType(source, R.id.ll_viewpager, "field 'mLlViewpager'", LinearLayout.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    NearbyActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mPtIndicator = null;
    target.mVpFragment = null;
    target.mIbtnBack = null;
    target.mIvVoice = null;
    target.mIbtnSearch = null;
    target.mEtSearch = null;
    target.mSearchResult = null;
    target.mLlViewpager = null;

    view2131427365.setOnClickListener(null);
    view2131427365 = null;
    view2131427460.setOnClickListener(null);
    view2131427460 = null;
    view2131427461.setOnClickListener(null);
    view2131427461 = null;
  }
}
