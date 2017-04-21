// Generated code from Butter Knife. Do not modify!
package com.second.letu.adapter;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.second.letu.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class OutDataAdapter$ViewHolder_ViewBinding implements Unbinder {
  private OutDataAdapter.ViewHolder target;

  @UiThread
  public OutDataAdapter$ViewHolder_ViewBinding(OutDataAdapter.ViewHolder target, View source) {
    this.target = target;

    target.mTvStartPoi = Utils.findRequiredViewAsType(source, R.id.tv_startPoi, "field 'mTvStartPoi'", TextView.class);
    target.mTvEndPoi = Utils.findRequiredViewAsType(source, R.id.tv_endPoi, "field 'mTvEndPoi'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    OutDataAdapter.ViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mTvStartPoi = null;
    target.mTvEndPoi = null;
  }
}
