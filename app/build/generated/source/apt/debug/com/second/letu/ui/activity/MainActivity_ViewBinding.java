// Generated code from Butter Knife. Do not modify!
package com.second.letu.ui.activity;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.indris.material.RippleView;
import com.second.letu.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class MainActivity_ViewBinding implements Unbinder {
  private MainActivity target;

  private View view2131427376;

  private View view2131427377;

  private View view2131427378;

  private View view2131427374;

  @UiThread
  public MainActivity_ViewBinding(MainActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public MainActivity_ViewBinding(final MainActivity target, View source) {
    this.target = target;

    View view;
    view = Utils.findRequiredView(source, R.id.rbtn_navigate, "field 'mRbtnNavigate' and method 'onClick'");
    target.mRbtnNavigate = Utils.castView(view, R.id.rbtn_navigate, "field 'mRbtnNavigate'", RippleView.class);
    view2131427376 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.rbtn_nearby, "field 'mRbtnNearby' and method 'onClick'");
    target.mRbtnNearby = Utils.castView(view, R.id.rbtn_nearby, "field 'mRbtnNearby'", RippleView.class);
    view2131427377 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.rbtn_me, "field 'mRbtnMe' and method 'onClick'");
    target.mRbtnMe = Utils.castView(view, R.id.rbtn_me, "field 'mRbtnMe'", RippleView.class);
    view2131427378 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick(p0);
      }
    });
    target.mLlBtnMain = Utils.findRequiredViewAsType(source, R.id.ll_btn_main, "field 'mLlBtnMain'", LinearLayout.class);
    view = Utils.findRequiredView(source, R.id.btn_message, "field 'mBtnMessage' and method 'onClick'");
    target.mBtnMessage = Utils.castView(view, R.id.btn_message, "field 'mBtnMessage'", Button.class);
    view2131427374 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick();
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    MainActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mRbtnNavigate = null;
    target.mRbtnNearby = null;
    target.mRbtnMe = null;
    target.mLlBtnMain = null;
    target.mBtnMessage = null;

    view2131427376.setOnClickListener(null);
    view2131427376 = null;
    view2131427377.setOnClickListener(null);
    view2131427377 = null;
    view2131427378.setOnClickListener(null);
    view2131427378 = null;
    view2131427374.setOnClickListener(null);
    view2131427374 = null;
  }
}
