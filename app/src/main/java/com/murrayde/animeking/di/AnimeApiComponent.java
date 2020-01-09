package com.murrayde.animeking.di;

import com.murrayde.animeking.network.AnimeApiEndpoint;

import dagger.Component;

@Component(modules = {AnimeDataModule.class})
public interface AnimeApiComponent {

    AnimeApiEndpoint getAnimeApiEndpoint();

}
