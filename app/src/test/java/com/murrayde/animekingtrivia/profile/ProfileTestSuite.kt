package com.murrayde.animekingtrivia.profile

import org.junit.Test
import org.junit.Assert.*


class ProfileTestSuite {

    @Test
    fun emailConvertedToUserName_TrueReturned() {
        val email = "testing@gmail.com"
        val user_name = email.split('@')
        val successful_convert = user_name[0] == "testing"
        assertEquals(successful_convert, true)
    }

    @Test
    fun emailConvertedToUserName_FalseReturned() {
        val email = "testing@gmail.com"
        val user_name = email.split('@')
        val successful_convert = user_name[0] == "testing@gmail.com"
        assertEquals(successful_convert, false)
    }
}