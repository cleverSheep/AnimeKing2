package com.murrayde.animeking.view.community.list_anime.item;

import android.view.View;

import androidx.annotation.NonNull;

import com.airbnb.epoxy.EpoxyHolder;
import com.airbnb.epoxy.EpoxyModelClass;
import com.airbnb.epoxy.EpoxyModelWithHolder;
import com.murrayde.animeking.R;

@EpoxyModelClass(layout = R.layout.loading_model)
public abstract class LoadingModel extends EpoxyModelWithHolder<LoadingModel.LoadingHolder> {


    static class LoadingHolder extends EpoxyHolder {

        @Override
        protected void bindView(@NonNull View itemView) {

        }
    }

}
