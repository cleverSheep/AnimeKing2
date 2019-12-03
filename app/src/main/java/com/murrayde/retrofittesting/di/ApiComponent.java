package com.murrayde.retrofittesting.di;

import com.murrayde.retrofittesting.network.AnimeApiService;
import com.murrayde.retrofittesting.viewmodel.MainActivityViewModel;

import dagger.Component;

@Component(modules = {ApiModule.class})
public interface ApiComponent {

    void inject(AnimeApiService service);

    void inject(MainActivityViewModel viewModel);

}
