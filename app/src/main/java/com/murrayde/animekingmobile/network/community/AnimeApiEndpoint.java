package com.murrayde.animekingmobile.network.community;

import com.murrayde.animekingmobile.network.community.api.AnimeComplete;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface AnimeApiEndpoint {

    @GET("anime?sort=popularityRank")
    Single<AnimeComplete> getAllPopularAnime(
            @Query("page[limit]") int limit,
            @Query("page[offset]") int offset
    );

    @GET("anime?page[limit]=20")
    Single<AnimeComplete> getUserRequestedAnime(
            @Query("filter[text]") String title
    );
}
