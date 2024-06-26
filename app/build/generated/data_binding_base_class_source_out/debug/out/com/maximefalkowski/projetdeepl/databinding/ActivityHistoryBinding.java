// Generated by view binder compiler. Do not edit!
package com.maximefalkowski.projetdeepl.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.maximefalkowski.projetdeepl.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivityHistoryBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final Button btnback;

  @NonNull
  public final Button btndeletehistory;

  @NonNull
  public final TextView keyvalueHistory;

  @NonNull
  public final ListView listview;

  @NonNull
  public final ImageView logo2;

  @NonNull
  public final TextView textView2;

  @NonNull
  public final TextView textview12;

  private ActivityHistoryBinding(@NonNull ConstraintLayout rootView, @NonNull Button btnback,
      @NonNull Button btndeletehistory, @NonNull TextView keyvalueHistory,
      @NonNull ListView listview, @NonNull ImageView logo2, @NonNull TextView textView2,
      @NonNull TextView textview12) {
    this.rootView = rootView;
    this.btnback = btnback;
    this.btndeletehistory = btndeletehistory;
    this.keyvalueHistory = keyvalueHistory;
    this.listview = listview;
    this.logo2 = logo2;
    this.textView2 = textView2;
    this.textview12 = textview12;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityHistoryBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityHistoryBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_history, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityHistoryBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.btnback;
      Button btnback = ViewBindings.findChildViewById(rootView, id);
      if (btnback == null) {
        break missingId;
      }

      id = R.id.btndeletehistory;
      Button btndeletehistory = ViewBindings.findChildViewById(rootView, id);
      if (btndeletehistory == null) {
        break missingId;
      }

      id = R.id.keyvalue_history;
      TextView keyvalueHistory = ViewBindings.findChildViewById(rootView, id);
      if (keyvalueHistory == null) {
        break missingId;
      }

      id = R.id.listview;
      ListView listview = ViewBindings.findChildViewById(rootView, id);
      if (listview == null) {
        break missingId;
      }

      id = R.id.logo2;
      ImageView logo2 = ViewBindings.findChildViewById(rootView, id);
      if (logo2 == null) {
        break missingId;
      }

      id = R.id.textView2;
      TextView textView2 = ViewBindings.findChildViewById(rootView, id);
      if (textView2 == null) {
        break missingId;
      }

      id = R.id.textview12;
      TextView textview12 = ViewBindings.findChildViewById(rootView, id);
      if (textview12 == null) {
        break missingId;
      }

      return new ActivityHistoryBinding((ConstraintLayout) rootView, btnback, btndeletehistory,
          keyvalueHistory, listview, logo2, textView2, textview12);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
