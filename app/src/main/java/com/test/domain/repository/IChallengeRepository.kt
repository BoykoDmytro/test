package com.test.domain.repository

import com.test.data.entity.ChallengeList

interface IChallengeRepository {

    suspend fun getChallenges(user: String, page: Long): ChallengeList
}