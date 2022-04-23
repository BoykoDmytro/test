package com.test.common

import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

object DateUtils {

    private const val DEFAULT_DATE_TIME_FORMAT = "dd-MMM-yyyy (hh:mm a)"
    private const val FORMAT_PATTERN_SERVER = "yyyy-MM-dd'T'HH:mm:ss.SSS"

    fun toDateTimeFormat(date: Date): String {
        val sdf = SimpleDateFormat(DEFAULT_DATE_TIME_FORMAT, Locale.ENGLISH)
        return sdf.format(date)
    }

    fun toDateFormat(date: String): String? {
        val sdf = SimpleDateFormat(FORMAT_PATTERN_SERVER, Locale.getDefault())
        val output = DateFormat.getDateInstance(DateFormat.LONG, Locale.getDefault()) as SimpleDateFormat
        return sdf.parse(date)?.let { output.format(it) }
    }
}