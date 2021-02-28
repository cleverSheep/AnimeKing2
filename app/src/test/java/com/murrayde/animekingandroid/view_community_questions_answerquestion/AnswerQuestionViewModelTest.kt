@file:Suppress("LocalVariableName")

package com.murrayde.animekingandroid.view_community_questions_answerquestion

import com.murrayde.animekingandroid.util.QuestionUtil
import org.junit.Assert.assertEquals
import org.junit.Test

class AnswerQuestionViewModelTest {

    @Test
    fun testCorrectQuestionSize_ReturnQuestionLimit() {
        val community_question_count = 14
        val question_limit = QuestionUtil.QUESTION_LIMIT

        assertEquals(questionCount(community_question_count, question_limit), question_limit)
    }

    @Test
    fun testCorrectQuestionSize_ReturnCommunityQuestionCount() {
        val community_question_count = 7
        val question_limit = QuestionUtil.QUESTION_LIMIT

        assertEquals(questionCount(community_question_count, question_limit), community_question_count)
    }

    @Suppress("SameParameterValue")
    /**This logic needs to be in a viewmodel*/
    private fun questionCount(community_count: Int, question_limit: Long) = if (community_count > question_limit) question_limit else community_count
}