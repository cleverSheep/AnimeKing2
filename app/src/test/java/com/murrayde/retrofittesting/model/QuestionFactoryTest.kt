package com.murrayde.retrofittesting.model

import org.junit.Before
import org.junit.Test

class QuestionFactoryTest {

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