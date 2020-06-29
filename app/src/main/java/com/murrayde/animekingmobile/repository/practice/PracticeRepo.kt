package com.murrayde.animekingmobile.repository.practice

import com.murrayde.animekingmobile.model.ui.AnimeForYou
import com.murrayde.animekingmobile.network.community.AnimeApiClient
import com.murrayde.animekingmobile.repository.Repository
import io.reactivex.Single
import javax.inject.Inject

class PracticeRepo @Inject constructor(private val animeApiClient: AnimeApiClient) : Repository {
}