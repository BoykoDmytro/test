package com.test.api

import com.test.base.BaseApiTest
import com.test.data.network.service.ChallengeService
import com.test.data.repository.ChallengeRepositoryImpl
import com.test.domain.repository.IChallengeRepository
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import org.junit.Test
import retrofit2.HttpException

class ChallengeServicesTest : BaseApiTest() {

    companion object {
        private const val USER_NAME = "Voile"
    }

    private lateinit var challengeServices: ChallengeService
    private lateinit var challengeRepo: IChallengeRepository

    override fun initValues() {
        challengeServices = getRetrofit().create(ChallengeService::class.java)
        challengeRepo = ChallengeRepositoryImpl(challengeServices)
    }

    @Test
    fun `check successful GetChallenges api`() = runTest {
        val mockResponse = MockResponse()
            .setResponseCode(200)
            .setBody(FileReader.readFileFromResources("challenge_list_success_response.json"))

        server.enqueue(mockResponse)
        val result = challengeRepo.getChallenges(USER_NAME, 1)
        assert(result.entities.isNotEmpty())
    }

    @Test(expected = HttpException::class)
    fun `check failed GetChallenges api`() = runTest {
        server.enqueue(MockResponse().setResponseCode(404))
        challengeRepo.getChallenges(USER_NAME, 1)
    }
}