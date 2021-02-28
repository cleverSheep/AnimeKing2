package com.murrayde.animekingandroid.di.community

import com.murrayde.animekingandroid.network.community.clients.AnimeApiClient
import com.murrayde.animekingandroid.repository.community.AnimeListRepo
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
    ): AnimeListRepo {
        return AnimeListRepo(animeApiClient)
    }
}
