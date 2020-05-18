@file:Suppress("LocalVariableName")

package com.murrayde.animekingtrivia.util

fun convertToUserName(email: String?): String {
    val user_name = email?.split('@')
    return "@${user_name!![0]}"
}