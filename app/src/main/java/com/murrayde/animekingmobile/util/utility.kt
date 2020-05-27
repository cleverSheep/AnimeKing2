@file:Suppress("LocalVariableName")

package com.murrayde.animekingmobile.util


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