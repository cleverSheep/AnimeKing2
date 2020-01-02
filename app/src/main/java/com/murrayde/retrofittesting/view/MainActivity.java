package com.murrayde.retrofittesting.view;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.murrayde.retrofittesting.R;
import com.murrayde.retrofittesting.viewmodel.MainActivityViewModel;

public class MainActivity extends AppCompatActivity {

    RecyclerView rvAnimeList;
    private AnimeDataAdapter listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MainActivityViewModel viewModel = new ViewModelProvider(this).get(MainActivityViewModel.class);
        rvAnimeList = findViewById(R.id.rv_anime);
        listAdapter = new AnimeDataAdapter();

        viewModel.getAnimeData().observe(this, list -> listAdapter.submitList(list));
        rvAnimeList.setAdapter(listAdapter);
        rvAnimeList.setLayoutManager(new GridLayoutManager(this, 2));
    }
}
