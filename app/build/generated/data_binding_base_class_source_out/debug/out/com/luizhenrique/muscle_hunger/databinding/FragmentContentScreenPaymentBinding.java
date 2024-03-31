// Generated by view binder compiler. Do not edit!
package com.luizhenrique.muscle_hunger.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.luizhenrique.muscle_hunger.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class FragmentContentScreenPaymentBinding implements ViewBinding {
  @NonNull
  private final FrameLayout rootView;

  @NonNull
  public final RadioGroup radioGroup;

  @NonNull
  public final RecyclerView recycleItemContentScreenPayment;

  @NonNull
  public final TextView titleInputScreenOrder;

  private FragmentContentScreenPaymentBinding(@NonNull FrameLayout rootView,
      @NonNull RadioGroup radioGroup, @NonNull RecyclerView recycleItemContentScreenPayment,
      @NonNull TextView titleInputScreenOrder) {
    this.rootView = rootView;
    this.radioGroup = radioGroup;
    this.recycleItemContentScreenPayment = recycleItemContentScreenPayment;
    this.titleInputScreenOrder = titleInputScreenOrder;
  }

  @Override
  @NonNull
  public FrameLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static FragmentContentScreenPaymentBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static FragmentContentScreenPaymentBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.fragment_content_screen_payment, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static FragmentContentScreenPaymentBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.radioGroup;
      RadioGroup radioGroup = ViewBindings.findChildViewById(rootView, id);
      if (radioGroup == null) {
        break missingId;
      }

      id = R.id.recycle_item_content_screen_payment;
      RecyclerView recycleItemContentScreenPayment = ViewBindings.findChildViewById(rootView, id);
      if (recycleItemContentScreenPayment == null) {
        break missingId;
      }

      id = R.id.title_input_screen_order;
      TextView titleInputScreenOrder = ViewBindings.findChildViewById(rootView, id);
      if (titleInputScreenOrder == null) {
        break missingId;
      }

      return new FragmentContentScreenPaymentBinding((FrameLayout) rootView, radioGroup,
          recycleItemContentScreenPayment, titleInputScreenOrder);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
