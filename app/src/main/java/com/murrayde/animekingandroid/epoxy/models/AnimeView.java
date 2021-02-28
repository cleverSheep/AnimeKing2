package com.murrayde.animekingandroid.epoxy.models;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.airbnb.epoxy.EpoxyAttribute;
import com.airbnb.epoxy.EpoxyHolder;
import com.airbnb.epoxy.EpoxyModelClass;
import com.airbnb.epoxy.EpoxyModelWithHolder;
import com.murrayde.animekingandroid.R;
import com.murrayde.animekingandroid.util.ImageUtil;

@EpoxyModelClass(layout = R.layout.item_anime)
public abstract class AnimeView extends EpoxyModelWithHolder<AnimeView.ItemHolder> {

    @EpoxyAttribute
    String anime_title;
    @EpoxyAttribute
    String image_url;
    @EpoxyAttribute
    String num_of_questions;
    @EpoxyAttribute
    String barrier_text;
    @EpoxyAttribute(EpoxyAttribute.Option.DoNotHash)
    View.OnClickListener onClickListener;
    @EpoxyAttribute(EpoxyAttribute.Option.DoNotHash)
    View.OnLongClickListener onLongClickListener;

    @Override
    public void bind(@NonNull ItemHolder holder) {
        holder.anime.setText(anime_title);
        holder.num_of_questions.setText(num_of_questions);
        ImageUtil.loadImageAnimeItem(holder.anime_image, image_url);
        holder.constraintLayout.setOnClickListener(onClickListener);
        holder.constraintLayout.setOnLongClickListener(onLongClickListener);
    }

    static class ItemHolder extends EpoxyHolder {

        TextView anime;
        TextView num_of_questions;
        TextView barrier_text;
        ImageView anime_image;
        ConstraintLayout constraintLayout;

        @Override
        protected void bindView(@NonNull View itemView) {
            anime = itemView.findViewById(R.id.tv_anime_name);
            num_of_questions = itemView.findViewById(R.id.tv_num_of_questions);
            barrier_text = itemView.findViewById(R.id.barrier_text);
            anime_image = itemView.findViewById(R.id.iv_anime_image);
            constraintLayout = itemView.findViewById(R.id.cardview_layout);
        }
    }
}
