package com.murrayde.retrofittesting.view;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.murrayde.retrofittesting.R;
import com.murrayde.retrofittesting.databinding.ItemAnimeBinding;
import com.murrayde.retrofittesting.model.AnimeData;

import java.util.ArrayList;
import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {

    private List<AnimeData> mAnimeList = new ArrayList<>();

    void updateList(List<AnimeData> list) {
        mAnimeList.clear();
        mAnimeList.addAll(list);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemAnimeBinding view = DataBindingUtil.inflate(inflater, R.layout.item_anime, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.itemView.setAnime(mAnimeList.get(position));
    }

    @Override
    public int getItemCount() {
        return mAnimeList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ItemAnimeBinding itemView;

        ViewHolder(@NonNull ItemAnimeBinding view) {
            super(view.getRoot());
            itemView = view;
        }
    }
}
