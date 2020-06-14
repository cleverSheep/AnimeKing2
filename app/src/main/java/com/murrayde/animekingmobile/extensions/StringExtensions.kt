package com.murrayde.animekingmobile.extensions

fun String.lastCharacterIsDigit(): Boolean {
    if (this.isEmpty()) return false
    return this[this.length - 1].isDigit()
}