package com.murrayde.animekingmobile.view.community.data_source;


import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;

import com.murrayde.animekingmobile.network.community.api.AnimeData;
import com.murrayde.animekingmobile.network.community.AnimeApiEndpoint;

import org.jetbrains.annotations.NotNull;

import io.reactivex.disposables.CompositeDisposable;

public class AnimeDataSourceFactory extends DataSource.Factory<String, AnimeData> {

    @SuppressWarnings("FieldCanBeLocal")
    private AnimeDataSource animeDataSource;
    private MutableLiveData<AnimeDataSource> animeSourceLiveData = new MutableLiveData<>();
    private MutableLiveData<Boolean> doneLoading;


    @SuppressWarnings("WeakerAccess")
    AnimeApiEndpoint animeApiEndpoint;
    private CompositeDisposable compositeDisposable;

    AnimeDataSourceFactory(AnimeApiEndpoint animeApiEndpoint, CompositeDisposable compositeDisposable, MutableLiveData<Boolean> doneLoading) {
        this.animeApiEndpoint = animeApiEndpoint;
        this.compositeDisposable = compositeDisposable;
        this.doneLoading = doneLoading;
    }

    @NotNull
    @Override
    public DataSource<String, AnimeData> create() {
        animeDataSource = new AnimeDataSource(animeApiEndpoint, compositeDisposable, doneLoading);
        animeSourceLiveData.postValue(animeDataSource);
        return animeDataSource;
    }
}
