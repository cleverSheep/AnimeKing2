@file:Suppress("LocalVariableName")

package com.murrayde.animekingmobile.view_community_quizresults

import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Test

class GameOverViewModelTest {

    @Test
    fun testPositiveMessage_ReturnPositive() {
        val total_correct = 5f
        val total_questions = 6f

        val total_score = total_correct / total_questions
        Assert.assertTrue(total_score > 0.5)

    }

    @Test
    fun testNegativeMessage_ReturnPositive() {
        val total_correct = 2f
        val total_questions = 6f

        val total_score = total_correct / total_questions
        Assert.assertTrue(total_score < 0.5)

    }


    @Test
    fun shouldRemoveQuestion_ReturnTrue() {
        val reaction = Reaction(21, 10, 11, "001122")
        assertEquals(shouldRemoveQuestion(reaction), true)
    }

    @Test
    fun shouldRemoveQuestion_ReturnFalse() {
        val reaction = Reaction(21, 11, 10, "001122")
        assertEquals(shouldRemoveQuestion(reaction), false)
    }

    data class Reaction(val total_reactions: Int, val total_likes: Int, val total_dislikes: Int, val user_id: String)

    /** This logic needs to be in a viewmodel*/
    private fun shouldRemoveQuestion(reaction: Reaction): Boolean {
        val can_check = reaction.total_reactions % 7 == 0 && reaction.total_reactions >= 7
        if (can_check) {
            return reaction.total_dislikes > reaction.total_likes
        }
        return false
    }
}