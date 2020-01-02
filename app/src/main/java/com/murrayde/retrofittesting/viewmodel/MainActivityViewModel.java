package com.murrayde.retrofittesting.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.murrayde.retrofittesting.di.AnimeApiComponent;
import com.murrayde.retrofittesting.di.DaggerAnimeApiComponent;
import com.murrayde.retrofittesting.model.AnimeData;
import com.murrayde.retrofittesting.network.AnimeApiEndpoint;

import io.reactivex.disposables.CompositeDisposable;

public class MainActivityViewModel extends ViewModel {

    private CompositeDisposable compositeDisposable;

    private LiveData<PagedList<AnimeData>> animeData;

    public MainActivityViewModel() {
        AnimeApiComponent daggerAnimeApiComponent = DaggerAnimeApiComponent.builder()
                .build();
        AnimeApiEndpoint animeApiEndpoint = daggerAnimeApiComponent.getAnimeApiEndpoint();

        compositeDisposable = new CompositeDisposable();
        AnimeDataSourceFactory animeDataSourceFactory = new AnimeDataSourceFactory(animeApiEndpoint, compositeDisposable);

        PagedList.Config pagingConfig = new PagedList.Config.Builder()
                .setPrefetchDistance(10)
                .setEnablePlaceholders(true)
                .build();
        animeData = new LivePagedListBuilder<>(animeDataSourceFactory, pagingConfig).build();
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.clear();
    }

    public LiveData<PagedList<AnimeData>> getAnimeData() {
        return animeData;
    }
}