package com.murrayde.animekingtrivia

import org.junit.Test
import org.junit.Assert.*


class ProfileTestSuite {

    @Test
    fun emailConvertedToUserName_TrueReturned() {
        val email = "testing@gmail.com"
        val user_name = email.split('@')
        assertEquals(user_name[0], "testing")
    }
}