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

public class DriveRouteDetailActivity_ViewBinding implements Unbinder {
  private DriveRouteDetailActivity target;

  private View view2131427365;

  private View view2131427370;

  @UiThread
  public DriveRouteDetailActivity_ViewBinding(DriveRouteDetailActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public DriveRouteDetailActivity_ViewBinding(final DriveRouteDetailActivity target, View source) {
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
    target.mTvMap = Utils.findRequiredViewAsType(source, R.id.tv_map, "field 'mTvMap'", TextView.class);
    target.mFirstLine = Utils.findRequiredViewAsType(source, R.id.firstLine, "field 'mFirstLine'", TextView.class);
    target.mSecondLine = Utils.findRequiredViewAsType(source, R.id.secondLine, "field 'mSecondLine'", TextView.class);
    target.mLvDriveRoute = Utils.findRequiredViewAsType(source, R.id.lv_bus_route, "field 'mLvDriveRoute'", ListView.class);
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
    DriveRouteDetailActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mIbtnBack = null;
    target.mTvMap = null;
    target.mFirstLine = null;
    target.mSecondLine = null;
    target.mLvDriveRoute = null;
    target.mTvTitle = null;
    target.mBtnGo = null;

    view2131427365.setOnClickListener(null);
    view2131427365 = null;
    view2131427370.setOnClickListener(null);
    view2131427370 = null;
  }
}
