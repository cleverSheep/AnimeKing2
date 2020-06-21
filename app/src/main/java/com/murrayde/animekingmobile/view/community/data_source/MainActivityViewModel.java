package com.murrayde.animekingmobile.view.community.data_source;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.murrayde.animekingmobile.di.community.AnimeApiComponent;
import com.murrayde.animekingmobile.di.community.DaggerAnimeApiComponent;
import com.murrayde.animekingmobile.network.community.AnimeApiEndpoint;
import com.murrayde.animekingmobile.network.community.api.AnimeData;
import com.murrayde.animekingmobile.util.PagingUtil;

import io.reactivex.disposables.CompositeDisposable;

public class MainActivityViewModel extends ViewModel {

    private CompositeDisposable compositeDisposable;

    private LiveData<PagedList<AnimeData>> animeData;
    private MutableLiveData<Boolean> networkDoneLoading;

    public MainActivityViewModel() {
        AnimeApiComponent daggerAnimeApiComponent = DaggerAnimeApiComponent.builder()
                .build();
        AnimeApiEndpoint animeApiEndpoint = daggerAnimeApiComponent.getAnimeApiEndpoint();

        compositeDisposable = new CompositeDisposable();
        networkDoneLoading = new MutableLiveData<Boolean>();
        AnimeDataSourceFactory animeDataSourceFactory = new AnimeDataSourceFactory(animeApiEndpoint, compositeDisposable, networkDoneLoading);

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

    public LiveData<Boolean> networkDoneLoading() {
        return networkDoneLoading;
    }
}