package com.test.base

import kotlinx.coroutines.Job
import org.junit.After
import org.junit.Before
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import kotlin.random.Random

@RunWith(RobolectricTestRunner::class)
abstract class BaseTest {

    protected val mockString: String
        get() = randomString()

    protected val mockInt: Int
        get() = Random.nextInt(Int.MIN_VALUE, Int.MAX_VALUE)

    protected var testJob: Job? = null

    abstract fun initValues()

    @Before
    fun setUp() {
        initValues()
    }

    @After
    fun clear() {
        testJob?.cancel()
    }

    private fun randomString(): String {
        val alphabet: List<Char> = ('a'..'z') + ('A'..'Z')
        return List(Random.nextInt(10, 15)) { alphabet.random() }.joinToString("")
    }
}