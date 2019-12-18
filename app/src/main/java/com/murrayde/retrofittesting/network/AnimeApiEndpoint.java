package com.murrayde.retrofittesting.network;

import com.murrayde.retrofittesting.model.AnimeComplete;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface AnimeApiEndpoint {

    @GET("anime")
    Single<AnimeComplete> getAnimeComplete(
            @Query("page[limit]") int limit,
            @Query("page[offset]") int offset
    );

}
