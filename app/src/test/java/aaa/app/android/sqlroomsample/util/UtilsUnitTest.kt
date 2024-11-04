package aaa.app.android.sqlroomsample.util

import aaa.app.android.sqlroomsample.util.Utils.validateEmail
import org.junit.Assert.assertEquals
import org.junit.Test


class UtilsUnitTest {


    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun validateEmailTest() {
        var result = false
        val invalidEmailList = listOf(
            "hello@gmail.com", "hai@gmail.com"
        )
        for (invalidEmail in invalidEmailList) {
            result = validateEmail(invalidEmail)
        }
        assertEquals(true, result)

    }


    /*@Test
    fun getCurrentMonthNAmeTest() {
       // val month = getCurrentMonthName()
        //assertEquals("October", month)

    }*/


}