@file:Suppress("LocalVariableName")

package com.murrayde.animekingmobile.view_community_questions_askquestion

import org.junit.Assert.assertEquals
import org.junit.Test

class AskQuestionViewModelTest {

    @Test
    fun animeTitle_CorrectlyFormatted_ReturnTrue() {
        val anime_title = "Naruto"
        val formattedTitle = correctlyFormatTitle(anime_title)
        val formatted = isFormatted(formattedTitle)
        assertEquals(formatted, true)
    }

    @Test
    fun animeTitle_CorrectlyFormatted_ReturnFalse() {
        val anime_title = "Fate/Zero"
        //val formattedTitle = correctlyFormatTitle(anime_title)
        val formatted = isFormatted(anime_title)
        assertEquals(formatted, false)
    }

    @Test
    fun animeTitle_CorrectlyFormatTitle() {
        val anime_title = "Fate/Zero"
        val formattedTitle = correctlyFormatTitle(anime_title)
        isFormatted(anime_title)
        assertEquals(formattedTitle, "Fate Zero")
    }

    private fun isFormatted(formattedTitle: String): Boolean {
        return !formattedTitle.contains('/')
    }

    private fun correctlyFormatTitle(animeTitle: String): String {
        if (animeTitle.contains('/')) return animeTitle.replace("/", " ")
        return animeTitle
    }
}