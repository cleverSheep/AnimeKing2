package com.murrayde.animeking.view.community.list_anime.item;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;

import com.airbnb.epoxy.EpoxyAttribute;
import com.airbnb.epoxy.EpoxyHolder;
import com.airbnb.epoxy.EpoxyModelClass;
import com.airbnb.epoxy.EpoxyModelWithHolder;
import com.murrayde.animeking.R;

@EpoxyModelClass(layout = R.layout.item_header)
public abstract class HeaderView extends EpoxyModelWithHolder<HeaderView.Holder> {

    @EpoxyAttribute @StringRes int title;
    @EpoxyAttribute (EpoxyAttribute.Option.DoNotHash)
    View.OnClickListener onClickListener;


    @Override
    public void bind(@NonNull Holder holder) {
        holder.title.setText(title);
        holder.play_button.setOnClickListener(onClickListener);
    }

    static class Holder extends EpoxyHolder {

        Button play_button;
        TextView title;

        @Override
        protected void bindView(@NonNull View itemView) {
            play_button = itemView.findViewById(R.id.header_play_button);
            title = itemView.findViewById(R.id.header_title);

        }
    }
}
