// Generated code from Butter Knife. Do not modify!
package com.second.letu.ui.activity;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.second.letu.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class BusRouteDetailActivity_ViewBinding implements Unbinder {
  private BusRouteDetailActivity target;

  private View view2131427365;

  private View view2131427367;

  private View view2131427370;

  @UiThread
  public BusRouteDetailActivity_ViewBinding(BusRouteDetailActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public BusRouteDetailActivity_ViewBinding(final BusRouteDetailActivity target, View source) {
    this.target = target;

    View view;
    view = Utils.findRequiredView(source, R.id.ibtn_back, "field 'mIbtnBack' and method 'onClick'");
    target.mIbtnBack = Utils.castView(view, R.id.ibtn_back, "field 'mIbtnBack'", ImageButton.class);
    view2131427365 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.tv_map, "field 'mTvMap' and method 'onClick'");
    target.mTvMap = Utils.castView(view, R.id.tv_map, "field 'mTvMap'", TextView.class);
    view2131427367 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick(p0);
      }
    });
    target.mFirstLine = Utils.findRequiredViewAsType(source, R.id.firstLine, "field 'mFirstLine'", TextView.class);
    target.mSecondLine = Utils.findRequiredViewAsType(source, R.id.secondLine, "field 'mSecondLine'", TextView.class);
    target.mLvBusRoute = Utils.findRequiredViewAsType(source, R.id.lv_bus_route, "field 'mLvBusRoute'", ListView.class);
    target.mTvTitle = Utils.findRequiredViewAsType(source, R.id.tv_title, "field 'mTvTitle'", TextView.class);
    view = Utils.findRequiredView(source, R.id.btn_go, "field 'mBtnGo' and method 'onClick'");
    target.mBtnGo = Utils.castView(view, R.id.btn_go, "field 'mBtnGo'", Button.class);
    view2131427370 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick(p0);
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    BusRouteDetailActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mIbtnBack = null;
    target.mTvMap = null;
    target.mFirstLine = null;
    target.mSecondLine = null;
    target.mLvBusRoute = null;
    target.mTvTitle = null;
    target.mBtnGo = null;

    view2131427365.setOnClickListener(null);
    view2131427365 = null;
    view2131427367.setOnClickListener(null);
    view2131427367 = null;
    view2131427370.setOnClickListener(null);
    view2131427370 = null;
  }
}
