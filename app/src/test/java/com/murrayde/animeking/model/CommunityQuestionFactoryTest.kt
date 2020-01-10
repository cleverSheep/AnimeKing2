package com.murrayde.animeking.model

import com.murrayde.animeking.model.community.QuestionFactory
import org.junit.Before
import org.junit.Test

class CommunityQuestionFactoryTest {

    lateinit var SUT: QuestionFactory

    @Before
    fun setUp() {
        SUT = QuestionFactory()
    }

    @Test
    fun hasEnoughQuestions_QuestionCountIsLimit_TrueReturned() {
        // TODO: MAKE FAKE CALL TO FIREBASE DATABASE
    }
}