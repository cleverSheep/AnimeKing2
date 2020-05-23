@file:Suppress("LocalVariableName")

package com.murrayde.animekingtrivia.util

import com.murrayde.animekingtrivia.model.reaction.Reaction


fun convertToUserName(email: String?): String {
    val user_name = email?.split('@')
    return "@${user_name!![0]}"
}

fun determineAppLanguage(current_language: String, system_lang: String) = if (current_language != system_lang) system_lang else current_language

fun shouldRemoveQuestion(reaction: Reaction): Boolean {
    val can_check = reaction.total_reactions % 7 == 0 && reaction.total_reactions >= 7
    if (can_check) {
        return reaction.total_dislikes > reaction.total_likes
    }
    return false
}

