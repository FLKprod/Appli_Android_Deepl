// Generated by view binder compiler. Do not edit!
package com.maximefalkowski.projetdeepl.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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

public final class HistorycellBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final TextView dateHistorycell;

  @NonNull
  public final ImageView imageView;

  @NonNull
  public final TextView languetradHistorycell;

  @NonNull
  public final TextView txtsaisieHistorycell;

  @NonNull
  public final TextView txttradHistorycell;

  private HistorycellBinding(@NonNull ConstraintLayout rootView, @NonNull TextView dateHistorycell,
      @NonNull ImageView imageView, @NonNull TextView languetradHistorycell,
      @NonNull TextView txtsaisieHistorycell, @NonNull TextView txttradHistorycell) {
    this.rootView = rootView;
    this.dateHistorycell = dateHistorycell;
    this.imageView = imageView;
    this.languetradHistorycell = languetradHistorycell;
    this.txtsaisieHistorycell = txtsaisieHistorycell;
    this.txttradHistorycell = txttradHistorycell;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static HistorycellBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static HistorycellBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.historycell, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static HistorycellBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.date_historycell;
      TextView dateHistorycell = ViewBindings.findChildViewById(rootView, id);
      if (dateHistorycell == null) {
        break missingId;
      }

      id = R.id.imageView;
      ImageView imageView = ViewBindings.findChildViewById(rootView, id);
      if (imageView == null) {
        break missingId;
      }

      id = R.id.languetrad_historycell;
      TextView languetradHistorycell = ViewBindings.findChildViewById(rootView, id);
      if (languetradHistorycell == null) {
        break missingId;
      }

      id = R.id.txtsaisie_historycell;
      TextView txtsaisieHistorycell = ViewBindings.findChildViewById(rootView, id);
      if (txtsaisieHistorycell == null) {
        break missingId;
      }

      id = R.id.txttrad_historycell;
      TextView txttradHistorycell = ViewBindings.findChildViewById(rootView, id);
      if (txttradHistorycell == null) {
        break missingId;
      }

      return new HistorycellBinding((ConstraintLayout) rootView, dateHistorycell, imageView,
          languetradHistorycell, txtsaisieHistorycell, txttradHistorycell);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
