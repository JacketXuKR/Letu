// Generated code from Butter Knife. Do not modify!
package com.second.letu.adapter;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.second.letu.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class BusResultListAdapter$ViewHolder_ViewBinding implements Unbinder {
  private BusResultListAdapter.ViewHolder target;

  @UiThread
  public BusResultListAdapter$ViewHolder_ViewBinding(BusResultListAdapter.ViewHolder target,
      View source) {
    this.target = target;

    target.mBusPathTitle = Utils.findRequiredViewAsType(source, R.id.bus_path_title, "field 'mBusPathTitle'", TextView.class);
    target.mBusPathDes = Utils.findRequiredViewAsType(source, R.id.bus_path_des, "field 'mBusPathDes'", TextView.class);
    target.mInfo = Utils.findRequiredViewAsType(source, R.id.info, "field 'mInfo'", LinearLayout.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    BusResultListAdapter.ViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mBusPathTitle = null;
    target.mBusPathDes = null;
    target.mInfo = null;
  }
}
