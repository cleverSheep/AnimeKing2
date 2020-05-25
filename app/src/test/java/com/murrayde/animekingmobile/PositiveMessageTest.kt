package com.murrayde.animekingmobile

import org.junit.Test
import org.junit.Assert.*

class PositiveMessageTest {

    @Test
    fun testPositiveMessage_ReturnPositive() {
        val total_correct = 5f
        val total_questions = 6f

        val total_score = total_correct / total_questions
        assertTrue(total_score > 0.5)

    }

    @Test
    fun testNegativeMessage_ReturnPositive() {
        val total_correct = 2f
        val total_questions = 6f

        val total_score = total_correct / total_questions
        assertTrue(total_score < 0.5)

    }
}