package com.murrayde.retrofittesting.network;

import com.murrayde.retrofittesting.model.AnimeComplete;

import io.reactivex.Single;
import retrofit2.http.GET;

public interface AnimeApiEndpoint {

    @GET("anime?page[limit]=20")
    Single<AnimeComplete> getAnimeComplete();

}
