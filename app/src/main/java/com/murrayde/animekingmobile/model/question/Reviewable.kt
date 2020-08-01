package com.murrayde.animekingmobile.model.question

open class Reviewable {
    open var isLiked = false
    open var isDisliked = false

    open fun likeQuestion() {
        isLiked = true
        isDisliked = false
    }

    open fun dislikeQuestion() {
        isLiked = false
        isDisliked = true
    }
}