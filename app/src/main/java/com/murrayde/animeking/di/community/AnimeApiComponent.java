package com.murrayde.animeking.di.community;


import com.murrayde.animeking.network.community.AnimeApiEndpoint;

import dagger.Component;

@Component(modules = {AnimeDataModule.class})
public interface AnimeApiComponent {
    AnimeApiEndpoint getAnimeApiEndpoint();
}
