package com.murrayde.animekingmobile.di.community;


import com.murrayde.animekingmobile.network.community.AnimeApiEndpoint;

import dagger.Component;

@Component(modules = {AnimeDataModule.class})
public interface  AnimeApiComponent {
    AnimeApiEndpoint getAnimeApiEndpoint();
}
