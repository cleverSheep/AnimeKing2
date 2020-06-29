package com.murrayde.animekingmobile.di.community

import com.murrayde.animekingmobile.network.community.AnimeApiClient
import com.murrayde.animekingmobile.repository.community.MainRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped

@Module
@InstallIn(ActivityRetainedComponent::class)
object RepositoryModule {

    @Provides
    @ActivityRetainedScoped
    fun provideMainRepository(
            animeApiClient: AnimeApiClient
    ): MainRepo {
        return MainRepo(animeApiClient)
    }
}
