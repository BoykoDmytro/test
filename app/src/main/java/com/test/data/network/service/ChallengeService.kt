package com.test.data.network.service

import com.test.data.entity.ChallengeList
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ChallengeService {

    companion object {
        private const val GET_CHALLENGE_LIST = "/api/v1/users/{user}/code-challenges/completed"
    }

    @GET(GET_CHALLENGE_LIST)
    suspend fun getChallenges(@Path("user") user: String, @Query("page") page: Long): ChallengeList
}