package com.murrayde.animekingmobile.extensions

import java.lang.StringBuilder

fun StringBuilder.formatQuestion(question: String): String {
    append(question)
    if(question.toCharArray()[question.length-1] != '?'){
        append('?')
        return toString()
    }
    return toString()
}