package com.murrayde.retrofittesting.di;

import com.murrayde.retrofittesting.network.AnimeApiEndpoint;
import com.murrayde.retrofittesting.network.AnimeApiService;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class ApiModule {

    @SuppressWarnings("FieldCanBeLocal")
    private static String BASE_URL = "https://kitsu.io/api/edge/";

    @Provides
    public AnimeApiEndpoint provideAnimeApiEndpoint() {
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(AnimeApiEndpoint.class);
    }

    @Provides
    public AnimeApiService provideAnimeApiService() {
        return new AnimeApiService();
    }

}
