package com.murrayde.animekingmobile.epoxy.models;

import android.view.View;

import androidx.annotation.NonNull;

import com.airbnb.epoxy.EpoxyHolder;
import com.airbnb.epoxy.EpoxyModelClass;
import com.airbnb.epoxy.EpoxyModelWithHolder;
import com.murrayde.animekingmobile.R;

@EpoxyModelClass(layout = R.layout.item_header)
public abstract class HeaderView extends EpoxyModelWithHolder<HeaderView.Holder> {
    @Override
    public void bind(@NonNull Holder holder) {
    }

    static class Holder extends EpoxyHolder {

        @Override
        protected void bindView(@NonNull View itemView) {
        }
    }
}
