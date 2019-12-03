package com.murrayde.retrofittesting.view;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.murrayde.retrofittesting.R;
import com.murrayde.retrofittesting.viewmodel.MainActivityViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.rv_anime)
    RecyclerView rvAnimeList;

    private ListAdapter listAdapter = new ListAdapter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        MainActivityViewModel viewModel = new ViewModelProvider(this).get(MainActivityViewModel.class);

        viewModel.getAnimeData().observe(this, list -> listAdapter.updateList(list));
        viewModel.refresh();

        if (rvAnimeList != null) {
            rvAnimeList.setAdapter(listAdapter);
            rvAnimeList.setLayoutManager(new GridLayoutManager(this, 2));
        }
    }
}
