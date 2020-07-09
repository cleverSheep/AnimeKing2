package com.murrayde.animekingmobile.repository.practice

import com.murrayde.animekingmobile.network.community.clients.AnimeApiClient
import com.murrayde.animekingmobile.repository.Repository
import javax.inject.Inject

class PracticeRepo @Inject constructor(private val animeApiClient: AnimeApiClient) : Repository {
}