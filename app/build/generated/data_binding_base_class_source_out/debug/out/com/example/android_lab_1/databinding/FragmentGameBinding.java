// Generated by view binder compiler. Do not edit!
package com.example.android_lab_1.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.android_lab_1.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class FragmentGameBinding implements ViewBinding {
  @NonNull
  private final RelativeLayout rootView;

  @NonNull
  public final ImageView imageView;

  @NonNull
  public final TableLayout tableGame;

  @NonNull
  public final TextView textScore;

  @NonNull
  public final TextView textTimer;

  private FragmentGameBinding(@NonNull RelativeLayout rootView, @NonNull ImageView imageView,
      @NonNull TableLayout tableGame, @NonNull TextView textScore, @NonNull TextView textTimer) {
    this.rootView = rootView;
    this.imageView = imageView;
    this.tableGame = tableGame;
    this.textScore = textScore;
    this.textTimer = textTimer;
  }

  @Override
  @NonNull
  public RelativeLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static FragmentGameBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static FragmentGameBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.fragment_game, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static FragmentGameBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.imageView;
      ImageView imageView = ViewBindings.findChildViewById(rootView, id);
      if (imageView == null) {
        break missingId;
      }

      id = R.id.tableGame;
      TableLayout tableGame = ViewBindings.findChildViewById(rootView, id);
      if (tableGame == null) {
        break missingId;
      }

      id = R.id.textScore;
      TextView textScore = ViewBindings.findChildViewById(rootView, id);
      if (textScore == null) {
        break missingId;
      }

      id = R.id.textTimer;
      TextView textTimer = ViewBindings.findChildViewById(rootView, id);
      if (textTimer == null) {
        break missingId;
      }

      return new FragmentGameBinding((RelativeLayout) rootView, imageView, tableGame, textScore,
          textTimer);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}