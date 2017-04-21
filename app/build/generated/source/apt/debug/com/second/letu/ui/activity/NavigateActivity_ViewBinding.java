// Generated code from Butter Knife. Do not modify!
package com.second.letu.ui.activity;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.amap.api.maps.MapView;
import com.second.letu.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class NavigateActivity_ViewBinding implements Unbinder {
  private NavigateActivity target;

  private View view2131427397;

  private View view2131427400;

  private View view2131427402;

  private View view2131427404;

  private View view2131427409;

  private View view2131427363;

  @UiThread
  public NavigateActivity_ViewBinding(NavigateActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public NavigateActivity_ViewBinding(final NavigateActivity target, View source) {
    this.target = target;

    View view;
    target.mEtStart = Utils.findRequiredViewAsType(source, R.id.et_start, "field 'mEtStart'", AutoCompleteTextView.class);
    target.mLlStart = Utils.findRequiredViewAsType(source, R.id.ll_start, "field 'mLlStart'", LinearLayout.class);
    target.mEtEnd = Utils.findRequiredViewAsType(source, R.id.et_end, "field 'mEtEnd'", AutoCompleteTextView.class);
    target.mLlEnd = Utils.findRequiredViewAsType(source, R.id.ll_end, "field 'mLlEnd'", LinearLayout.class);
    view = Utils.findRequiredView(source, R.id.tv_search, "field 'mTvSearch' and method 'onClick'");
    target.mTvSearch = Utils.castView(view, R.id.tv_search, "field 'mTvSearch'", TextView.class);
    view2131427397 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick(p0);
      }
    });
    target.mLlFromto = Utils.findRequiredViewAsType(source, R.id.ll_fromto, "field 'mLlFromto'", LinearLayout.class);
    view = Utils.findRequiredView(source, R.id.rl_route_drive_normal, "field 'mRlRouteDriveNormal' and method 'onClick'");
    target.mRlRouteDriveNormal = Utils.castView(view, R.id.rl_route_drive_normal, "field 'mRlRouteDriveNormal'", RelativeLayout.class);
    view2131427400 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.rl_route_bus_normal, "field 'mRlRouteBusNormal' and method 'onClick'");
    target.mRlRouteBusNormal = Utils.castView(view, R.id.rl_route_bus_normal, "field 'mRlRouteBusNormal'", RelativeLayout.class);
    view2131427402 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.rl_route_walk_normal, "field 'mRlRouteWalkNormal' and method 'onClick'");
    target.mRlRouteWalkNormal = Utils.castView(view, R.id.rl_route_walk_normal, "field 'mRlRouteWalkNormal'", RelativeLayout.class);
    view2131427404 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick(p0);
      }
    });
    target.mLlThirdWay = Utils.findRequiredViewAsType(source, R.id.ll_third_way, "field 'mLlThirdWay'", LinearLayout.class);
    target.mLvRoute = Utils.findRequiredViewAsType(source, R.id.lv_route, "field 'mLvRoute'", ListView.class);
    target.mRouteMap = Utils.findRequiredViewAsType(source, R.id.route_map, "field 'mRouteMap'", MapView.class);
    target.mFirstline = Utils.findRequiredViewAsType(source, R.id.firstline, "field 'mFirstline'", TextView.class);
    target.mSecondline = Utils.findRequiredViewAsType(source, R.id.secondline, "field 'mSecondline'", TextView.class);
    target.mDetail = Utils.findRequiredViewAsType(source, R.id.detail, "field 'mDetail'", LinearLayout.class);
    view = Utils.findRequiredView(source, R.id.bottom_layout, "field 'mBottomLayout' and method 'onClick'");
    target.mBottomLayout = Utils.castView(view, R.id.bottom_layout, "field 'mBottomLayout'", RelativeLayout.class);
    view2131427409 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick(p0);
      }
    });
    target.mIvDrive = Utils.findRequiredViewAsType(source, R.id.iv_drive, "field 'mIvDrive'", ImageView.class);
    target.mIvBus = Utils.findRequiredViewAsType(source, R.id.iv_bus, "field 'mIvBus'", ImageView.class);
    target.mIvWalk = Utils.findRequiredViewAsType(source, R.id.iv_walk, "field 'mIvWalk'", ImageView.class);
    target.mLvSearch = Utils.findRequiredViewAsType(source, R.id.lv_search, "field 'mLvSearch'", ListView.class);
    view = Utils.findRequiredView(source, R.id.btn_jam, "field 'mBtnJam' and method 'onClick'");
    target.mBtnJam = Utils.castView(view, R.id.btn_jam, "field 'mBtnJam'", Button.class);
    view2131427363 = view;
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
    NavigateActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mEtStart = null;
    target.mLlStart = null;
    target.mEtEnd = null;
    target.mLlEnd = null;
    target.mTvSearch = null;
    target.mLlFromto = null;
    target.mRlRouteDriveNormal = null;
    target.mRlRouteBusNormal = null;
    target.mRlRouteWalkNormal = null;
    target.mLlThirdWay = null;
    target.mLvRoute = null;
    target.mRouteMap = null;
    target.mFirstline = null;
    target.mSecondline = null;
    target.mDetail = null;
    target.mBottomLayout = null;
    target.mIvDrive = null;
    target.mIvBus = null;
    target.mIvWalk = null;
    target.mLvSearch = null;
    target.mBtnJam = null;

    view2131427397.setOnClickListener(null);
    view2131427397 = null;
    view2131427400.setOnClickListener(null);
    view2131427400 = null;
    view2131427402.setOnClickListener(null);
    view2131427402 = null;
    view2131427404.setOnClickListener(null);
    view2131427404 = null;
    view2131427409.setOnClickListener(null);
    view2131427409 = null;
    view2131427363.setOnClickListener(null);
    view2131427363 = null;
  }
}
