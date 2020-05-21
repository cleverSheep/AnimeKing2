@file:Suppress("LocalVariableName")

package com.murrayde.animekingtrivia.util


fun convertToUserName(email: String?): String {
    val user_name = email?.split('@')
    return "@${user_name!![0]}"
}

fun determineQuestionLanguage(app_language: String, system_language: String): String {
    return if (app_language != system_language) app_language
    else system_language
}