// Generated code from Butter Knife. Do not modify!
package com.second.letu.ui.activity;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.second.letu.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class GPSNaviActivity_ViewBinding implements Unbinder {
  private GPSNaviActivity target;

  private View view2131427363;

  @UiThread
  public GPSNaviActivity_ViewBinding(GPSNaviActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public GPSNaviActivity_ViewBinding(final GPSNaviActivity target, View source) {
    this.target = target;

    View view;
    view = Utils.findRequiredView(source, R.id.btn_jam, "field 'mBtnJam' and method 'onClick'");
    target.mBtnJam = Utils.castView(view, R.id.btn_jam, "field 'mBtnJam'", Button.class);
    view2131427363 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick();
      }
    });
    target.mWebview = Utils.findRequiredViewAsType(source, R.id.webview, "field 'mWebview'", WebView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    GPSNaviActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mBtnJam = null;
    target.mWebview = null;

    view2131427363.setOnClickListener(null);
    view2131427363 = null;
  }
}
