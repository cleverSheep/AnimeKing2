package com.murrayde.animeking.network.community;

import com.murrayde.animeking.model.community.api.AnimeComplete;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface AnimeApiEndpoint {

    @GET("anime?filter[subtype]=tv&sort=popularityRank")
    Single<AnimeComplete> getAnimeComplete(
            @Query("page[limit]") int limit,
            @Query("page[offset]") int offset
    );

}
