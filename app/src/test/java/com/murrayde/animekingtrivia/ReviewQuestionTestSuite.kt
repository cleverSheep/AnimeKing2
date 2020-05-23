@file:Suppress("LocalVariableName")

package com.murrayde.animekingtrivia

import org.junit.Test
import org.junit.Assert.*

class ReviewQuestionTestSuite {

    @Test
    fun shouldRemoveQuestion_ReturnTrue() {
        val reaction = Reaction(21, 10, 11)
        assertEquals(shouldRemoveQuestion(reaction), true)
    }

    @Test
    fun shouldRemoveQuestion_ReturnFalse() {
        val reaction = Reaction(21, 11, 10)
        assertEquals(shouldRemoveQuestion(reaction), false)
    }

    data class Reaction(val total_reactions: Int, val total_likes: Int, val total_dislikes: Int)

    fun shouldRemoveQuestion(reaction: Reaction): Boolean {
        val can_check = reaction.total_reactions % 7 == 0 && reaction.total_reactions >= 7
        if (can_check) {
            return reaction.total_dislikes > reaction.total_likes
        }
        return false
    }
}