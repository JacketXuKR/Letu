// Generated code from Butter Knife. Do not modify!
package com.second.letu.ui.activity;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.second.letu.R;
import de.hdodenhof.circleimageview.CircleImageView;
import java.lang.IllegalStateException;
import java.lang.Override;

public class MeActivity_ViewBinding implements Unbinder {
  private MeActivity target;

  private View view2131427365;

  private View view2131427379;

  private View view2131427380;

  private View view2131427381;

  private View view2131427382;

  private View view2131427384;

  private View view2131427386;

  private View view2131427388;

  private View view2131427389;

  private View view2131427390;

  private View view2131427391;

  @UiThread
  public MeActivity_ViewBinding(MeActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public MeActivity_ViewBinding(final MeActivity target, View source) {
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
    view = Utils.findRequiredView(source, R.id.twowei, "field 'mTwowei' and method 'onClick'");
    target.mTwowei = Utils.castView(view, R.id.twowei, "field 'mTwowei'", ImageButton.class);
    view2131427379 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.profile_image, "field 'mProfileImage' and method 'onClick'");
    target.mProfileImage = Utils.castView(view, R.id.profile_image, "field 'mProfileImage'", CircleImageView.class);
    view2131427380 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.tv_name, "field 'mTvName' and method 'onClick'");
    target.mTvName = Utils.castView(view, R.id.tv_name, "field 'mTvName'", TextView.class);
    view2131427381 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick(p0);
      }
    });
    target.mIvMoney = Utils.findRequiredViewAsType(source, R.id.iv_money, "field 'mIvMoney'", ImageView.class);
    view = Utils.findRequiredView(source, R.id.rl_money, "field 'mRlMoney' and method 'onClick'");
    target.mRlMoney = Utils.castView(view, R.id.rl_money, "field 'mRlMoney'", RelativeLayout.class);
    view2131427382 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick(p0);
      }
    });
    target.mIvBinding = Utils.findRequiredViewAsType(source, R.id.iv_binding, "field 'mIvBinding'", ImageView.class);
    view = Utils.findRequiredView(source, R.id.rl_binding, "field 'mRlBinding' and method 'onClick'");
    target.mRlBinding = Utils.castView(view, R.id.rl_binding, "field 'mRlBinding'", RelativeLayout.class);
    view2131427384 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick(p0);
      }
    });
    target.mIvContribute = Utils.findRequiredViewAsType(source, R.id.iv_contribute, "field 'mIvContribute'", ImageView.class);
    view = Utils.findRequiredView(source, R.id.rl_contribute, "field 'mRlContribute' and method 'onClick'");
    target.mRlContribute = Utils.castView(view, R.id.rl_contribute, "field 'mRlContribute'", RelativeLayout.class);
    view2131427386 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.tv_outdata, "field 'mTvOutdata' and method 'onClick'");
    target.mTvOutdata = Utils.castView(view, R.id.tv_outdata, "field 'mTvOutdata'", TextView.class);
    view2131427388 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.tv_help, "field 'mTvHelp' and method 'onClick'");
    target.mTvHelp = Utils.castView(view, R.id.tv_help, "field 'mTvHelp'", TextView.class);
    view2131427389 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.tv_share, "field 'mTvShare' and method 'onClick'");
    target.mTvShare = Utils.castView(view, R.id.tv_share, "field 'mTvShare'", TextView.class);
    view2131427390 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.tv_author, "field 'mTvAuthor' and method 'onClick'");
    target.mTvAuthor = Utils.castView(view, R.id.tv_author, "field 'mTvAuthor'", TextView.class);
    view2131427391 = view;
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
    MeActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mIbtnBack = null;
    target.mTwowei = null;
    target.mProfileImage = null;
    target.mTvName = null;
    target.mIvMoney = null;
    target.mRlMoney = null;
    target.mIvBinding = null;
    target.mRlBinding = null;
    target.mIvContribute = null;
    target.mRlContribute = null;
    target.mTvOutdata = null;
    target.mTvHelp = null;
    target.mTvShare = null;
    target.mTvAuthor = null;

    view2131427365.setOnClickListener(null);
    view2131427365 = null;
    view2131427379.setOnClickListener(null);
    view2131427379 = null;
    view2131427380.setOnClickListener(null);
    view2131427380 = null;
    view2131427381.setOnClickListener(null);
    view2131427381 = null;
    view2131427382.setOnClickListener(null);
    view2131427382 = null;
    view2131427384.setOnClickListener(null);
    view2131427384 = null;
    view2131427386.setOnClickListener(null);
    view2131427386 = null;
    view2131427388.setOnClickListener(null);
    view2131427388 = null;
    view2131427389.setOnClickListener(null);
    view2131427389 = null;
    view2131427390.setOnClickListener(null);
    view2131427390 = null;
    view2131427391.setOnClickListener(null);
    view2131427391 = null;
  }
}
