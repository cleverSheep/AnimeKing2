package com.murrayde.animekingtrivia.model.random

data class RandomQuestion(
    val response_code: Int,
    val results: List<Result>
)