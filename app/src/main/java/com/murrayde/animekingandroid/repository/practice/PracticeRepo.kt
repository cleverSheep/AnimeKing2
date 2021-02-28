package com.murrayde.animekingandroid.repository.practice

import com.murrayde.animekingandroid.network.community.clients.AnimeApiClient
import com.murrayde.animekingandroid.repository.Repository
import javax.inject.Inject

class PracticeRepo @Inject constructor(private val animeApiClient: AnimeApiClient) : Repository {
}