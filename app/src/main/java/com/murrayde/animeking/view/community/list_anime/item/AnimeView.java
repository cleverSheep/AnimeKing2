package com.murrayde.animeking.view.community.list_anime.item;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.cardview.widget.CardView;

import com.airbnb.epoxy.EpoxyAttribute;
import com.airbnb.epoxy.EpoxyHolder;
import com.airbnb.epoxy.EpoxyModelClass;
import com.airbnb.epoxy.EpoxyModelWithHolder;
import com.murrayde.animeking.R;
import com.murrayde.animeking.util.ImageUtil;

@EpoxyModelClass(layout = R.layout.item_anime)
public abstract class AnimeView extends EpoxyModelWithHolder<AnimeView.ItemHolder> {

    @EpoxyAttribute String anime_title;
    @EpoxyAttribute String image_url;
    @EpoxyAttribute (EpoxyAttribute.Option.DoNotHash)
    View.OnClickListener onClickListener;

    @Override
    public void bind(@NonNull ItemHolder holder) {
        holder.anime.setText(anime_title);
        ImageUtil.loadImage(holder.anime_image, image_url);
        holder.cardView.setOnClickListener(onClickListener);
    }

    static class ItemHolder extends EpoxyHolder {

        TextView anime;
        ImageView anime_image;
        CardView cardView;

        @Override
        protected void bindView(@NonNull View itemView) {
            anime = itemView.findViewById(R.id.tv_anime_name);
            anime_image = itemView.findViewById(R.id.iv_anime_image);
            cardView = itemView.findViewById(R.id.cardview_layout);

        }
    }
}
