package com.murrayde.retrofittesting.di;

import com.murrayde.retrofittesting.network.AnimeApiEndpoint;

import dagger.Component;

@Component(modules = {AnimeDataModule.class})
public interface AnimeApiComponent {

    AnimeApiEndpoint getAnimeApiEndpoint();

}
