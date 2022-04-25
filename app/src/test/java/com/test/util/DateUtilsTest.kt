package com.test.util

import com.test.common.DateUtils
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class DateUtilsTest {

    @Test
    fun `validate date convertor`() {
        val date = DateUtils.toDateFormat("2022-01-04T07:30:24.342Z")
        assert(date != null)
        assert(date == "January 4, 2022")
    }
}