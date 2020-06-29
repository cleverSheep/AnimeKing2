@file:Suppress("LocalVariableName")

package com.murrayde.animekingmobile.util

import com.murrayde.animekingmobile.extensions.lastCharacterIsDigit
import com.murrayde.animekingmobile.network.community.api.AnimeData


fun convertToUserName(email: String?): String {
    val user_name = email?.split('@')
    return "@${user_name!![0]}"
}

fun determineQuestionLanguage(app_language: String, system_language: String): String {
    return if (app_language != system_language) app_language
    else system_language
}

fun questionCount(community_count: Int, question_limit: Long) = if (community_count > question_limit) question_limit else community_count

fun removeForwardSlashes(animeTitle: String): String {
    if (animeTitle.contains('/')) return animeTitle.replace("/", " ")
    return animeTitle
}

fun performAnimeTitleFiltering(animeDataArrayList: ArrayList<AnimeData>): List<AnimeData> {
    return animeDataArrayList.asSequence().filter {
        if (it.attributes.synopsis == null) return@filter false else !it.attributes.synopsis.contains("season", true)
    }.filter {
        if (it.attributes.slug == null) return@filter false else !it.attributes.slug.contains("season", true)
    }.filter {
        if (it.attributes.slug == null) return@filter false else !it.attributes.slug.contains("ii", true)
    }.filter {
        if (it.attributes.slug == null) return@filter false else !it.attributes.slug.lastCharacterIsDigit()
    }.filter {
        if (it.attributes.slug == null) return@filter false else it.attributes.slug.isNotEmpty()
    }.filter {
        if (it.attributes.titles.en == null) return@filter false else !it.attributes.titles.en.contains("season", true)
    }.filter {
        if (it.attributes.titles.en == null) return@filter false else !it.attributes.titles.en.contains("ii", true)
    }.filter {
        if (it.attributes.titles.en == null) return@filter false else !it.attributes.titles.en.lastCharacterIsDigit()
    }.filter {
        if (it.attributes.titles.en == null) return@filter false else it.attributes.titles.en.isNotEmpty()
    }.filter {
        if (it.attributes.titles.enJp == null) return@filter false else !it.attributes.titles.enJp.contains("season", true)
    }.filter {
        if (it.attributes.titles.jaJp == null) return@filter false else !it.attributes.titles.jaJp.contains("season", true)
    }.filter {
        if (it.attributes.canonicalTitle == null) return@filter false else !it.attributes.canonicalTitle.contains("season", true)
    }.toList() as ArrayList<AnimeData>
}