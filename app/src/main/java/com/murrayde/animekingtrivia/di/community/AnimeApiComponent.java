package com.murrayde.animekingtrivia.di.community;


import com.murrayde.animekingtrivia.network.community.AnimeApiEndpoint;

import dagger.Component;

@Component(modules = {AnimeDataModule.class})
public interface AnimeApiComponent {
    AnimeApiEndpoint getAnimeApiEndpoint();
}
