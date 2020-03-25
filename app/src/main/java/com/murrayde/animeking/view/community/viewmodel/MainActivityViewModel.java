package com.murrayde.animeking.view.community.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.murrayde.animeking.di.community.AnimeApiComponent;
import com.murrayde.animeking.di.community.DaggerAnimeApiComponent;
import com.murrayde.animeking.network.community.AnimeApiEndpoint;
import com.murrayde.animeking.network.community.api.AnimeData;
import com.murrayde.animeking.util.PagingUtil;

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
                .setPrefetchDistance(PagingUtil.PAGING_PREFETCH)
                .setEnablePlaceholders(false)
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