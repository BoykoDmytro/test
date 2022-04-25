package com.test.base

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.text.format.DateUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.setMain
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@RunWith(RobolectricTestRunner::class)
abstract class BaseApiTest {

    companion object {
        const val MOCK_WEB_SERVER_PORT = 8000
    }

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    protected val server: MockWebServer = MockWebServer()

    abstract fun initValues()

    @Before
    open fun setUp() {
        Dispatchers.setMain(StandardTestDispatcher())
        server.start(MOCK_WEB_SERVER_PORT)
        initValues()
    }

    @After
    fun clear() {
        server.shutdown()
    }

    protected fun getRetrofit(): Retrofit = Retrofit.Builder()
        .baseUrl(server.url("/"))
        .addConverterFactory(gsonConvertorFactory())
        .client(getOkHttpClient())
        .build()

    private fun getOkHttpClient(): OkHttpClient {
        val client = OkHttpClient.Builder()
            .connectTimeout(DateUtils.MINUTE_IN_MILLIS, TimeUnit.MILLISECONDS)
            .writeTimeout(DateUtils.MINUTE_IN_MILLIS, TimeUnit.MILLISECONDS)
            .readTimeout(DateUtils.MINUTE_IN_MILLIS, TimeUnit.MILLISECONDS)
        return client.build()
    }

    private fun gsonConvertorFactory(): GsonConverterFactory = GsonConverterFactory.create()
}