// Generated code from Butter Knife. Do not modify!
package com.second.letu.ui.activity;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.ListView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.second.letu.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class OutActivity_ViewBinding implements Unbinder {
  private OutActivity target;

  @UiThread
  public OutActivity_ViewBinding(OutActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public OutActivity_ViewBinding(OutActivity target, View source) {
    this.target = target;

    target.mLvData = Utils.findRequiredViewAsType(source, R.id.lv_data, "field 'mLvData'", ListView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    OutActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mLvData = null;
  }
}
