package com.murrayde.animekingtrivia.view.community.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.murrayde.animekingtrivia.di.community.AnimeApiComponent;
import com.murrayde.animekingtrivia.di.community.DaggerAnimeApiComponent;
import com.murrayde.animekingtrivia.network.community.AnimeApiEndpoint;
import com.murrayde.animekingtrivia.network.community.api.AnimeData;
import com.murrayde.animekingtrivia.util.PagingUtil;

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