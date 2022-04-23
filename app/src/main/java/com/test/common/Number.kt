package com.test.common

import android.content.res.Resources

fun Number.toDp(): Int {
    return (this.toFloat() * Resources.getSystem().displayMetrics.density).toInt()
}