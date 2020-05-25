package com.murrayde.animekingmobile

import org.junit.Test
import org.junit.Assert.*

class QuestionLanguageTestSuite {

    @Test
    fun determineAppLanguage_ReturnEnglish() {
        val app_langauge = "en"
        val system_language = "en"
        assertEquals(determineAppLanguage(app_langauge, system_language), "en")
    }

    @Test
    fun determineAppLanguage_ReturnSpanish() {
        val app_langauge = "en"
        val system_language = "sp"
        assertEquals(determineAppLanguage(app_langauge, system_language), "sp")
    }

    private fun determineAppLanguage(current_language: String, system_lang: String) = if (current_language != system_lang) system_lang else current_language
}