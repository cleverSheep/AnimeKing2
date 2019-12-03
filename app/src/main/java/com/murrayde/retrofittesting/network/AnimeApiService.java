package com.murrayde.retrofittesting.network;

import com.murrayde.retrofittesting.di.DaggerApiComponent;
import com.murrayde.retrofittesting.model.AnimeComplete;

import javax.inject.Inject;

import io.reactivex.Single;

public class AnimeApiService {

    @Inject
    AnimeApiEndpoint apiEndpoint;

    @Inject
    public AnimeApiService() {
        DaggerApiComponent.create().inject(this);
    }

    public Single<AnimeComplete> getAnimeComplete() {
        return apiEndpoint.getAnimeComplete();
    }
}
